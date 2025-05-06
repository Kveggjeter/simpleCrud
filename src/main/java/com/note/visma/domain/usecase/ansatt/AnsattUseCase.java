package com.note.visma.domain.usecase.ansatt;

import com.note.visma.domain.model.AnsattDom;
import com.note.visma.domain.repository.IAnsattRepository;
import java.util.List;

public class AnsattUseCase {

    private final IAnsattRepository repository;

    public AnsattUseCase(IAnsattRepository repository) {
        this.repository = repository;
    }

    public List<AnsattDom> execute() throws Exception {
        return repository.getAll();
    }

    public void execute(AnsattDom ansatt) throws Exception {
        repository.insert(ansatt);
    }

    public void execute(int id) throws Exception {
        repository.deleteById(id);
    }

    public List<AnsattDom> execute(String search) throws Exception {
        return repository.getSearch(search);
    }

    public List<AnsattDom> gettingAvailible() throws Exception {
        return repository.getAllAnsatt();
    }
}
