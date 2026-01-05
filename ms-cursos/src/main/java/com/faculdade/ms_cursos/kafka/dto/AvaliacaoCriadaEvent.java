package com.faculdade.ms_cursos.kafka.dto;

import java.time.LocalDateTime;

public record AvaliacaoCriadaEvent (
    Long idAvaliacao,
    Long idUsuario,
    Long idCurso,
    String descricao,
    Integer nota,
    LocalDateTime dataHoraAvaliacao
    ) {
}
