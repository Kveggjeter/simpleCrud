package com.note.visma.domain.model;

import java.time.LocalDate;

public record OppgaveDom(
        int oppgaveID,
        String oppgaveNavn,
        Integer ansattID,
        LocalDate startDato,
        LocalDate sluttDato
) {
}
