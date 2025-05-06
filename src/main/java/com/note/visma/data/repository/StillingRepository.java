package com.note.visma.data.repository;

import com.note.visma.data.datasource.DbConnect;
import com.note.visma.data.model.Oppgave;
import com.note.visma.data.model.Stilling;
import com.note.visma.domain.model.OppgaveDom;
import com.note.visma.domain.model.StillingDom;
import com.note.visma.domain.repository.IStillingRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StillingRepository implements IStillingRepository {

    private final Connection connection;

    public StillingRepository() {
        this.connection = DbConnect.getConnection();
    }

    @Override
    public void insert(StillingDom stilling) throws SQLException {
        String sql = "INSERT INTO stillinger (stilling_navn, ansatt_id, start_dato, slutt_dato) VALUES (?, ?, ?, ?)";
        Stilling dataStilling = Stilling.fromDomain(stilling);

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, dataStilling.stillingNavn());
            if(dataStilling.ansattID() != null)
                stmt.setInt(2, dataStilling.ansattID());
            else stmt.setNull(2, java.sql.Types.INTEGER);
            stmt.setDate(3, dataStilling.startDato());
            stmt.setDate(4, dataStilling.sluttDato());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<StillingDom> getSearch(String search) throws SQLException {
        List<StillingDom> stillinger = new ArrayList<>();
        String sql = "SELECT * FROM stillinger WHERE CAST(stilling_id as TEXT) ILIKE ?" +
                " OR stilling_navn ILIKE ? OR CAST(ansatt_id AS TEXT) ILIKE ?" +
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
                    Stilling dataStilling = new Stilling(
                            rs.getInt("stilling_id"),
                            rs.getString("stilling_navn"),
                            rs.getInt("ansatt_id"),
                            rs.getDate("start_dato"),
                            rs.getDate("slutt_dato")
                    );
                    stillinger.add(dataStilling.toDomain());
                }
            }
        }
        return stillinger;
    }

    @Override
    public List<StillingDom> getAll() throws SQLException {
        List<StillingDom> stillinger = new ArrayList<>();
        String sql = "SELECT * FROM stillinger";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Stilling dataStilling = new Stilling(
                        rs.getInt("stilling_id"),
                        rs.getString("stilling_navn"),
                        rs.getInt("ansatt_id"),
                        rs.getDate("start_dato"),
                        rs.getDate("slutt_dato")
                );
                stillinger.add(dataStilling.toDomain());
            }
        }
        return stillinger;
    }

    @Override
    public StillingDom getById(int id) throws SQLException {
        String sql = "SELECT * FROM stillinger WHERE stilling_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Stilling dataStilling = new Stilling(
                            rs.getInt("stilling_id"),
                            rs.getString("stilling_navn"),
                            rs.getInt("ansatt_id"),
                            rs.getDate("start_dato"),
                            rs.getDate("slutt_dato")
                    );
                    return dataStilling.toDomain();
                }
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM stillinger WHERE stilling_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
