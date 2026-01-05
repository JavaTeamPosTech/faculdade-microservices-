package com.faculdade.ms_cursos.dto.response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AvaliacaoResponseDTO(
    Long idCurso,
    String descricao,
    Integer nota) {
}
