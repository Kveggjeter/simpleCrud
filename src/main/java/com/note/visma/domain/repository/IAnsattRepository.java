package com.note.visma.domain.repository;

import com.note.visma.domain.model.AnsattDom;

import java.sql.SQLException;
import java.util.List;

public interface IAnsattRepository {

    public void insert(AnsattDom ansatt) throws SQLException;
    public List<AnsattDom> getAll() throws SQLException;
    public AnsattDom getById(int id) throws SQLException;
    public void deleteById(int id) throws SQLException;
    public List<AnsattDom> getSearch(String search) throws SQLException;
    public List<AnsattDom> getAllAnsatt() throws SQLException;
}

