package com.kopo.web_final.category.controller;

import com.kopo.web_final.Command;
import com.kopo.web_final.category.dao.CategoryDao;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;

public class CategoryDeleteCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8"); // Y
        System.out.println(req.getParameter("nbCategory"));
        try(Connection conn = Db.getConnection()){
            CategoryDao dao = new CategoryDao(conn);

            int result = dao.updateDeleteAndUseStatusFlush(req.getParameter("nbCategory"));

            if(result < 1){
                // 실패 메시지와 함께 리다이렉트
                req.setAttribute("message","DeleteFail");
                req.setAttribute("type","error");
            }
            req.setAttribute("message","DeleteSuccess");
            req.setAttribute("type","success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "categoryManagement.do";
    }
}
