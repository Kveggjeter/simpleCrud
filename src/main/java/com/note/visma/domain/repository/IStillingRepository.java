package com.note.visma.domain.repository;

import com.note.visma.domain.model.StillingDom;
import java.sql.SQLException;
import java.util.List;

public interface
IStillingRepository {
    public void insert(StillingDom stilling) throws SQLException;
    public List<StillingDom> getAll() throws SQLException;
    public void deleteById(int id) throws SQLException;
    public List<StillingDom> getSearch(String search) throws SQLException;
    public StillingDom getByAnsattId(int id) throws SQLException;
    public List<StillingDom> getAllByAnsattID(int ID) throws SQLException;
    public void updateById(int ansattID, int stillingID) throws SQLException;
    public List<StillingDom> getAllStillingWithoutAnsatt() throws SQLException;

}
