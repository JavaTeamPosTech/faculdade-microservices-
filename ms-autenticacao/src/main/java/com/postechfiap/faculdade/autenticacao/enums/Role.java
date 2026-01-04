package com.postechfiap.faculdade.autenticacao.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    PROFESSOR,
    ALUNO,
    COORDENADOR;

    @Override
    public String getAuthority() {
        return name();
    }
}