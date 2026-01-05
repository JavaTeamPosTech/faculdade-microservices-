package com.faculdade.ms_cursos.services;

import com.faculdade.ms_cursos.dto.request.AvaliacaoRequestDTO;
import com.faculdade.ms_cursos.dto.response.AvaliacaoResponseDTO;

public interface IAvaliacaoService {

    AvaliacaoResponseDTO criarAvaliacao(AvaliacaoRequestDTO avaliacao);
}
