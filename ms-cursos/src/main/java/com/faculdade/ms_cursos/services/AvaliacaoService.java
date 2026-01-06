package com.faculdade.ms_cursos.services;

import com.faculdade.ms_cursos.client.AuthClientService;
import com.faculdade.ms_cursos.client.dto.UsuarioDetails;
import com.faculdade.ms_cursos.entities.CursoEntity;
import com.faculdade.ms_cursos.exception.RegraDeNegocioException;
import com.faculdade.ms_cursos.mapper.AvaliacaoMapper;
import com.faculdade.ms_cursos.dto.request.AvaliacaoRequestDTO;
import com.faculdade.ms_cursos.dto.response.AvaliacaoResponseDTO;
import com.faculdade.ms_cursos.entities.AvaliacaoEntity;
import com.faculdade.ms_cursos.kafka.AvaliacaoProducer;
import com.faculdade.ms_cursos.repositories.AvaliacaoRepository;
import com.faculdade.ms_cursos.repositories.CursoRepository;
import com.postechfiap.meuhospital.dto.AvaliacaoCriadaEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AvaliacaoService implements IAvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AuthClientService authClientService;
    private final CursoRepository cursoRepository;
    private final AvaliacaoMapper avaliacaoMapper;
    private final AvaliacaoProducer avaliacaoProducer;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, CursoRepository cursoRepository, AvaliacaoMapper avaliacaoMapper, AvaliacaoProducer avaliacaoProducer, AuthClientService authClientService) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoMapper = avaliacaoMapper;
        this.cursoRepository = cursoRepository;
        this.avaliacaoProducer = avaliacaoProducer;
        this.authClientService = authClientService;
    }

    public AvaliacaoResponseDTO criarAvaliacao(AvaliacaoRequestDTO request, Authentication authentication) {

        AvaliacaoEntity avaliacaoEntity = new AvaliacaoEntity();
        if (isCursoExiste(request)) {

            UsuarioDetails usuarioDetails = buscarUsuarioPorId(request.idUsuario());
            String alunoId = usuarioDetails.id().toString();
            if(!request.idUsuario().toString().equals(alunoId)){
                throw new RegraDeNegocioException("Usuário logado não corresponde ao usuário da avaliação.");
            }
            //faz a conversão e retorna o dto salvo

            avaliacaoEntity = avaliacaoRepository.save(avaliacaoMapper.toEntity(request));
            AvaliacaoCriadaEvent event = new AvaliacaoCriadaEvent(
                    avaliacaoEntity.getId(),
                    avaliacaoEntity.getIdUsuario(),
                    avaliacaoEntity.getIdCurso(),
                    avaliacaoEntity.getDescricao(),
                    avaliacaoEntity.getNota(),
                    LocalDateTime.now()
            );

            //Envio da avaliacao via kafka para geração do email
            avaliacaoProducer.sendAvaliacaoEvent(event);
        }

        return avaliacaoMapper.toDto(avaliacaoEntity);
    }

    @Override
    public AvaliacaoResponseDTO buscarConsultaPorId(UUID id) {
        AvaliacaoEntity avaliacaoEntity = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada com o ID: " + id));
        return avaliacaoMapper.toDto(avaliacaoEntity);
    }

    @Override
    public Boolean isCursoExiste(AvaliacaoRequestDTO request) {
        CursoEntity cursoEntity = cursoRepository.findById(request.idCurso())
                .orElseThrow(() -> new RegraDeNegocioException("Curso não encontrada com o ID: " + request.idCurso()));

        return true;
    }

    @Override
    public UsuarioDetails buscarUsuarioPorId(UUID idUsuario) {
        return authClientService.buscarUsuarioPorId(idUsuario);
    }
}
