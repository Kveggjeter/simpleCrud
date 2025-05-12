package com.note.visma.data.repository;

import com.note.visma.data.model.Oppgave;
import com.note.visma.data.model.SqlProperties;
import com.note.visma.data.sql.OppgaveSQL;
import com.note.visma.domain.model.OppgaveDom;
import com.note.visma.domain.repository.IOppgaveRepository;
import java.sql.*;
import java.util.List;

public class OppgaveRepository extends CRUD<Oppgave, OppgaveDom> implements IOppgaveRepository {

    public OppgaveRepository() {
    }


    @Override
    protected void setInsertValues(PreparedStatement stmt, Oppgave oppgave) {
        try {
            stmt.setString(1, oppgave.oppgaveNavn());
            if(oppgave.ansattID() != null)
                stmt.setInt(2, oppgave.ansattID());
            else stmt.setNull(2, java.sql.Types.INTEGER);
            stmt.setDate(3, oppgave.startDato());
            stmt.setDate(4, oppgave.sluttDato());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void insert(OppgaveDom oppgave) throws SQLException {
        Oppgave dataOppgave = Oppgave.fromDomain(oppgave);
        addToDb(dataOppgave);
    }

    @Override
    protected void setSqlProperties() {
        sqlProperties = new SqlProperties(
                OppgaveSQL.TABLE.getSql(),
                OppgaveSQL.GET_ALL.getSql(),
                OppgaveSQL.GET_BY_ID.getSql(),
                OppgaveSQL.INSERT.getSql(),
                OppgaveSQL.GET_SEARCH.getSql(),
                OppgaveSQL.GET_BY_ANSATT_ID.getSql(),
                OppgaveSQL.GET_ALL_WITHOUT_VALUE.getSql(),
                OppgaveSQL.UPDATE.getSql(),
                OppgaveSQL.DELETE.getSql()
        );
    }

    @Override
    protected Oppgave mapper(ResultSet rs) throws SQLException {
       return new Oppgave(
                rs.getInt("oppgave_id"),
                rs.getString("oppgave_navn"),
                rs.getInt("ansatt_id"),
                rs.getDate("start_dato"),
                rs.getDate("slutt_dato")
        );
    }

    @Override
    public List<OppgaveDom> getSearch(String search) throws SQLException {
        return search(search);
    }

    @Override
    public List<OppgaveDom> getAll() throws SQLException {
        return fetchAll();
    }

    @Override
    public OppgaveDom getById(int id) throws SQLException {
      return fetchOneById(id);
    }

    public void updateById(int ansattID, int oppgaveID) throws SQLException {
        update(ansattID, oppgaveID);
    }

    @Override
    public void deleteById(int id) throws SQLException {
        delete(id);
    }
}
