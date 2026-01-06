package com.postechfiap.faculdade.autenticacao.controller;

import com.postechfiap.faculdade.autenticacao.dto.UsuarioResponse;
import com.postechfiap.faculdade.autenticacao.service.UsuarioService;
import com.postechfiap.meuhospital.usuario.PacienteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller responsável pelos usuários (Consulta, etc.).
 */
@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para consulta e gestão de dados de usuários.")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint protegido: permite a busca de um usuário por ID.
     * Somente Professores e Coordenadores podem buscar qualquer usuário. Alunos só podem buscar a si mesmos.
     * * Regra de autorização:
     * - Permite se a Role for PROFESSOR ou COORDENADOR (busca qualquer um).
     * - OU Permite se o ID do caminho for IGUAL ao ID do usuário autenticado.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID (Acesso Controlado)",
            description = "Retorna detalhes de um usuário. Alunos só acessam seus próprios dados.")
    @ApiResponse(responseCode = "200", description = "Sucesso. Retorna dados do usuário.")
    @ApiResponse(responseCode = "403", description = "Proibido. Usuário não tem permissão para acessar este ID.")
    @ApiResponse(responseCode = "404", description = "Não encontrado.")
    @PreAuthorize("hasAnyAuthority('ALUNO','PROFESSOR', 'COORDENADOR', 'INTERNAL_SERVICE_ACCESS') or #id == authentication.principal.id")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UsuarioResponse> buscarPorId(
            @Parameter(description = "UUID do usuário a ser buscado.") @PathVariable UUID id) {

        log.info("Requisição GET /usuarios/{} recebida. Iniciando busca.", id);

        UsuarioResponse response = usuarioService.buscarUsuarioPorId(id);

        log.info("Busca por usuário ID {} concluída com sucesso.", id);
        return ResponseEntity.ok(response);
    }


}