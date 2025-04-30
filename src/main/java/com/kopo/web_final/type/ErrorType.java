package com.kopo.web_final.type;

public enum ErrorType {
    DUPLICATE_ID("아이디 중복입니다."),
    USER_NOT_FOUND("해당 유저는 존재하지않습니다."),
    INTERNAL_ERROR("서버 내부 오류입니다."),
    DB_CONN_FAIL("DB 연결과정에서 에러가 발생했습니다."),
    DB_QUERY_FAIL("쿼리 처리 과정에서 에러가 발생했습니다."),
    UPDATE_FAIL("회원정보 수정에 실패했습니다."),
    INVALID_CREDENTIALS("잘못된 비밀번호입니다.");

    private final String message;

    ErrorType(String msg){
        this.message = msg;
    }
    public String getMessage(){
        return message;
    }
}
