package com.note.visma.data.model;

import com.note.visma.domain.model.OppgaveDom;
import java.sql.Date;

public record Oppgave(
        int oppgaveID,
        String oppgaveNavn,
        Integer ansattID,
        Date startDato,
        Date sluttDato
) implements DomainConvertible<OppgaveDom> {
    public OppgaveDom toDomain() {
        return new OppgaveDom(oppgaveID, oppgaveNavn, ansattID, startDato.toLocalDate(), sluttDato.toLocalDate());
    }

    public static Oppgave fromDomain(OppgaveDom domain) {
        return new Oppgave(
                domain.oppgaveID(),
                domain.oppgaveNavn(),
                domain.ansattID(),
                Date.valueOf(domain.startDato()),
                Date.valueOf(domain.sluttDato())
        );
    }
}
