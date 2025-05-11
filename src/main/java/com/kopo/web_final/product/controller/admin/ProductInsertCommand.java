package com.kopo.web_final.product.controller.admin;

import com.kopo.web_final.Command;
import com.kopo.web_final.product.dao.CategoryProductMappingDao;
import com.kopo.web_final.product.dao.ProductDao;
import com.kopo.web_final.product.model.CategoryProductMapping;
import com.kopo.web_final.product.model.Product;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.time.LocalDate;

public class ProductInsertCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        Product product = new Product();
        product.setNmProduct(req.getParameter("nmProduct"));
        product.setNmDetailExplain(req.getParameter("nmDetailExplain"));
        product.setIdFile("none");
        product.setDtStartDate(req.getParameter("dtStartDate").replace("-", ""));
        product.setDtEndDate(req.getParameter("dtEndDate").replace("-", ""));
        product.setQtCustomer(Integer.parseInt(req.getParameter("qtCustomer")));
        product.setQtSalePrice(Integer.parseInt(req.getParameter("qtSalePrice")));
        product.setQtStock(Integer.parseInt(req.getParameter("qtStock")));
        product.setQtDeliveryFee(Integer.parseInt(req.getParameter("qtDeliveryFee")));
        product.setNoRegister(req.getParameter("noRegister"));
        product.setDaFirstDate(LocalDate.now());

        CategoryProductMapping categoryProductMapping = new CategoryProductMapping();
        categoryProductMapping.setNbCategory(Integer.parseInt(req.getParameter("nbCategory")));
        categoryProductMapping.setCnOrder(Integer.parseInt(req.getParameter("cnOrder")));
        categoryProductMapping.setDaFirstDate(LocalDate.now());
        categoryProductMapping.setNoRegister(req.getParameter("noRegister"));

        try (Connection conn = Db.getConnection()) {
            conn.setAutoCommit(false);

            ProductDao dao = new ProductDao(conn);
            String productId = dao.insertProduct(product);
            if (productId == null || productId.isEmpty()) {
                conn.rollback();
                req.setAttribute("message", "상품 등록에 실패했습니다.");
                req.setAttribute("type", "error");
                return "productManagement.do";
            }

            CategoryProductMappingDao cpMappingDao = new CategoryProductMappingDao(conn);
            categoryProductMapping.setNoProduct(productId);
            int ctmResult = cpMappingDao.insertCpMapping(categoryProductMapping);
            if (ctmResult < 1) {
                conn.rollback();
                req.setAttribute("message", "카테고리 매핑 등록에 실패했습니다.");
                req.setAttribute("type", "error");
                return "productManagement.do";
            }

            conn.commit();
            req.setAttribute("message", "상품이 성공적으로 추가되었습니다.");
            req.setAttribute("type", "success");

        } catch (Exception e) {
            req.setAttribute("message", "오류 발생: " + e.getMessage());
            req.setAttribute("type", "error");
            e.printStackTrace();
            return "productManagement.do";
        }

        return "productManagement.do"; // 또는 실제 뷰 경로
    }
}
