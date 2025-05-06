package com.note.visma.data.repository;

import com.note.visma.data.datasource.DbConnect;
import com.note.visma.data.model.Ansatt;
import com.note.visma.domain.model.AnsattDom;
import com.note.visma.domain.repository.IAnsattRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnsattRepository implements IAnsattRepository {

    private final Connection connection;

    public AnsattRepository() {
        this.connection = DbConnect.getConnection();
    }

    @Override
    public void insert(AnsattDom ansatt) throws SQLException {
        String sql = "INSERT INTO ansatte (fornavn, etternavn) VALUES (?, ?)";
        Ansatt dataAnsatt = Ansatt.fromDomain(ansatt);

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, dataAnsatt.fornavn());
            stmt.setString(2, dataAnsatt.etternavn());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<AnsattDom> getAll() throws SQLException {
        List<AnsattDom> ansatte = new ArrayList<>();
        String sql = "SELECT * FROM ansatte";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Ansatt dataAnsatt = new Ansatt(
                        rs.getInt("ansatt_id"),
                        rs.getString("fornavn"),
                        rs.getString("etternavn")
                );

                ansatte.add(dataAnsatt.toDomain());
            }
        }
        return ansatte;
    }

    @Override
    public List<AnsattDom> getSearch(String search) throws SQLException {
        List<AnsattDom> ansatte = new ArrayList<>();
        String sql = "SELECT * FROM ansatte WHERE fornavn ILIKE ?" +
                " OR etternavn ILIKE ? OR CAST(ansatt_id AS TEXT) ILIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String pattern = "%" + search + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ansatt dataAnsatt = new Ansatt(
                            rs.getInt("ansatt_id"),
                            rs.getString("fornavn"),
                            rs.getString("etternavn")
                    );
                    ansatte.add(dataAnsatt.toDomain());
                }
            }
        }
        return ansatte;
    }

    @Override
    public List<AnsattDom> getAllAnsatt() throws SQLException {
        List<AnsattDom> ansatte = new ArrayList<>();
        String sql = "SELECT a.ansatt_id, a.fornavn, a.etternavn " +
                "FROM ansatte a " +
                "WHERE a.ansatt_id IN (SELECT ansatt_id FROM stillinger)";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ansatt dataAnsatt = new Ansatt(
                        rs.getInt("ansatt_id"),
                        rs.getString("fornavn"),
                        rs.getString("etternavn")
                );
                ansatte.add(dataAnsatt.toDomain());
            }
        }
        return ansatte;
    }

    @Override
    public AnsattDom getById(int id) throws SQLException {
        String sql = "SELECT * FROM ansatte WHERE ansatt_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Ansatt dataAnsatt = new Ansatt(
                            rs.getInt("ansatt_id"),
                            rs.getString("fornavn"),
                            rs.getString("etternavn")
                    );
                    return dataAnsatt.toDomain();
                }
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM ansatte WHERE ansatt_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

}
