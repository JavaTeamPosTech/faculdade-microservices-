package com.postechfiap.faculdade.autenticacao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credenciais para login")
public record LoginRequest(
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        @Schema(description = "E-mail cadastrado", example = "gustavo@faculdade.com")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Schema(description = "Senha do usuário", example = "SenhaForte@123")
        String senha
) {}