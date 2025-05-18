package com.kopo.web_final.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql .SQLException;

public class Db {
    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@dinkdb_medium?TNS_ADMIN=C:/dev/web_final/Wallet_DinkDB";
    private static final String USER = "DA2507";
    private static final String PASSWORD = "Data2507";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Oracle JDBC 드라이버를 로드할 수 없습니다.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
