package com.note.visma.data.datasource;

import java.sql.Connection;

public class DbConnect {

    private static Connection connection;

    public static void setConnection(Connection conn) {
        connection = conn;
    }

    public static Connection getConnection() {
        return connection;
    }
}
