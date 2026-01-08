package com.postechfiap.meuhospital.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AvaliacaoCriadaEvent(
        UUID idAvaliacao,
        UUID idUsuario,
        UUID idCurso,
        String emailProfessor,
        String descricao,
        Integer nota,
        LocalDateTime dataHoraAvaliacao
) {
}
