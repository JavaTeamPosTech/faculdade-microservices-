package com.faculdade.ms_cursos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avaliacoes")
public class AvaliacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_curso")
    private Long idCurso;
    @Column(name = "id_usuario")
    private Long idUsuario;
    private String descricao;
    private Integer nota;

    public AvaliacaoEntity(Long idCurso, Long idUsuario, String descricao, Integer nota) {
        this.idCurso = idCurso;
        this.idUsuario = idUsuario;
        this.descricao = descricao;
        this.nota = nota;
    }
}
