package com.kopo.web_final.product.command.member;

import com.kopo.web_final.Command;
import com.kopo.web_final.category.dao.CategoryDao;
import com.kopo.web_final.category.model.Category;
import com.kopo.web_final.product.dao.ProductDao;
import com.kopo.web_final.product.dto.ProductDisplayDto;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

public class SortProductListCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        String keyword = req.getParameter("keyword");
        String sort = req.getParameter("sort");
        String categoryId = req.getParameter("categoryId");
        int category = 0;
        if(categoryId != null || !categoryId.isEmpty()){
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
            return "/500.do";
        }

        return "/product/main_page.jsp";
    }
}
