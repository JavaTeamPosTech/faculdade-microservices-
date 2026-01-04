package com.postechfiap.faculdade.autenticacao.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de autenticação bem-sucedida")
public record LoginResponse(
        @Schema(description = "Token JWT para acesso aos recursos protegidos", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndXN0YXZvQGZhY3VsZGFkZS5jb20iLCJyb2xlIjoiQUxVTk8iLCJpZCI6IjU1MGU4NDAwLWUyOWItNDFkNC1hNzE2LTQ0NjY1NTQ0MDAwMCIsImV4cCI6MTcxNDg1NzYwMH0.AssinaturaCriptografada...")
        String token,

        @Schema(description = "Dados do usuário autenticado")
        UsuarioResponse usuario
) {}