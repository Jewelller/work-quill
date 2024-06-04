package com.qwizery.workquillserverbase.filter;

import jakarta.servlet.*;
import org.springframework.security.authentication.TestingAuthenticationToken;

import java.io.IOException;

public class TestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Before TestFilter");

        // 模拟放行
        var auth = new TestingAuthenticationToken("username", "password", "ROLE_USER");
//        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(request, response);
        System.out.println("After TestFilter");
    }

}
