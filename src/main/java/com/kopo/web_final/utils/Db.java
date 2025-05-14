package com.kopo.web_final.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class Db {
    private static DataSource ds;

    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/OracleDB");
        } catch (Exception e) {
            // 예외를 RuntimeException으로 감싸서 던지면 예외 전파됨 (선택)
            throw new RuntimeException("DB 초기화 실패", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return ds.getConnection();
    }

}
