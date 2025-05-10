package com.note.visma.data.sql;

public enum AnsattSQL {
    TABLE("ansatte"),
    GET_ALL("SELECT * FROM ansatte"),
    GET_BY_ID("SELECT * FROM ansatte WHERE ansatt_id = ?"),
    INSERT("INSERT INTO ansatte (fornavn, etternavn) VALUES (?, ?)"),
    GET_SEARCH("SELECT * FROM ansatte WHERE fornavn ILIKE ? OR etternavn ILIKE ? OR CAST(ansatt_id AS TEXT) ILIKE ?"),
    GET_BY_ANSATT_ID("SELECT ansatt_id, fornavn, etternavn FROM ansatte WHERE ansatt_id IN (SELECT ansatt_id FROM stillinger)"),
    GET_ALL_WITHOUT_VALUE("SELECT * FROM ansatte WHERE ansatt_id IS NULL"),
    UPDATE("UPDATE ansatte SET fornavn = ? WHERE ansatt_id = ?"),
    DELETE("DELETE FROM ansatte WHERE ansatt_id = ?");

    private final String sql;

    AnsattSQL(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
