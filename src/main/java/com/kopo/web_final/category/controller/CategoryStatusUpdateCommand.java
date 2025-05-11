package com.kopo.web_final.category.controller;

import com.kopo.web_final.Command;
import com.kopo.web_final.category.dao.CategoryDao;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;

public class CategoryStatusUpdateCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8"); // Y
        String ynUse = req.getParameter("ynUse");
        try(Connection conn = Db.getConnection()){
            CategoryDao dao = new CategoryDao(conn);
            int result;
            result = dao.updateUseStatusActive(req.getParameter("nbCategory"), ynUse);

            if(result < 1){
                // 실패 메시지와 함께 리다이렉트
                req.setAttribute("message","StatusUpdateFail");
                req.setAttribute("type","error");
            }
            // 성공 메시지와 함께 리다이렉트
            req.setAttribute("message","StatusUpdateSuccess");
            req.setAttribute("type","success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "categoryManagement.do";
    }
}
