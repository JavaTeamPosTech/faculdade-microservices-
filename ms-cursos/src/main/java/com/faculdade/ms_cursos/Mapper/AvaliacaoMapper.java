package com.faculdade.ms_cursos.Mapper;

import com.faculdade.ms_cursos.dto.request.AvaliacaoRequestDTO;
import com.faculdade.ms_cursos.dto.response.AvaliacaoResponseDTO;
import com.faculdade.ms_cursos.entities.AvaliacaoEntity;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoMapper {

    public AvaliacaoEntity toEntity(AvaliacaoRequestDTO request) {
        return new AvaliacaoEntity(request.idCurso(), request.idUsuario(), request.descricao(), request.nota());
    }

    public AvaliacaoResponseDTO toDto(AvaliacaoEntity entity) {
        return new AvaliacaoResponseDTO(
                entity.getIdCurso(),
                entity.getDescricao(),
                entity.getNota()
        );
    }

}
