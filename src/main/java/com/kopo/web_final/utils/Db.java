package com.kopo.web_final.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

public class Db {

    private static final String URL = "jdbc:oracle:thin:@dinkdb_medium?TNS_ADMIN=C:/dev/web_final/Wallet_DinkDB";
    private static final String USERNAME = "DA2507";
    private static final String PASSWORD = "Data2507";

    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver"); // 드라이버 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
