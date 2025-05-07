package com.kopo.web_final.category.controller;

import com.kopo.web_final.category.dao.CategoryDao;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;

@WebServlet(name = "CategoryDeleteController", value = "/admin/category-delete")
public class CategoryDeleteController extends HttpServlet {
    public CategoryDeleteController() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8"); // Y

        try(Connection conn = Db.getConnection()){
            CategoryDao dao = new CategoryDao(conn);

            int result = dao.deleteCategory(req.getParameter("nbCategory"));

            if(result != 1){
                // 실패 메시지와 함께 리다이렉트
                res.sendRedirect("/admin/category?message=InsertFail&type=error");
                return;
            }
            // 성공 메시지와 함께 리다이렉트
            res.sendRedirect("/admin/category?message=" +
                    URLEncoder.encode("카테고리가 성공적으로 변경되었습니다.", "UTF-8") +
                    "&type=success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
