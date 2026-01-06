package com.faculdade.ms_cursos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avaliacoes")
public class AvaliacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "id_curso")
    private UUID idCurso;
    @Column(name = "id_usuario")
    private UUID idUsuario;
    private String descricao;
    private Integer nota;

    public AvaliacaoEntity(UUID idCurso, UUID idUsuario, String descricao, Integer nota) {
        this.idCurso = idCurso;
        this.idUsuario = idUsuario;
        this.descricao = descricao;
        this.nota = nota;
    }
}
