package com.kopo.web_final.category.controller;

import com.kopo.web_final.Command;
import com.kopo.web_final.category.dao.CategoryDao;
import com.kopo.web_final.category.model.Category;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "CategoryInsertController", value = "/admin/category-insert")
public class CateGoryInsertCommand implements Command {
    public CateGoryInsertCommand() {
        super();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        Category category = new Category();

        // nbParentCategory 처리
        String parentCategoryStr = req.getParameter("nbParentCategory");
        if (parentCategoryStr != null && !parentCategoryStr.isEmpty()) {
            category.setNbParentCategory(Integer.parseInt(parentCategoryStr));
        } else {
            category.setNbParentCategory(null);
        }

        category.setNmCategory(req.getParameter("nmCategory"));
        category.setNmFullCategory(req.getParameter("nmFullCategory"));
        category.setNmExplain(req.getParameter("nmExplain"));
        category.setCnLevel(Integer.parseInt(req.getParameter("cnLevel")));
        category.setCnOrder(Integer.parseInt(req.getParameter("cnOrder")));

        // ynUse 값을 ynUser 대신 정확하게 처리
        String ynUse = req.getParameter("ynUse");
        System.out.println(ynUse);
        category.setYnUse(ynUse); // "Y" 또는 "N"

        category.setNoRegister(req.getParameter("noRegister"));
        category.setYnDelete("N");
        category.setDaFirstDate(LocalDate.now());

        try(Connection conn = Db.getConnection()) {
            CategoryDao dao = new CategoryDao(conn);

            int result = dao.insertCategory(category);
            if(result < 1){
                // 실패 메시지와 함께 리다이렉트
                req.setAttribute("message","InsertFail");
                req.setAttribute("type","error");
            }
            // 성공 메시지와 함께 리다이렉트
            req.setAttribute("message","InsertSuccess");
            req.setAttribute("type","success");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "categoryManagement.do";
    }
}