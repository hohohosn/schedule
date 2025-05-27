package com.example.schedulepj.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthFilter implements Filter {

    // 인증이 필요하지 않은 경로들
    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
            "/users/signup",
            "/users/login",
            "/users/logout"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // 인증이 필요하지 않은 경로는 필터를 통과
        if (EXCLUDE_PATHS.contains(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // 세션에서 로그인 정보 확인
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("로그인이 필요합니다.");
            return;
        }

        chain.doFilter(request, response);
    }
}