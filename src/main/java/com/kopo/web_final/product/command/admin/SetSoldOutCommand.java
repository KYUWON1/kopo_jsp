package com.kopo.web_final.product.command.admin;

import com.kopo.web_final.Command;
import com.kopo.web_final.product.dao.ProductDao;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetSoldOutCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        System.out.println("UPDATE : SetSoldOutCommand : " + req.getParameter("noProduct"));
        String noProduct = req.getParameter("noProduct");


        try (Connection conn = Db.getConnection()) {
            ProductDao dao = new ProductDao(conn);
            int result = dao.setSoldOut(noProduct);
            if (result < 1) {
                req.setAttribute("message", "상태 변경에 실패했습니다.");
                req.setAttribute("type", "error");
                return "productManagement.do";
            }

            // 리다이렉트 또는 메시지 전달
            req.setAttribute("message", "상품이 품절 처리되었습니다.");
            req.setAttribute("type", "success");
        } catch (SQLException e) {
            req.setAttribute("message", "품절 처리 중 오류 발생");
            req.setAttribute("type", "error");
            return "productManagement.do";
        }

        return "productManagement.do";
    }
}
