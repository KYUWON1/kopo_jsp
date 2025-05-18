package com.kopo.web_final.category.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.category.dao.CategoryDao;
import com.kopo.web_final.category.model.Category;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

public class GetCategoryManagementListCommand implements Command {
    public GetCategoryManagementListCommand() {
        super();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        System.out.println("GET: GetCategoryManagementListCommand");

        try(Connection conn = Db.getConnection()) {
            CategoryDao dao = new CategoryDao(conn);
            List<Category> categoryList = dao.getCategoryList();

            req.setAttribute("categoryList", categoryList);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message","카테고리 로드중 에러가 발생했습니다.");
            return "/admin/category_management.jsp";
        }

        return "/admin/category_management.jsp";
    }
}
