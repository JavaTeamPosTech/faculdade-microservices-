package com.faculdade.ms_cursos.repositories;

import com.faculdade.ms_cursos.entities.AvaliacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, UUID> {
}
