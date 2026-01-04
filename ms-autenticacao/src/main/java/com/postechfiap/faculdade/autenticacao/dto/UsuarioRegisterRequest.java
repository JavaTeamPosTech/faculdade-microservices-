package com.postechfiap.faculdade.autenticacao.dto;

import com.postechfiap.faculdade.autenticacao.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UsuarioRegisterRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String senha,

        @NotBlank(message = "O CPF é obrigatório")
        @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos")
        String cpf,

        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        @NotNull(message = "O perfil (Role) é obrigatório")
        Role role,

        String matricula,

        String departamento,

        LocalDate dataNascimento
) {}