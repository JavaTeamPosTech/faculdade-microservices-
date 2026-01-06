package com.faculdade.ms_cursos.controllers.avaliacao;

import com.faculdade.ms_cursos.dto.request.AvaliacaoRequestDTO;
import com.faculdade.ms_cursos.dto.response.AvaliacaoResponseDTO;
import com.faculdade.ms_cursos.services.IAvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/avaliacoes")
@RestController
@Tag(name = "Consultas", description = "Endpoints para agendamento e  gestão de consulta.")
@SecurityRequirement(name = "bearerAuth")
public class AvaliacaoController {

    private final IAvaliacaoService avaliacaoService;

    public AvaliacaoController(IAvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    /**
     * Endpoint para criação de uma nova avaliacao.
     */
    @PostMapping
    @Operation(summary = "Criar Nova Consulta",
            description = "Cria um novo agendamento, valida a disponibilidade do médico e publica um evento Kafka.")
    @ApiResponse(responseCode = "201", description = "Consulta criada com sucesso.")
    @ApiResponse(responseCode = "400", description = "Regra de Negócio violada (Ex: Conflito de horário, DTO inválido).")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ALUNO')")
    public ResponseEntity<AvaliacaoResponseDTO> criarAvaliacao(
            @RequestBody @Valid AvaliacaoRequestDTO request, Authentication authentication
    ) {
        AvaliacaoResponseDTO response = avaliacaoService.criarAvaliacao(request, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar Avaliacao por ID (Acesso Granular)",
            description = "Retorna uma avalição. Alunos só podem ver as suas.")
    @ApiResponse(responseCode = "200", description = "Avaliação encontrada.")
    @ApiResponse(responseCode = "403", description = "Proibido. Usuário tenta acessar avaliação de terceiros.")
    @PreAuthorize("hasAnyAuthority('PROFESSOR')")
    public ResponseEntity<AvaliacaoResponseDTO> buscarConsultaPorId(
            @Parameter(description = "ID da consulta.") @PathVariable UUID id) {

        //log.info("Requisição GET /consultas/{} recebida.", id);

        AvaliacaoResponseDTO response = avaliacaoService.buscarConsultaPorId(id);

        //log.info("Busca de consulta ID {} concluída.", id);
        return ResponseEntity.ok(response);
    }
}
