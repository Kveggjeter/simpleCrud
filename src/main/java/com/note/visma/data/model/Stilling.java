package com.note.visma.data.model;

import com.note.visma.domain.model.StillingDom;

import java.sql.Date;

public record Stilling(
        int stillingID,
        String stillingNavn,
        Integer ansattID,
        Date startDato,
        Date sluttDato
) {
    public StillingDom toDomain() {
        return new StillingDom(stillingID, stillingNavn, ansattID, startDato.toLocalDate(), sluttDato.toLocalDate());
    }

    public static Stilling fromDomain(StillingDom domain) {
        return new Stilling(
                domain.stillingID(),
                domain.stillingNavn(),
                domain.ansattID(),
                Date.valueOf(domain.startDato()),
                Date.valueOf(domain.sluttDato())
        );
    }
}
