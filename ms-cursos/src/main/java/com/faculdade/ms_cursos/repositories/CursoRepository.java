package com.faculdade.ms_cursos.repositories;

import com.faculdade.ms_cursos.entities.AvaliacaoEntity;
import com.faculdade.ms_cursos.entities.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CursoRepository extends JpaRepository<CursoEntity, UUID> {
}
