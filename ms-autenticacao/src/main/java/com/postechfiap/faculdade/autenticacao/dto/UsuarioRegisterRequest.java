package com.postechfiap.faculdade.autenticacao.dto;

import com.postechfiap.faculdade.autenticacao.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Dados para cadastro de um novo usuário")
public record UsuarioRegisterRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Schema(description = "Nome completo do usuário", example = "Gustavo Bomfim")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        @Schema(description = "E-mail corporativo ou pessoal", example = "gustavo@faculdade.com")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        @Schema(description = "Senha de acesso (mínimo 6 caracteres)", example = "SenhaForte@123")
        String senha,

        @NotBlank(message = "O CPF é obrigatório")
        @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos")
        @Schema(description = "CPF (apenas números)", example = "12345678901")
        String cpf,

        @NotBlank(message = "O telefone é obrigatório")
        @Schema(description = "Telefone de contato", example = "11999998888")
        String telefone,

        @NotNull(message = "O perfil (Role) é obrigatório")
        @Schema(description = "Perfil de acesso do usuário", example = "ALUNO", allowableValues = {"ALUNO", "PROFESSOR", "COORDENADOR"})
        Role role,

        @Schema(description = "Matrícula (Obrigatório se Role = ALUNO)", example = "2024001")
        String matricula,

        @Schema(description = "Departamento (Obrigatório se Role = PROFESSOR ou COORDENADOR)", example = "CIENCIAS_DA_COMPUTACAO")
        String departamento,

        @Schema(description = "Data de Nascimento (Obrigatório se Role = ALUNO)", example = "2000-05-20")
        LocalDate dataNascimento
) {}