package com.note.visma.data.datasource;

import com.note.visma.data.sql.CreateTables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectDb {

    public static void initialize() {
        String sql = CreateTables.CREATE_TABLES.getSql();

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","password");

            if(connection!=null) {
                System.out.println("Kontakt med databasen er opprettet");
                Statement statement = connection.createStatement();
                DbConnect.setConnection(connection);
                statement.execute(sql);
                System.out.println("Schema er lagd.");

            }else {
                System.out.println("Vi får ikke kontakt med databasen.");
            }

        } catch (Exception e) {
            System.err.println("Dessverre ikke klart å koble oss til databasen: " + e.getMessage());
        }
    }
}
