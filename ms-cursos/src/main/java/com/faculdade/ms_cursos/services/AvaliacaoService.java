package com.faculdade.ms_cursos.services;

import com.faculdade.ms_cursos.Mapper.AvaliacaoMapper;
import com.faculdade.ms_cursos.dto.request.AvaliacaoRequestDTO;
import com.faculdade.ms_cursos.dto.response.AvaliacaoResponseDTO;
import com.faculdade.ms_cursos.entities.AvaliacaoEntity;
import com.faculdade.ms_cursos.kafka.AvaliacaoProducer;
import com.faculdade.ms_cursos.repositories.AvaliacaoRepository;
import com.postechfiap.meuhospital.dto.AvaliacaoCriadaEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AvaliacaoService implements IAvaliacaoService{

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoMapper avaliacaoMapper;
    private final AvaliacaoProducer avaliacaoProducer;

    public  AvaliacaoService(AvaliacaoRepository avaliacaoRepository, AvaliacaoMapper avaliacaoMapper, AvaliacaoProducer avaliacaoProducer) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoMapper = avaliacaoMapper;
        this.avaliacaoProducer = avaliacaoProducer;
    }

    public AvaliacaoResponseDTO criarAvaliacao(AvaliacaoRequestDTO request) {
        //faz a conversão e retorna o dto salvo

        AvaliacaoEntity avaliacaoEntity = avaliacaoRepository.save(avaliacaoMapper.toEntity(request));
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

        return  avaliacaoMapper.toDto(avaliacaoEntity);
    }

    @Override
    public AvaliacaoResponseDTO buscarConsultaPorId(Long id) {
        AvaliacaoEntity avaliacaoEntity = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada com o ID: " + id));
        return avaliacaoMapper.toDto(avaliacaoEntity);
    }
}
