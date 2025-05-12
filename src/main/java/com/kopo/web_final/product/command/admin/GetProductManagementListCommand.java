package com.kopo.web_final.product.command.admin;

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

public class GetProductManagementListCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        try(Connection conn = Db.getConnection()) {
            ProductDao productDao = new ProductDao(conn);
            List<ProductDisplayDto> productListWithCategory = productDao.getAllProductListWithCategory();

            CategoryDao categoryDao = new CategoryDao(conn);
            List<Category> categoryList = categoryDao.getCategoryList();

            req.setAttribute("categoryList", categoryList);
            req.setAttribute("productListWithCategory", productListWithCategory);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return "/admin/product_management.jsp";
    }
}
