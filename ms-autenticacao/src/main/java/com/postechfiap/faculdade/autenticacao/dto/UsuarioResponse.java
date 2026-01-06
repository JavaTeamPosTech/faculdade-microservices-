package com.postechfiap.faculdade.autenticacao.dto;

import com.postechfiap.faculdade.autenticacao.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Dados públicos do usuário retornado")
public record UsuarioResponse(
        @Schema(description = "Identificador único do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @Schema(description = "Nome completo", example = "Gustavo Bomfim")
        String nome,

        @Schema(description = "E-mail", example = "gustavo@faculdade.com")
        String email,

        @Schema(description = "CPF", example = "12345678901")
        String cpf,

        @Schema(description = "Telefone", example = "11999998888")
        String telefone,

        @Schema(description = "Perfil de acesso", example = "ALUNO")
        Role role,

        @Schema(description = "Matrícula (se aplicável)", example = "2024001")
        String matricula,

        @Schema(description = "Departamento (se aplicável)", example = "CIENCIAS_DA_COMPUTACAO")
        String departamento,

        @Schema(description = "Data de Nascimento", example = "2000-05-20")
        LocalDate dataNascimento,

        @Schema(description = "Status da conta", example = "true")
        boolean ativo
) {}