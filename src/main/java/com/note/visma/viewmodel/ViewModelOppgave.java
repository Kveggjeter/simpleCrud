package com.note.visma.viewmodel;

import com.note.visma.domain.model.OppgaveDom;
import com.note.visma.domain.repository.IOppgaveRepository;
import java.sql.SQLException;
import java.util.List;

public class ViewModelOppgave {

    private final IOppgaveRepository repository;

    public ViewModelOppgave(IOppgaveRepository repository) {
        this.repository = repository;
    }

    public List<OppgaveDom> execute() throws SQLException {
        return repository.getAll();
    }

    public void execute(OppgaveDom oppgave) throws SQLException {
        repository.insert(oppgave);
    }

    public void execute(int id) throws SQLException {
        repository.deleteById(id);
    }

    public List<OppgaveDom> execute(String search) throws Exception {
        return repository.getSearch(search);
    }

    public void execute(int ansattID, int oppgaveID) throws SQLException {
        repository.updateById(ansattID, oppgaveID);
    }
}
