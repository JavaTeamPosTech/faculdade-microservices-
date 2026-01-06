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
    @PreAuthorize("hasAnyAuthority('ALUNO')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AvaliacaoResponseDTO> criarConsulta(@RequestBody @Valid AvaliacaoRequestDTO request) {
//        log.info("INICIANDO: POST /avaliacoes. Paciente: {}, Médico: {}, Data: {}",
//                request.pacienteId(), request.medicoId(), request.dataConsulta());

        AvaliacaoResponseDTO response = avaliacaoService.criarAvaliacao(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
//    @Operation(summary = "Buscar Consulta por ID (Acesso Granular)",
//            description = "Retorna uma consulta. Pacientes só podem ver as suas.")
//    @ApiResponse(responseCode = "200", description = "Consulta encontrada.")
//    @ApiResponse(responseCode = "403", description = "Proibido. Usuário tenta acessar consulta de terceiros.")
//    @PreAuthorize("hasAnyAuthority('MEDICO', 'ENFERMEIRO') or @consultaService.isPacienteDaConsulta(#id, authentication.principal.id.toString())")
    public ResponseEntity<AvaliacaoResponseDTO> buscarConsultaPorId(
            @Parameter(description = "ID da consulta.") @PathVariable Long id) {

        //log.info("Requisição GET /consultas/{} recebida.", id);

        AvaliacaoResponseDTO response = avaliacaoService.buscarConsultaPorId(id);

        //log.info("Busca de consulta ID {} concluída.", id);
        return ResponseEntity.ok(response);
    }
}
