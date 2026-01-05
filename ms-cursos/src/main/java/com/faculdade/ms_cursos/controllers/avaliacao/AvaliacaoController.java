package com.faculdade.ms_cursos.controllers.avaliacao;

import com.faculdade.ms_cursos.dto.request.AvaliacaoRequestDTO;
import com.faculdade.ms_cursos.dto.response.AvaliacaoResponseDTO;
import com.faculdade.ms_cursos.services.IAvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/avaliacoes")
@RestController
//@Tag(name = "Consultas", description = "Endpoints para agendamento e  gestão de consulta.")
//@SecurityRequirement(name = "bearerAuth")
public class AvaliacaoController {

    private final IAvaliacaoService avaliacaoService;

    public AvaliacaoController(IAvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    /**
     * Endpoint para criação de uma nova avaliacao.
     */
    @PostMapping
//    @Operation(summary = "Criar Nova Consulta",
//            description = "Cria um novo agendamento, valida a disponibilidade do médico e publica um evento Kafka.")
//    @ApiResponse(responseCode = "201", description = "Consulta criada com sucesso.")
//    @ApiResponse(responseCode = "400", description = "Regra de Negócio violada (Ex: Conflito de horário, DTO inválido).")
//    @PreAuthorize("hasAnyAuthority('MEDICO', 'ENFERMEIRO')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AvaliacaoResponseDTO> criarConsulta(@RequestBody @Valid AvaliacaoRequestDTO request) {
//        log.info("INICIANDO: POST /avaliacoes. Paciente: {}, Médico: {}, Data: {}",
//                request.pacienteId(), request.medicoId(), request.dataConsulta());

        AvaliacaoResponseDTO response = avaliacaoService.criarAvaliacao(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
