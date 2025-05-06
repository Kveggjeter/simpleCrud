package com.note.visma.domain.repository;

import com.note.visma.domain.model.StillingDom;
import java.sql.SQLException;
import java.util.List;

public interface
IStillingRepository {
    public void insert(StillingDom stilling) throws SQLException;
    public List<StillingDom> getAll() throws SQLException;
    public StillingDom getById(int id) throws SQLException;
    public void deleteById(int id) throws SQLException;
    public List<StillingDom> getSearch(String search) throws SQLException;


}
