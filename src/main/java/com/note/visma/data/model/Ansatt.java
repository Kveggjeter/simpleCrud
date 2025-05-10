package com.note.visma.data.model;

import com.note.visma.domain.model.AnsattDom;

public record Ansatt(
        int ansattID,
        String fornavn,
        String etternavn
) implements DomainConvertible<AnsattDom> {

    public AnsattDom toDomain() {
        return new AnsattDom(ansattID, fornavn, etternavn);
    }

    public static Ansatt fromDomain(AnsattDom domain) {
        return new Ansatt(domain.ansattID(), domain.fornavn(), domain.etternavn());
    }

}
