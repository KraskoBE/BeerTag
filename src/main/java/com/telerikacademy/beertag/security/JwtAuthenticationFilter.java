package com.telerikacademy.beertag.security;

import org.springframework.security.core.Authentication;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private JwtProvider provider;

    public JwtAuthenticationFilter(JwtProvider provider) {
        this.provider = provider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = provider.resolveToken((HttpServletRequest) request);

        if (token != null && provider.validateToken(token)) {
            Authentication authentication = (token != null ? provider.getAuthentication(token) : null);
        }
        chain.doFilter(request, response);
    }
}
