package com.kopo.web_final;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,   // 1MB
        maxFileSize = 1024 * 1024 * 10,    // 10MB
        maxRequestSize = 1024 * 1024 * 50  // 50MB
)
@WebServlet("*.do")
public class FrontController extends HttpServlet {
    public FrontController() {
        super();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        System.out.println("URI: " + uri);
        String contextPath = request.getContextPath();
        System.out.println("contextPath: " + contextPath);
        String commandKey = uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf(".do"));
        System.out.println("commandKey: " + commandKey);

        Command command = CommandFactory.getCommand(commandKey);

        String viewPage = null;

        try {
            if (command != null) {
                viewPage = command.execute(request, response);

                // viewPage가 null이 아닐 때만 forward 실행
                if (viewPage != null) {
                    request.getRequestDispatcher(viewPage).forward(request, response);
                }
                // viewPage가 null이면 이미 리다이렉트된 것으로 간주

            } else {
                // 직접 JSP로 forward
                request.setAttribute("errorMessage", "요청하신 기능을 찾을 수 없습니다.");
                request.getRequestDispatcher("/error/404.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "서버 내부 오류가 발생했습니다.");
            request.getRequestDispatcher("/error/500.jsp").forward(request, response);
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
