package com.note.visma.viewmodel;

import com.note.visma.domain.model.StillingDom;
import com.note.visma.domain.repository.IStillingRepository;
import java.sql.SQLException;
import java.util.List;

public class ViewModelStilling {

    private final IStillingRepository repository;

    public ViewModelStilling(IStillingRepository repository) {
        this.repository = repository;
    }

    public void execute(StillingDom stilling) throws SQLException {
        repository.insert(stilling);
    }

    public List<StillingDom> execute() throws SQLException {
        return repository.getAll();
    }

    public void execute(int id) throws SQLException {
        repository.deleteById(id);
    }

    public List<StillingDom> execute(String search) throws Exception {
        return repository.getSearch(search);
    }

    public StillingDom getByAnsattId(int id) throws Exception {
        return repository.getByAnsattId(id);
    }

    public List<StillingDom> getAllByAnsattID(int ID) throws SQLException {
        return repository.getAllByAnsattID(ID);
    }

    public void updateById(int ansattID, int stillingID) throws SQLException {
        repository.updateById(ansattID, stillingID);
    }

    public List<StillingDom> getAllStillingWithoutAnsatt() throws SQLException {
        return repository.getAllStillingWithoutAnsatt();
    }
}
