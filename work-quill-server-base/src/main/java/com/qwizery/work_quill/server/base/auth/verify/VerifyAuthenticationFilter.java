package com.qwizery.work_quill.server.base.auth.verify;

import com.qwizery.work_quill.server.base.exception.NoSuchElementException;
import com.qwizery.work_quill.server.base.util.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 验证用户交互token，若失效则验证refreshToken并且返回一个新的token和refreshToken
 * 如果访问的是登录接口则跳过当前验证
 **/
public class VerifyAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final String loginPath;
    private final RequestMatcher requestMatcher;

    public VerifyAuthenticationFilter(String loginPath, AntPathRequestMatcher pathRequestMatcher, AuthenticationManager authenticationManager, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler) {
        super(pathRequestMatcher);
        this.loginPath = loginPath;
        this.requestMatcher = pathRequestMatcher;
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
            return;
        }

        // Take over request and response
        var requestWrapper = new ContentCachingRequestWrapper(request);
        var responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            Authentication authenticationResult = attemptAuthentication(requestWrapper, responseWrapper);
            if (authenticationResult == null) {
                // return immediately as subclass has indicated that it hasn't completed
                return;
            }
            // Authentication success
            if (authenticationResult.isAuthenticated()) {
                logger.debug("Authenticated, now pass to controller");

                // Set the authentication object into the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authenticationResult);

                chain.doFilter(requestWrapper, responseWrapper);
                logger.debug("Controller finished, now go to VerifySuccessHandler");
                successfulAuthentication(requestWrapper, responseWrapper, chain, authenticationResult);
            }
        } catch (InternalAuthenticationServiceException failed) {
            this.logger.error("An internal error occurred while trying to authenticate the user.", failed);
            unsuccessfulAuthentication(request, responseWrapper, failed);
        } catch (AuthenticationException ex) {
            // Authentication failed
            logger.debug("Authentication failed: {}", ex);
            unsuccessfulAuthentication(request, responseWrapper, ex);
        }
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        final AntPathRequestMatcher matcher = AntPathRequestMatcher.antMatcher(loginPath);
        if (matcher.matches(request)) {
            return false;
        }

        if (this.requestMatcher.matches(request)) {
            return true;
        }
        if (this.logger.isTraceEnabled()) {
            this.logger
                    .trace(LogMessage.format("Did not match request to %s", this.requestMatcher));
        }
        return false;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        logger.debug("Using VerifyAuthenticationFilter");

        String requestJsonData = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Map<String, Object> requestMapData = JSON.parseToMap(requestJsonData);

        // Use this to parse object!
        request.setAttribute("requestBody", requestJsonData);

        var tokenOpt = Optional.ofNullable(requestMapData.get("token"));
        var refreshTokenOpt = Optional.ofNullable(requestMapData.get("refreshToken"));

        String token = tokenOpt.orElseThrow(() ->
                new NoSuchElementException("auth.verify.missing_token")).toString();
        String refreshToken = refreshTokenOpt.orElseThrow(() ->
                new NoSuchElementException("auth.verify.missing_refresh_token")).toString();

        var authentication = new VerifyAuthentication();
        authentication.setJwtToken(token);
        authentication.setJwtRefreshToken(refreshToken);

        // 开始鉴权
        return getAuthenticationManager().authenticate(authentication);
    }
}
