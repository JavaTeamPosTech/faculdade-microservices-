package com.postechfiap.faculdade.autenticacao.dto;

public record LoginResponse(
        String token,
        UsuarioResponse usuario
) {}