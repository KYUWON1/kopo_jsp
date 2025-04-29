package com.kopo.web_final.member.model;

import com.kopo.web_final.type.UserStatus;
import com.kopo.web_final.type.UserType;

import java.time.LocalDate;

public class Member {
    private String idUser;         // 사용자 ID (이메일 주소)
    private String nmUser;         // 사용자 명
    private String nmPaswd;        // 비밀번호
    private String nmEncPaswd;     // 암호 비밀번호
    private String nmMobile;       // 휴대전화
    private String nmEmail;        // 이메일
    private String stStatus;       // 상태 (ST00: 요청, ST01: 정상, ST02: 해지)
    private String cdUserType;     // 사용자 구분 코드 (10: 일반, 20: 관리자)
    private String noRegister;     // 최초 등록자 ID
    private LocalDate daFirstDate;      // 최초 등록 일시

    public Member() {
    }

    public static Member buildMember(String nmEmail, String nmUser, String nmPaswd, String noMobile) {
        Member member = new Member();
        member.setNmUser(nmUser);
        member.setNmPaswd(nmPaswd);
        member.setNoMobile(noMobile);
        member.setNmEmail(nmEmail);

        member.setStStatus(UserStatus.ST00.toString());
        member.setCdUserType(UserType._01.toString());

        member.setDaFirstDate(LocalDate.now());

        return member;
    }

    public Member(String idUser, String nmUser, String nmPaswd, String nmEncPaswd, String noMobile, String nmEmail, String stStatus, String cdUserType, String noRegister, LocalDate daFirstDate) {
        this.idUser = idUser;
        this.nmUser = nmUser;
        this.nmPaswd = nmPaswd;
        this.nmEncPaswd = nmEncPaswd;
        this.nmMobile = noMobile;
        this.nmEmail = nmEmail;
        this.stStatus = stStatus;
        this.cdUserType = cdUserType;
        this.noRegister = noRegister;
        this.daFirstDate = daFirstDate;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNmUser() {
        return nmUser;
    }

    public void setNmUser(String nmUser) {
        this.nmUser = nmUser;
    }

    public String getNmPaswd() {
        return nmPaswd;
    }

    public void setNmPaswd(String nmPaswd) {
        this.nmPaswd = nmPaswd;
    }

    public String getNmEncPaswd() {
        return nmEncPaswd;
    }

    public void setNmEncPaswd(String nmEncPaswd) {
        this.nmEncPaswd = nmEncPaswd;
    }

    public String getNoMobile() {
        return nmMobile;
    }

    public void setNoMobile(String nmMobile) {
        this.nmMobile = nmMobile;
    }

    public String getNmEmail() {
        return nmEmail;
    }

    public void setNmEmail(String nmEmail) {
        this.nmEmail = nmEmail;
    }

    public String getStStatus() {
        return stStatus;
    }

    public void setStStatus(String stStatus) {
        this.stStatus = stStatus;
    }

    public String getCdUserType() {
        return cdUserType;
    }

    public void setCdUserType(String cdUserType) {
        this.cdUserType = cdUserType;
    }

    public String getNoRegister() {
        return noRegister;
    }

    public void setNoRegister(String noRegister) {
        this.noRegister = noRegister;
    }

    public LocalDate getDaFirstDate() {
        return daFirstDate;
    }

    public void setDaFirstDate(LocalDate daFirstDate) {
        this.daFirstDate = daFirstDate;
    }
}
