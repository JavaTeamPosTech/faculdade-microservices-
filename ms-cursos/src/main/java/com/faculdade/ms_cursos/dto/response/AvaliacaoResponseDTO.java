package com.faculdade.ms_cursos.dto.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AvaliacaoResponseDTO(
        Long id,
        Long idCurso,
        Long idUsuario,
        String descricao,
        Integer nota
) {
}
