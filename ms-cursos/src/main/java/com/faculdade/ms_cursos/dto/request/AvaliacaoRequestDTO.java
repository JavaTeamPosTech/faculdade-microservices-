package com.faculdade.ms_cursos.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record AvaliacaoRequestDTO (

    @NotNull
    Long idCurso,
    @NotNull
    Long idUsuario,
    @NotBlank
    String descricao,
    @Min(1)
    @Max(10)
    Integer nota
){}
