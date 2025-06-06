package com.kopo.web_final.domain.product.command.member;

import com.kopo.web_final.Command;
import com.kopo.web_final.domain.category.dao.CategoryDao;
import com.kopo.web_final.domain.category.model.Category;
import com.kopo.web_final.domain.product.dao.ProductDao;
import com.kopo.web_final.domain.product.dto.ProductDisplayDto;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

public class SortProductListCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        System.out.println("GET: SortProductListCommand");

        String keyword = req.getParameter("keyword");
        String sort = req.getParameter("sort");
        String categoryId = req.getParameter("categoryId");
        int category = 0;
        if (categoryId != null && !categoryId.isEmpty()) {
            category = Integer.parseInt(categoryId);
        }

        try(Connection conn = Db.getConnection()){
            ProductDao dao = new ProductDao(conn);

            List<ProductDisplayDto> filteredProductList = dao.getFilteredProductList(category, keyword, sort);
            req.setAttribute("productListWithCategory",filteredProductList);

            CategoryDao categoryDao = new CategoryDao(conn);
            List<Category> categoryList = categoryDao.getCategoryList();

            req.setAttribute("categoryList", categoryList);
        } catch (Exception e) {
            e.printStackTrace();
            return "/error/500.jsp";
        }

        return "/product/main_page.jsp";
    }
}
