package com.qwizery.work_quill.server.base.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class TestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Before TestFilter");

        chain.doFilter(request, response);

        System.out.println("After TestFilter");
    }

}
