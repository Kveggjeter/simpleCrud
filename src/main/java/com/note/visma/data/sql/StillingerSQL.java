package com.note.visma.data.sql;

public enum StillingerSQL {
    TABLE("stillinger"),
    GET_ALL("SELECT * FROM stillinger"),
    GET_BY_ID("SELECT * FROM stillinger WHERE stilling_id = ?"),
    INSERT("INSERT INTO stillinger (stilling_navn, ansatt_id, start_dato, slutt_dato) VALUES (?, ?, ? , ?)"),
    GET_SEARCH("SELECT * FROM stillinger WHERE CAST(stilling_id as TEXT) ILIKE ?"
            + " OR stilling_navn ILIKE ? OR CAST(ansatt_id AS TEXT) ILIKE ? OR CAST(start_dato as TEXT) ILIKE ?"
            + " OR CAST(slutt_dato as TEXT) ILIKE ?"),
    GET_BY_ANSATT_ID("SELECT * FROM stillinger WHERE ansatt_id = ?"),
    GET_ALL_WITHOUT_VALUE("SELECT * FROM stillinger WHERE ansatt_id IS NULL"),
    UPDATE("UPDATE stillinger SET ansatt_id = ? WHERE stilling_id = ?"),
    DELETE("DELETE FROM stillinger WHERE stilling_id = ?");


    private final String sql;

    StillingerSQL(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

}
