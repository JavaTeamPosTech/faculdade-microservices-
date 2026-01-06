package com.postechfiap.faculdade.autenticacao.controller;

import com.postechfiap.faculdade.autenticacao.exception.RecursoNaoEncontradoException;
import com.postechfiap.faculdade.autenticacao.mapper.UsuarioMapper;
import com.postechfiap.faculdade.autenticacao.security.JwtService;
import com.postechfiap.faculdade.autenticacao.service.UsuarioService;
import com.postechfiap.meuhospital.contracts.core.LoginRequest;
import com.postechfiap.meuhospital.contracts.core.LoginResponse;
import com.postechfiap.meuhospital.contracts.core.UsuarioRegisterRequest;
import com.postechfiap.meuhospital.contracts.core.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller responsável pelos endpoints de autenticação e geração de tokens.
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação e Cadastro", description = "Endpoints de acesso público para login e criação de contas.")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint público para cadastro de novos usuários.
     */
    @PostMapping("/register")
    @Operation(summary = "Cadastro de Novo Usuário",
            description = "Cria uma nova conta (Professor, Aluno ou Coordenador), criptografando a senha.")
    @ApiResponse(responseCode = "201", description = "Usuário criado e persistido. Retorna dados do usuário.")
    @ApiResponse(responseCode = "400", description = "Falha na validação de campos ou regras de negócio (e.g., campo obrigatório ausente).")
    @ApiResponse(responseCode = "409", description = "Conflito. E-mail ou CPF já cadastrado.")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid UsuarioRegisterRequest request) {
        log.info("INICIANDO: POST /auth/register para E-mail: {} e Role: {}", request.email(), request.role());

        UsuarioResponse response = authService.registrar(request);

        log.info("SUCESSO: Usuário ID {} criado.", response.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint de login que gera um Token JWT válido.
     */
    @PostMapping("/login")
    @Operation(summary = "Login e Geração de JWT",
            description = "Autentica o usuário com e-mail e senha e retorna um Token JWT.")
    @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida. Retorna o token e os dados do usuário.")
    @ApiResponse(responseCode = "401", description = "Não Autorizado. Credenciais inválidas.")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        log.info("INICIANDO: POST /auth/login para E-mail: {}", request.email());

        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}