package com.note.visma.domain.repository;

import com.note.visma.domain.model.OppgaveDom;
import java.sql.SQLException;
import java.util.List;

public interface IOppgaveRepository {

    public void insert(OppgaveDom oppgave) throws SQLException;
    public List<OppgaveDom> getAll() throws SQLException;
    public OppgaveDom getById(int id) throws SQLException;
    public void deleteById(int id) throws SQLException;
    public List<OppgaveDom> getSearch(String search) throws SQLException;
}
