package com.kopo.web_final.filter;

import com.kopo.web_final.domain.member.model.Member;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("*.do")
public class AdminCheckFilter implements Filter {

    // 관리자 전용 URI 식별자 목록
    private final List<String> adminUris = Arrays.asList(
            "memberManagement",
            "memberApproval",
            "memberStatusUpdate",
            "categoryInsert",
            "categoryUpdate",
            "categoryDelete",
            "categoryStatusUpdate",
            "categoryManagement",
            "productManagement",
            "productInsert",
            "productUpdate",
            "productDelete",
            "orderManagement",
            "productSoldOut",
            "orderDetailManagement"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String uri = req.getRequestURI(); // 예: /web_final/productManagement.do
        String command = uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf(".do"));

        // 관리자 권한이 필요한 URI인가?
        if (adminUris.contains(command)) {
            HttpSession session = req.getSession();
            Member loginUser = (Member) session.getAttribute("loginUser");

            if (loginUser == null || !"_20".equals(loginUser.getCdUserType())) {
                session.setAttribute("message", "관리자 권한이 필요한 서비스입니다.");
                res.sendRedirect(req.getContextPath() + "/member/login.jsp");
                return;
            }
        }

        // 관리자 요청이 아니면 통과
        filterChain.doFilter(req, res);
    }
}
