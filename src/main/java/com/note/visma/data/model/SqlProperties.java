package com.note.visma.data.model;

public record SqlProperties(
        String table,
        String getAllSql,
        String getByIdSql,
        String insertSql,
        String getSearchSql,
        String getByAnsattIdSql,
        String getAllWithoutValueSql,
        String updateSql,
        String deleteSql
) {
}
