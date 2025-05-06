package com.note.visma.domain.model;

import java.time.LocalDate;

public record StillingDom(
        int stillingID,
        String stillingNavn,
        Integer ansattID,
        LocalDate startDato,
        LocalDate sluttDato
) {
}
