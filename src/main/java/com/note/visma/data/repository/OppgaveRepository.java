package com.note.visma.data.repository;

import com.note.visma.data.datasource.DbConnect;
import com.note.visma.data.model.Ansatt;
import com.note.visma.data.model.Oppgave;
import com.note.visma.domain.model.AnsattDom;
import com.note.visma.domain.model.OppgaveDom;
import com.note.visma.domain.repository.IOppgaveRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OppgaveRepository implements IOppgaveRepository {

    private final Connection connection;

    public OppgaveRepository() {
        this.connection = DbConnect.getConnection();
    }

    @Override
    public void insert(OppgaveDom oppgave) throws SQLException {
        String sql = "INSERT INTO oppgaver (oppgave_navn, ansatt_id, start_dato, slutt_dato) VALUES (?, ?, ?, ?)";
        Oppgave dataOppgave = Oppgave.fromDomain(oppgave);

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, dataOppgave.oppgaveNavn());
            if(dataOppgave.ansattID() != null)
                stmt.setInt(2, dataOppgave.ansattID());
            else stmt.setNull(2, java.sql.Types.INTEGER);
            stmt.setDate(3, dataOppgave.startDato());
            stmt.setDate(4, dataOppgave.startDato());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<OppgaveDom> getSearch(String search) throws SQLException {
        List<OppgaveDom> oppgave = new ArrayList<>();
        String sql = "SELECT * FROM oppgaver WHERE CAST(oppgave_id as TEXT) ILIKE ?" +
                " OR oppgave_navn ILIKE ? OR CAST(ansatt_id AS TEXT) ILIKE ?" +
                " OR CAST(start_dato as TEXT) ILIKE ? OR CAST(slutt_dato as TEXT) ILIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String pattern = "%" + search + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            ps.setString(4, pattern);
            ps.setString(5, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Oppgave dataOppgave = new Oppgave(
                            rs.getInt("oppgave_id"),
                            rs.getString("oppgave_navn"),
                            rs.getInt("ansatt_id"),
                            rs.getDate("start_dato"),
                            rs.getDate("slutt_dato")
                    );
                    oppgave.add(dataOppgave.toDomain());
                }
            }
        }
        return oppgave;
    }

    @Override
    public List<OppgaveDom> getAll() throws SQLException {
        List<OppgaveDom> oppgaver = new ArrayList<>();
        String sql = "SELECT * FROM oppgaver";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Oppgave dataOppgave = new Oppgave(
                        rs.getInt("oppgave_id"),
                        rs.getString("oppgave_navn"),
                        rs.getInt("ansatt_id"),
                        rs.getDate("start_dato"),
                        rs.getDate("slutt_dato")
                );
                oppgaver.add(dataOppgave.toDomain());
            }
        }
        return oppgaver;
    }

    @Override
    public OppgaveDom getById(int id) throws SQLException {
        String sql = "SELECT * FROM oppgaver WHERE oppgave_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Oppgave dataOppgave = new Oppgave(
                            rs.getInt("oppgave_id"),
                            rs.getString("oppgave_navn"),
                            rs.getInt("ansatt_id"),
                            rs.getDate("start_dato"),
                            rs.getDate("slutt_dato")
                    );
                    return dataOppgave.toDomain();
                }
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM oppgaver WHERE oppgave_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
