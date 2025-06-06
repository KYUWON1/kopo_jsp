package com.kopo.web_final.domain.member.dao;

import com.kopo.web_final.domain.member.model.Member;
import com.kopo.web_final.type.UserStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberDao{

    private Connection conn;

    public MemberDao(Connection conn) {
        this.conn = conn;
    }

    public int insertMember(Member member) throws SQLException {
        String sql = "INSERT INTO tb_user (id_user, nm_user, nm_paswd, nm_enc_paswd, no_mobile, nm_email, st_status, cd_user_type, no_register, da_first_date)"
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
            e.printStackTrace();
            throw e;
        }

    }

    public int updateMember(String idUser,Member member) throws SQLException {
        String sql = "update tb_user " +
                "set " +
                "nm_email = ?, " +
                "nm_user = ?, " +
                "no_mobile = ? " +
                "WHERE id_user = ? ";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,member.getNmEmail());
            pstmt.setString(2,member.getNmUser());
            pstmt.setString(3,member.getNoMobile());
            pstmt.setString(4,idUser);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    public int updateMemberPassword(String idUser,Member member) throws SQLException {
        String sql = "update tb_user " +
                "set " +
                "nm_paswd = ?, " +
                "nm_enc_paswd = ? " +
                "WHERE id_user = ? ";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,member.getNmPaswd());
            pstmt.setString(2,member.getNmEncPaswd());
            pstmt.setString(3,idUser);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    public int checkEmailExist(String email) throws SQLException {
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
            e.printStackTrace();
            throw e;
        }
    }

    public Member findByEmail(String email) throws SQLException {
        String sql = "select * FROM tb_user where nm_email = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                return Member.buildMember(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Member> getActiveMemberList(UserStatus status) throws SQLException {
        String sql = "select * from tb_user where st_status = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, status.toString());

            ResultSet rs = pstmt.executeQuery();
            List<Member> memberList = new ArrayList<>();
            while(rs.next()){
                memberList.add(Member.buildMember(rs));
            }
            return memberList;
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

    public int updateStatusInit(String adminId, String idUser, UserStatus userStatus) throws SQLException {
        String sql = "UPDATE tb_user " +
                " SET st_status = ?, no_register = ?, da_first_date = ?  " +
                " WHERE id_user = ? ";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,userStatus.toString());
            pstmt.setString(2,adminId);
            pstmt.setDate(3, toSqlDate(LocalDate.now()));
            pstmt.setString(4,idUser);

            return pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

    public int updateStatus(String idUser, UserStatus userStatus) throws SQLException {
        String sql = "UPDATE tb_user " +
                " SET st_status = ? " +
                " WHERE id_user = ? ";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,userStatus.toString());
            pstmt.setString(2,idUser);

            return pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

    public int updateMemberAuth(String targetId, Member member) throws SQLException {
        String sql = "UPDATE tb_user " +
                " SET st_status = ?, cd_user_type = ? " +
                " WHERE id_user = ? ";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,member.getStStatus());
            pstmt.setString(2,member.getCdUserType());
            pstmt.setString(3,targetId);

            return pstmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

    public Member findById(String id) throws SQLException {
        String query = "SELECT * FROM tb_user WHERE id_user = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Member.buildMember(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return null;
    }

    private java.sql.Date toSqlDate(LocalDate localDate) {
        return localDate != null ? java.sql.Date.valueOf(localDate) : null;
    }


}
