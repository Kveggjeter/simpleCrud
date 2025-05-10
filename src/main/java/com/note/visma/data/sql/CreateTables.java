package com.note.visma.data.sql;

public enum CreateTables {
    CREATE_TABLES("""
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
                                ON DELETE CASCADE
                            );
                            CREATE TABLE IF NOT EXISTS oppgaver (
                                oppgave_id SERIAL PRIMARY KEY,
                                oppgave_navn VARCHAR(100) NOT NULL,
                                ansatt_id INTEGER,
                                start_dato DATE NOT NULL,
                                slutt_dato DATE NOT NULL,
                                FOREIGN KEY (ansatt_id) REFERENCES ansatte(ansatt_id)
                                ON DELETE CASCADE
                            );
            """
    );

    private final String sql;

    CreateTables(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
