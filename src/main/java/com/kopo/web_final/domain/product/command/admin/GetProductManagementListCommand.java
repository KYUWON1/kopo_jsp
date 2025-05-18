package com.kopo.web_final.domain.product.command.admin;

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

public class GetProductManagementListCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        System.out.println( "GET: GetProductManagementListCommand");

        try(Connection conn = Db.getConnection()) {
            ProductDao productDao = new ProductDao(conn);
            List<ProductDisplayDto> productListWithCategory = productDao.getAllProductListWithCategory();

            CategoryDao categoryDao = new CategoryDao(conn);
            List<Category> categoryList = categoryDao.getCategoryList();

            req.setAttribute("categoryList", categoryList);
            req.setAttribute("productListWithCategory", productListWithCategory);
        } catch (Exception e) {
            e.printStackTrace();
            return "/error/500.jsp";
        }

        return "/admin/product_management.jsp";
    }
}
