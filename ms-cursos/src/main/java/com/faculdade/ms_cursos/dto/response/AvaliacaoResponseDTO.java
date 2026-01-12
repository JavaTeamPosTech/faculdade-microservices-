package com.faculdade.ms_cursos.dto.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record AvaliacaoResponseDTO(
        UUID id,
        UUID idCurso,
        UUID idUsuario,
        String descricao,
        Integer nota
) {
}
