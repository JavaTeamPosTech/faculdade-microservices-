package com.faculdade.ms_cursos.services;

import com.faculdade.ms_cursos.client.dto.UsuarioDetails;
import com.faculdade.ms_cursos.dto.request.AvaliacaoRequestDTO;
import com.faculdade.ms_cursos.dto.response.AvaliacaoResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface IAvaliacaoService {

    AvaliacaoResponseDTO criarAvaliacao(AvaliacaoRequestDTO avaliacao, Authentication authentication);
    AvaliacaoResponseDTO buscarConsultaPorId(UUID id);
    Boolean isCursoExiste(AvaliacaoRequestDTO avaliacaoId);
    UsuarioDetails buscarUsuarioPorId(UUID idUsuario);
}
