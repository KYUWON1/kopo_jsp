package com.kopo.web_final.member.dao;

import com.kopo.web_final.member.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MemberDao {

    private Connection conn;

    public MemberDao(Connection conn) {
        this.conn = conn;
    }

    public int insertMember(Member member) {
        String sql = "INSERT INTO tb_user (id_user, nm_user, nm_paswd, nm_enc_paswd, nm_mobile, nm_email, st_status, cd_user_type, no_register, da_first_date)"
                +" VALUES (" +
                " 'U_' || LPAD(seq_tb_user_id.NEXTVAL,6,'0'), ?, ?, ?, ?, ?, ?, ?, ?, ?" +
                ")";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,member.getNmUser());
            pstmt.setString(2,member.getNmPaswd());
            pstmt.setString(3,member.getNmPaswd());
            pstmt.setString(4,member.getNoMobile());
            pstmt.setString(5,member.getNmEmail());
            pstmt.setString(6,member.getStStatus());
            pstmt.setString(7,member.getCdUserType());
            pstmt.setString(8,member.getNoRegister());
            pstmt.setDate(9, toSqlDate(member.getDaFirstDate()));

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int checkEmailExist(String email) {
        String sql = "select COUNT(*) from tb_user" +
                " WHERE nm_email = ?";
        
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,email);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                return rs.getInt(1);
            }

            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Member findByEmail(String email) {
        String sql = "select * FROM tb_user where nm_email = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                return new Member(
                    rs.getString("id_user"),
                        rs.getString("nm_user"),
                        rs.getString("nm_paswd"),
                        rs.getString("nm_enc_paswd"),
                        rs.getString("nm_mobile"),
                        rs.getString("nm_email"),
                        rs.getString("st_status"),
                        rs.getString("cd_user_type"),
                        rs.getString("no_register"),
                        fromSqlDate(rs.getDate("da_first_date"))
                );
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private java.sql.Date toSqlDate(LocalDate localDate) {
        return localDate != null ? java.sql.Date.valueOf(localDate) : null;
    }

    private LocalDate fromSqlDate(java.sql.Date sqlDate) {
        return sqlDate != null ? sqlDate.toLocalDate() : null;
    }
}
