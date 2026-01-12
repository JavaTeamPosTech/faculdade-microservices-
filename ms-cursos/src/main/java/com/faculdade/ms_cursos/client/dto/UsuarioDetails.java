package com.faculdade.ms_cursos.client.dto;

import com.postechfiap.meuhospital.core.Role;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioDetails(
        UUID id,
        String email,
        String senha,
        Role role,
        String nome,
        String cpf,
        String telefone,
        String matricula,
        String departamento,
        LocalDate dataNascimento,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean ativo
) {
}
