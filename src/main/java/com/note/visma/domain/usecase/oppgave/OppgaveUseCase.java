package com.note.visma.domain.usecase.oppgave;

import com.note.visma.domain.model.AnsattDom;
import com.note.visma.domain.model.OppgaveDom;
import com.note.visma.domain.repository.IOppgaveRepository;
import java.sql.SQLException;
import java.util.List;

public class OppgaveUseCase {

    private final IOppgaveRepository repository;

    public OppgaveUseCase(IOppgaveRepository repository) {
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
