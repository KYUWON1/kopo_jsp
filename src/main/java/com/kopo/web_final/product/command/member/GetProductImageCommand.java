package com.kopo.web_final.product.command.member;

import com.kopo.web_final.Command;
import com.kopo.web_final.product.dao.ContentDao;
import com.kopo.web_final.product.dto.ImageDisplayDto;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;

public class GetProductImageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String id = req.getParameter("id");

        if (id == null || id.isEmpty()) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "이미지 ID가 누락되었습니다.");
            return null;
        }

        try (Connection conn = Db.getConnection()) {
            ContentDao dao = new ContentDao(conn);
            ImageDisplayDto imageDto = dao.getContentById(id);

            if (imageDto == null || imageDto.getBlob() == null) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND, "이미지를 찾을 수 없습니다.");
                return null;
            }

            Blob blob = imageDto.getBlob();
            byte[] imageBytes = blob.getBytes(1, (int) blob.length());

            // MIME 타입을 JPEG으로 설정 (실제 확장자에 따라 변경 가능)
            res.setContentType("image/jpeg");
            res.setContentLength(imageBytes.length);

            // 응답 스트림에 이미지 출력
            try (OutputStream out = res.getOutputStream()) {
                out.write(imageBytes);
                out.flush();
            }

        } catch (Exception e) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "이미지 처리 중 오류 발생");
            e.printStackTrace();
        }

        return null; // 응답은 직접 스트림으로 처리했기 때문에 null 반환
    }
}
