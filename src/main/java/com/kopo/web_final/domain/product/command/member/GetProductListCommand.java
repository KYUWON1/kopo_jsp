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

public class GetProductListCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        System.out.println("GET: GetProductListCommand, categoryId : " + req.getParameter("categoryId"));

        String categoryId = req.getParameter("categoryId");


        try(Connection conn = Db.getConnection()) {
            ProductDao productDao = new ProductDao(conn);
            List<ProductDisplayDto> productListWithCategory;
            //카테고리 없을때
            if(categoryId == null || categoryId.isEmpty()){
                productListWithCategory = productDao.getAllProductListWithCategory();
            }
            // 있을때
            else{
                productDao.getProductListByCategoryId(categoryId);
                productListWithCategory = productDao.getProductListByCategoryId(categoryId);
            }

            CategoryDao categoryDao = new CategoryDao(conn);
            List<Category> categoryList = categoryDao.getCategoryList();


            req.setAttribute("categoryList", categoryList);
            req.setAttribute("productListWithCategory", productListWithCategory);
        } catch (Exception e) {
            e.printStackTrace();
            return "/error/500.jsp";
        }
        return "/product/main_page.jsp";
    }
}
