package com.note.visma.domain.service.validator;

import com.note.visma.domain.model.OppgaveDom;
import com.note.visma.domain.model.StillingDom;

public class CheckForDate {

    public CheckForDate() {}

    public static boolean isDateValid(StillingDom stilling, OppgaveDom oppgave) {
        return !oppgave.startDato().isBefore(stilling.startDato()) &&
                !oppgave.sluttDato().isAfter(stilling.sluttDato());
    }

    public static boolean isDateValid(StillingDom eksisterende, StillingDom ny) {
        return ny.sluttDato().isBefore(eksisterende.startDato()) ||
                ny.startDato().isAfter(eksisterende.sluttDato());
    }
}
