package com.note.visma.data.repository;

import com.note.visma.data.model.SqlProperties;
import com.note.visma.data.model.Stilling;
import com.note.visma.data.sql.StillingerSQL;
import com.note.visma.domain.model.StillingDom;
import com.note.visma.domain.repository.IStillingRepository;
import java.sql.*;
import java.util.List;

public class StillingRepository extends CRUD<Stilling, StillingDom> implements IStillingRepository {

    public StillingRepository() {
    }

    @Override
    protected void setInsertValues(PreparedStatement stmt, Stilling stilling) {
            try {
                stmt.setString(1, stilling.stillingNavn());
                if (stilling.ansattID() != null)
                    stmt.setInt(2, stilling.ansattID());
                else stmt.setNull(2, java.sql.Types.INTEGER);
                stmt.setDate(3, stilling.startDato());
                stmt.setDate(4, stilling.sluttDato());
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
    }

    @Override
    public void insert(StillingDom stilling) throws SQLException {
        Stilling newStilling = Stilling.fromDomain(stilling);
        addToDb(newStilling);
    }

    @Override
    protected void setSqlProperties() {
        sqlProperties = new SqlProperties(
                StillingerSQL.TABLE.getSql(),
                StillingerSQL.GET_ALL.getSql(),
                StillingerSQL.GET_BY_ID.getSql(),
                StillingerSQL.INSERT.getSql(),
                StillingerSQL.GET_SEARCH.getSql(),
                StillingerSQL.GET_BY_ANSATT_ID.getSql(),
                StillingerSQL.GET_ALL_WITHOUT_VALUE.getSql(),
                StillingerSQL.UPDATE.getSql(),
                StillingerSQL.DELETE.getSql()
        );
    }

    @Override
    protected Stilling mapper(ResultSet rs) throws SQLException {
        return new Stilling(
                rs.getInt("stilling_id"),
                rs.getString("stilling_navn"),
                rs.getInt("ansatt_id"),
                rs.getDate("start_dato"),
                rs.getDate("slutt_dato")
        );
    }

    @Override
    public List<StillingDom> getSearch(String search) throws SQLException {
        return search(search);
    }

    @Override
    public List<StillingDom> getAll() throws SQLException {
       return fetchAll();
    }

    @Override
    public List<StillingDom> getAllByAnsattID(int ID) throws SQLException {
        return fetchAllById(ID);
    }

    @Override
    public StillingDom getByAnsattId(int id) throws SQLException {
        return fetchOneById(id);
    }

    @Override
    public List<StillingDom> getAllStillingWithoutAnsatt() throws SQLException {
        return fetchAllBut();
    }

    @Override
    public void updateById(int ansattID, int stillingID) throws SQLException {
        update(ansattID, stillingID);
    }

    @Override
    public void deleteById(int id) throws SQLException {
        delete(id);
    }
}
