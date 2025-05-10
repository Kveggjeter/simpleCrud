package com.note.visma.data.sql;

public enum OppgaveSQL {
    TABLE("oppgaver"),
    GET_ALL("SELECT * FROM oppgaver"),
    GET_BY_ID("SELECT * FROM oppgaver WHERE oppgave_id = ?"),
    INSERT("INSERT INTO oppgaver (oppgave_navn, ansatt_id, start_dato, slutt_dato) VALUES (? ,? ,? ,?)"),
    GET_SEARCH("SELECT * FROM oppgaver WHERE CAST(oppgave_id as TEXT) ILIKE ?"
            + " OR oppgave_navn ILIKE ? OR CAST(ansatt_id AS TEXT) ILIKE ? OR CAST(start_dato as TEXT) ILIKE ?"
            + " OR CAST(slutt_dato as TEXT) ILIKE ?"),
    GET_BY_ANSATT_ID("SELECT * FROM oppgaver WHERE ansatt_id = ?"),
    GET_ALL_WITHOUT_VALUE("SELECT * FROM oppgaver WHERE ansatt_id IS NULL"),
    UPDATE("UPDATE oppgaver SET ansatt_id = ? WHERE oppgave_id = ?"),
    DELETE("DELETE FROM oppgaver WHERE oppgave_id = ?");


    private final String sql;

    OppgaveSQL(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
