package com.postechfiap.meuhospital.core;

/**
 * Record (DTO) para a resposta de login bem-sucedido.
 * Contém o Token JWT para uso nas requisições subsequentes.
 */
public record LoginResponse(
        String token,
        UsuarioResponse usuario
) {}
