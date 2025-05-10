package com.note.visma.viewmodel;

import com.note.visma.domain.model.AnsattDom;
import com.note.visma.domain.model.OppgaveDom;
import com.note.visma.domain.model.StillingDom;
import com.note.visma.domain.service.validator.CheckForDate;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ViewControllerTest {

    static AnsattDom ansatt = new AnsattDom(1, "Ola", "Normann");

    static StillingDom stilling(LocalDate start, LocalDate slutt) {
        return new StillingDom(1, "Utvikler", 1, start, slutt);
    }

    static OppgaveDom oppgave(LocalDate start, LocalDate slutt) {
        return new OppgaveDom(1, "Lage app", 1, start, slutt);
    }

    @Test
    void testEnkeltTilfelleMatch() {
        StillingDom stilling = ViewControllerTest.stilling(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
        OppgaveDom oppgave = ViewControllerTest.oppgave(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 4, 1));

        boolean result = CheckForDate.isDateValid(stilling, oppgave);
        assertTrue(result, "Oppgaven faller innenfor stillingsperioden");
    }

    @Test
    void testOppgaveStarterSammeDagSomStilling() {
        StillingDom stilling = ViewControllerTest.stilling(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
        OppgaveDom oppgave = ViewControllerTest.oppgave(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 2, 1));

        assertTrue(CheckForDate.isDateValid(stilling, oppgave), "Oppgave starter nøyaktig ved stillingsstart");
    }

    @Test
    void testOppgaveSlutterEtterStilling() {
        StillingDom stilling = ViewControllerTest.stilling(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 30));
        OppgaveDom oppgave = ViewControllerTest.oppgave(LocalDate.of(2024, 5, 1), LocalDate.of(2024, 7, 1));

        assertFalse(CheckForDate.isDateValid(stilling, oppgave), "Oppgave slutter etter stillingsslutt");
    }

    @Test
    void testRiktigStillingVelges() {
        StillingDom stilling1 = ViewControllerTest.stilling(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 30));
        StillingDom stilling2 = ViewControllerTest.stilling(LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31));
        OppgaveDom oppgave = ViewControllerTest.oppgave(LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 15));

        boolean match1 = CheckForDate.isDateValid(stilling1, oppgave);
        boolean match2 = CheckForDate.isDateValid(stilling2, oppgave);

        assertFalse(match1);
        assertTrue(match2, "Oppgaven matcher kun stilling 2");
    }

    @Test
    void testOverlappendeStillingGirFeil() {
        StillingDom eksisterende = ViewControllerTest.stilling(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 1));
        StillingDom ny = ViewControllerTest.stilling(LocalDate.of(2024, 5, 15), LocalDate.of(2024, 10, 1));

        boolean valid = CheckForDate.isDateValid(eksisterende, ny);
        assertFalse(valid, "Ny stilling overlapper eksisterende");
    }


}