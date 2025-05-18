package com.kopo.web_final.filter;

import com.kopo.web_final.domain.member.model.Member;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("*.do")
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String uri = req.getRequestURI();

        // 로그인 없이 접근 가능한 URI 예외 처리
        if (uri.contains("login") ||
                uri.contains("join") ||
                uri.contains("main") ||
                uri.contains("productListSort") ||
                uri.contains("productDetail") ) {
            filterChain.doFilter(req, res);
            return;
        }

        HttpSession session = req.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        if (loginUser == null) {
            session.setAttribute("message", "로그인이 필요한 서비스입니다.");
            res.sendRedirect(req.getContextPath() + "/member/login.jsp");
            return;
        }

        // 로그인된 경우 요청 계속 진행
        filterChain.doFilter(req, res);
    }
}
