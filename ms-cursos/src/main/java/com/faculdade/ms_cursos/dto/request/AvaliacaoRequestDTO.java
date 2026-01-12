package com.faculdade.ms_cursos.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;


public record AvaliacaoRequestDTO (

    @NotNull
    UUID idCurso,
    @NotNull
    UUID idUsuario,
    @NotBlank
    String descricao,
    @Min(1)
    @Max(10)
    Integer nota
){}
