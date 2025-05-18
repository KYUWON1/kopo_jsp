package com.kopo.web_final.product.command.admin;

import com.kopo.web_final.Command;
import com.kopo.web_final.product.dao.CategoryProductMappingDao;
import com.kopo.web_final.product.dao.ContentDao;
import com.kopo.web_final.product.dao.ProductDao;
import com.kopo.web_final.product.model.Content;
import com.kopo.web_final.product.model.Product;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.nio.file.Paths;
import java.sql.Connection;
import java.time.LocalDate;

public class ProductUpdateCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        System.out.println("UPDATE: ProductUpdateCommand , productId : " + req.getParameter("noProduct"));
        String fileId = req.getParameter("idFile");

        String productId = req.getParameter("noProduct");
        int categoryID = Integer.parseInt(req.getParameter("nbCategory"));

        Product product = new Product();
        product.setNmProduct(req.getParameter("nmProduct"));
        product.setNmDetailExplain(req.getParameter("nmDetailExplain"));
        product.setIdFile(req.getParameter("idFile")); // 기존 파일 ID 값
        product.setDtStartDate(req.getParameter("dtStartDate").replace("-", ""));
        product.setDtEndDate(req.getParameter("dtEndDate").replace("-", ""));
        product.setQtCustomer(Integer.parseInt(req.getParameter("qtCustomer")));
        product.setQtSalePrice(Integer.parseInt(req.getParameter("qtSalePrice")));
        product.setQtStock(Integer.parseInt(req.getParameter("qtStock")));
        product.setQtDeliveryFee(Integer.parseInt(req.getParameter("qtDeliveryFee")));

        // 컨텐츠 테이블 생성
        Part filePart = req.getPart("productImage");
        String submittedFileName = filePart.getSubmittedFileName();

        try (Connection conn = Db.getConnection()) {
            conn.setAutoCommit(false);

            // 컨텐츠 생성
            if (submittedFileName != null && !submittedFileName.isEmpty()) {
                // 새 파일이 있는 경우에만 콘텐츠 업데이트 수행
                String fileName = Paths.get(submittedFileName).getFileName().toString();

                ContentDao contentDao = new ContentDao(conn);
                Content content = new Content();
                content.setBoSaveFile(new javax.sql.rowset.serial.SerialBlob(filePart.getInputStream().readAllBytes()));
                content.setNmOrgFile(fileName);
                content.setDaSave(LocalDate.now());

                    int contentResult = contentDao.updateContent(fileId, content);
                if (contentResult < 1) {
                    conn.rollback();
                    req.setAttribute("message", "컨텐츠 수정에 실패했습니다.");
                    req.setAttribute("type", "error");
                    return "productManagement.do";
                }
            }


            ProductDao dao = new ProductDao(conn);
            int result1 = dao.updateProduct(productId, product);
            if (result1 < 1) {
                conn.rollback();
                req.setAttribute("message", "상품 정보 수정에 실패했습니다.");
                req.setAttribute("type", "error");
                return "productManagement.do";
            }

            CategoryProductMappingDao cpmDao = new CategoryProductMappingDao(conn);
            int result2 = cpmDao.updateCpMapping(productId, categoryID);
            if (result2 < 1) {
                conn.rollback();
                req.setAttribute("message", "카테고리 정보 수정에 실패했습니다.");
                req.setAttribute("type", "error");
                return "productManagement.do";
            }

            conn.commit();
            req.setAttribute("message", "상품이 성공적으로 업데이트되었습니다.");
            req.setAttribute("type", "success");

        } catch (Exception e) {
            req.setAttribute("message", "오류 발생: " + e.getMessage());
            req.setAttribute("type", "error");
            e.printStackTrace();
        }

        return "productManagement.do";
    }
}
