package com.kopo.web_final.category.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.category.dao.CategoryDao;
import com.kopo.web_final.category.model.Category;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;

@WebServlet(name = "CategoryUpdateController", value = "/admin/category-update")
public class CategoryUpdateCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        Category category = new Category();
        String noCategory = req.getParameter("nbCategory");

        // nbParentCategory 처리
        String parentCategoryStr = req.getParameter("nbParentCategory");
        System.out.println(req.getParameter("nbParentCategory"));
        if (parentCategoryStr != null && !parentCategoryStr.isEmpty()) {
            category.setNbParentCategory(Integer.parseInt(parentCategoryStr));
        } else {
            category.setNbParentCategory(0);
        }

        category.setNmCategory(req.getParameter("nmCategory"));
        category.setNmFullCategory(req.getParameter("nmFullCategory"));
        category.setNmExplain(req.getParameter("nmExplain"));
        category.setCnLevel(Integer.parseInt(req.getParameter("cnLevel")));
        category.setCnOrder(Integer.parseInt(req.getParameter("cnOrder")));

        // ynUse 값을 ynUser 대신 정확하게 처리
        String ynUse = req.getParameter("ynUse");
        category.setYnUse(ynUse); // "Y" 또는 "N"


        try(Connection conn = Db.getConnection()) {
            CategoryDao dao = new CategoryDao(conn);

            int result = dao.updateCategory(noCategory,category);
            if(result < 1){
                // 실패 메시지와 함께 리다이렉트
                req.setAttribute("message","UpdateFail");
                req.setAttribute("type","error");
            }
            // 성공 메시지와 함께 리다이렉트
            req.setAttribute("message","UpdateSuccess");
            req.setAttribute("type","success");

        } catch (Exception e) {
            e.printStackTrace();
            return "categoryManagement.do";
        }
        return "categoryManagement.do";
    }
}
