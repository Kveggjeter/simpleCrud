package com.note.visma.viewmodel;

import com.note.visma.domain.model.AnsattDom;
import com.note.visma.domain.model.OppgaveDom;
import com.note.visma.domain.model.StillingDom;
import com.note.visma.domain.usecase.ansatt.AnsattUseCase;
import com.note.visma.domain.usecase.oppgave.OppgaveUseCase;
import com.note.visma.domain.usecase.stilling.StillingUseCase;

import java.sql.SQLException;
import java.util.List;

public class ViewModel {

    private final AnsattUseCase ansattUseCase;
    private final StillingUseCase stillingUseCase;
    private final OppgaveUseCase oppgaveUseCase;

    public ViewModel(
            AnsattUseCase ansattUseCase,
            StillingUseCase stillingUseCase,
            OppgaveUseCase oppgaveUseCase
    ) {
        this.ansattUseCase = ansattUseCase;
        this.stillingUseCase = stillingUseCase;
        this.oppgaveUseCase = oppgaveUseCase;
    }


    public List<AnsattDom> getAnsatte() throws Exception {
        return ansattUseCase.execute();
    }

    public void addAnsatt(AnsattDom ansatt) throws Exception {
        ansattUseCase.execute(ansatt);
    }

    public void deleteAnsatt(int id) throws Exception {
        ansattUseCase.execute(id);
    }

    public List<AnsattDom> searchAnsatt(String search) throws Exception {
        return ansattUseCase.execute(search);
    }

    public List<AnsattDom> gettingAvailible() throws Exception {
        return ansattUseCase.gettingAvailible();
    }

    public List<StillingDom> getStillinger() throws SQLException {
        return stillingUseCase.execute();
    }

    public void addStilling(StillingDom stilling) throws SQLException {
        stillingUseCase.execute(stilling);
    }

    public void deleStilling(int id) throws SQLException {
        stillingUseCase.execute(id);
    }

    public List<StillingDom> searchStilling(String search) throws Exception {
        return stillingUseCase.execute(search);
    }

    public List<OppgaveDom> getOppgaver() throws SQLException {
        return oppgaveUseCase.execute();
    }

    public void addOppgave(OppgaveDom oppgave) throws SQLException {
        oppgaveUseCase.execute(oppgave);
    }

    public void deleteOppgave(int id) throws SQLException {
        oppgaveUseCase.execute(id);
    }
    public List<OppgaveDom> searchOppgave(String search) throws Exception {
        return oppgaveUseCase.execute(search);
    }
}
