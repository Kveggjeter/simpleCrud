package com.note.visma.domain.service;

import com.note.visma.domain.model.OppgaveDom;
import com.note.visma.domain.model.StillingDom;

public class CheckForDate {

    public CheckForDate() {}

    public static boolean isDateValid(StillingDom stilling, OppgaveDom oppgave) {
        return !oppgave.startDato().isBefore(stilling.startDato()) &&
                !oppgave.sluttDato().isAfter(stilling.sluttDato());
    }
}
