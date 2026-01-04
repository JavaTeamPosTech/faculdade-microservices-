package com.postechfiap.faculdade.autenticacao.dto;

import com.postechfiap.faculdade.autenticacao.enums.Role;

import java.time.LocalDate;
import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nome,
        String email,
        String cpf,
        String telefone,
        Role role,
        String matricula,
        String departamento,
        LocalDate dataNascimento,
        boolean ativo
) {}