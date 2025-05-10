package com.note.visma.data.repository;

import com.note.visma.data.model.Ansatt;
import com.note.visma.data.model.SqlProperties;
import com.note.visma.data.sql.AnsattSQL;
import com.note.visma.domain.model.AnsattDom;
import com.note.visma.domain.repository.IAnsattRepository;
import java.sql.*;
import java.util.List;

public class AnsattRepository extends CRUD<Ansatt, AnsattDom> implements IAnsattRepository {

    public AnsattRepository() {;}

    @Override
    protected void setInsertValues(PreparedStatement stmt, Ansatt ansatt) {
        try {
            stmt.setString(1, ansatt.fornavn());
            stmt.setString(2, ansatt.etternavn());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void setSqlProperties() {
        sqlProperties = new SqlProperties(
                AnsattSQL.TABLE.getSql(),
                AnsattSQL.GET_ALL.getSql(),
                AnsattSQL.GET_BY_ID.getSql(),
                AnsattSQL.INSERT.getSql(),
                AnsattSQL.GET_SEARCH.getSql(),
                AnsattSQL.GET_BY_ANSATT_ID.getSql(),
                AnsattSQL.GET_ALL_WITHOUT_VALUE.getSql(),
                AnsattSQL.UPDATE.getSql(),
                AnsattSQL.DELETE.getSql()
        );
    }

    @Override
    protected Ansatt mapper(ResultSet rs) throws SQLException {
        return new Ansatt(
                rs.getInt("ansatt_id"),
                rs.getString("fornavn"),
                rs.getString("etternavn")
        );
    }

    @Override
    public void insert(AnsattDom ansatt) throws SQLException {
        Ansatt dataAnsatt = Ansatt.fromDomain(ansatt);
        addToDb(dataAnsatt);
    }

    @Override
    public List<AnsattDom> getAll() throws SQLException {
       return fetchAll();
    }

    @Override
    public List<AnsattDom> getSearch(String search) throws SQLException {
        return search(search);
    }

    @Override
    public List<AnsattDom> getAllAnsatt() throws SQLException {
        return fetchAll(sqlProperties.getByAnsattIdSql());
    }

    @Override
    public AnsattDom getById(int id) throws SQLException {
       return fetchOneById(id);
    }

    @Override
    public void deleteById(int id) throws SQLException {
        delete(id);
    }

}
