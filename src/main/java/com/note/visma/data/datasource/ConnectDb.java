package com.note.visma.data.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectDb {

    public static void initialize() {

        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","password");

            if(connection!=null) {
                System.out.println("Connection Established!");
                Statement statement = connection.createStatement();
                DbConnect.setConnection(connection);

                String sql = """
                CREATE TABLE IF NOT EXISTS ansatte (
                    ansatt_id SERIAL PRIMARY KEY,
                    fornavn VARCHAR(100) NOT NULL,
                    etternavn VARCHAR(100) NOT NULL
                );

                CREATE TABLE IF NOT EXISTS stillinger (
                    stilling_id SERIAL PRIMARY KEY,
                    stilling_navn VARCHAR(100) NOT NULL,
                    ansatt_id INTEGER,
                    start_dato DATE NOT NULL,
                    slutt_dato DATE NOT NULL,
                    FOREIGN KEY (ansatt_id) REFERENCES ansatte(ansatt_id)
                );
                
                CREATE TABLE IF NOT EXISTS oppgaver (
                    oppgave_id SERIAL PRIMARY KEY,
                    oppgave_navn VARCHAR(100) NOT NULL,
                    ansatt_id INTEGER,
                    start_dato DATE NOT NULL,
                    slutt_dato DATE NOT NULL,
                    FOREIGN KEY (ansatt_id) REFERENCES ansatte(ansatt_id)
                );
               
                INSERT INTO ansatte (fornavn, etternavn) VALUES ('Ola', 'Nordmann') ON CONFLICT DO NOTHING;
                INSERT INTO ansatte (fornavn, etternavn) VALUES ('Kari', 'Hansen') ON CONFLICT DO NOTHING;
               
                INSERT INTO stillinger (stilling_navn, ansatt_id, start_dato, slutt_dato) 
                VALUES ('Utvikler', 1, '2025-01-01', '2025-12-31') ON CONFLICT DO NOTHING;

                INSERT INTO stillinger (stilling_navn, ansatt_id, start_dato, slutt_dato) 
                VALUES ('Tester', 2, '2025-03-01', '2025-09-30') ON CONFLICT DO NOTHING;

                INSERT INTO oppgaver (oppgave_navn, ansatt_id, start_dato, slutt_dato) 
                VALUES ('Implementere feature X', 1, '2025-05-05', '2025-06-06') ON CONFLICT DO NOTHING;

                INSERT INTO oppgaver (oppgave_navn, ansatt_id, start_dato, slutt_dato)
                VALUES ('Test feature Y', 2, '2025-04-15', '2025-06-06') ON CONFLICT DO NOTHING;

                INSERT INTO oppgaver (oppgave_navn, ansatt_id, start_dato, slutt_dato) 
                VALUES ('Test feature Z', 2, '2026-01-10', '2026-04-10') ON CONFLICT DO NOTHING;
                """;

                statement.execute(sql);
                System.out.println("Schema created.");

            }else {
                System.out.println("Connection Not Established!");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
