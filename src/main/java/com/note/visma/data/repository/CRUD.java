package com.note.visma.data.repository;

import com.note.visma.data.datasource.DbConnect;
import com.note.visma.data.model.DomainConvertible;
import com.note.visma.data.model.SqlProperties;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CRUD<T extends DomainConvertible<E>, E> {

    private final Connection connection;
    protected SqlProperties sqlProperties;

    public CRUD() {
        this.connection = DbConnect.getConnection();
        setSqlProperties();
    }

    protected List<E> fetchAll() throws SQLException {
        return fetchAllFromDb(sqlProperties.getAllSql());
    }

    protected List<E> fetchAll(String sql) throws SQLException {
        return fetchAllFromDb(sql);
    }

    protected List<E> fetchAllBut() throws SQLException {
        return fetchAllFromDb(sqlProperties.getAllWithoutValueSql());
    }

    private List<E> fetchAllFromDb(String sql) throws SQLException {
        List<E> list;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            list = executeQuery(stmt);
        }
        return list;
    }

    protected void addToDb(T t) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(sqlProperties.insertSql())) {
            setInsertValues(stmt, t);
            stmt.executeUpdate();
        }
    }

    protected void update(int firstID, int secondID) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(sqlProperties.updateSql())) {
            stmt.setInt(1, firstID);
            stmt.setInt(2, secondID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Ingen rader oppdatert");
            }
        }
    }

    protected void delete(int ID) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(sqlProperties.deleteSql())) {
            stmt.setInt(1, ID);
            stmt.executeUpdate();
        }
    }

    protected List<E> fetchAllById(int ID) throws SQLException {
        List<E> list;
        try (PreparedStatement stmt = connection.prepareStatement(sqlProperties.getByIdSql())) {
            stmt.setInt(1, ID);
            list = executeQuery(stmt);
        }
        return list;
    }

    protected E fetchOneById(int ID) throws SQLException {
        List<E> list = fetchAllById(ID);
        return list.isEmpty() ? null : list.getFirst();
    }

    protected List<E> search(String search) throws SQLException {
        List<E> list;
        try (PreparedStatement stmt = connection.prepareStatement(sqlProperties.getSearchSql())) {
            String pattern = "%" + search + "%";
            for (int i = 0; i < countParams(sqlProperties.getSearchSql()); i++) {
                stmt.setString(i + 1, pattern);
            }
            list = executeQuery(stmt);
        }
        return list;
    }

    private List<E> executeQuery(PreparedStatement stmt) throws SQLException {
        List<E> list = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapper(rs).toDomain());
            }
        }
        return list;
    }


    private int countParams(String sql) {
        int count = 0;
        for (int i = 0; i < sql.length(); i++) {
            if (sql.charAt(i) == '?')
                count++;
        }
        return count;
    }

    protected abstract void setInsertValues(PreparedStatement stmt, T t);
    protected abstract void setSqlProperties();
    protected abstract T mapper(ResultSet rs) throws SQLException;

}
