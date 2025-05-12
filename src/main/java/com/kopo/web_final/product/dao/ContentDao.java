package com.kopo.web_final.product.dao;

import com.kopo.web_final.exception.MemberException;
import com.kopo.web_final.product.dto.ImageDisplayDto;
import com.kopo.web_final.product.model.Content;
import com.kopo.web_final.type.ErrorType;

import java.sql.*;
import java.time.LocalDate;

public class ContentDao {
    private Connection conn;

    public ContentDao(Connection conn) {
        this.conn = conn;
    }


    public String insertContent(Content content) throws MemberException {
        String contentId = null;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT SEQ_TB_CONTENT_ID.NEXTVAL FROM DUAL");
            if (rs.next()) {
                long seq = rs.getLong(1);
                contentId = String.format("FILE_%06d", seq); // → FILE_000001
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 디버깅을 위해 예외 출력
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }

        String sql = "INSERT INTO TB_CONTENT (" +
                "ID_FILE, NM_ORG_FILE, BO_SAVE_FILE, " +
                "DA_SAVE, CN_HIT, NO_REGISTER, DA_FIRST_DATE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contentId);
            pstmt.setString(2, content.getNmOrgFile());
            pstmt.setBlob(3, content.getBoSaveFile().getBinaryStream()); // BLOB 처리
            pstmt.setDate(4, toSqlDate(content.getDaSave()));
            pstmt.setInt(5, content.getCnHit());
            pstmt.setString(6, content.getNoRegister());
            pstmt.setDate(7, toSqlDate(content.getDaFirstDate()));

            if(pstmt.executeUpdate() > 0)
                return contentId;
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace(); // 디버깅을 위해 예외 출력
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }
    }

    private Date toSqlDate(LocalDate localDate) {
        return localDate != null ? java.sql.Date.valueOf(localDate) : null;
    }

    public ImageDisplayDto getContentById(String id) throws MemberException {
        String sql = "SELECT BO_SAVE_FILE,CN_HIT FROM TB_CONTENT WHERE ID_FILE = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            ImageDisplayDto imageDisplayDto = new ImageDisplayDto();
            if (rs.next()) {
                Blob blob = rs.getBlob("BO_SAVE_FILE");

                imageDisplayDto.setBlob(rs.getBlob("BO_SAVE_FILE"));
                imageDisplayDto.setCount(rs.getInt("CN_HIT"));
            }
            return imageDisplayDto;
        } catch (SQLException e) {
            e.printStackTrace(); // 디버깅을 위해 예외 출력
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }
    }

}
