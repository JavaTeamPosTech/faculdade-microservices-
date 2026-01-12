package com.faculdade.ms_cursos.client.dto;

import jakarta.persistence.Column;

import java.util.UUID;

public record CursoDetails(
        UUID id,
        UUID idProfessor,
        String nome,
        String descricao
) {
}
