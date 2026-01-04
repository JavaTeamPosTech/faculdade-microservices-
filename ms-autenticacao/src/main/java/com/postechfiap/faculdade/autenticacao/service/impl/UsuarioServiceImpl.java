package com.postechfiap.faculdade.autenticacao.service.impl;

import com.postechfiap.faculdade.autenticacao.dto.LoginRequest;
import com.postechfiap.faculdade.autenticacao.dto.LoginResponse;
import com.postechfiap.faculdade.autenticacao.dto.UsuarioRegisterRequest;
import com.postechfiap.faculdade.autenticacao.dto.UsuarioResponse;
import com.postechfiap.faculdade.autenticacao.entity.Usuario;
import com.postechfiap.faculdade.autenticacao.enums.Role;
import com.postechfiap.faculdade.autenticacao.exception.RecursoNaoEncontradoException;
import com.postechfiap.faculdade.autenticacao.exception.UsuarioExistenteException;
import com.postechfiap.faculdade.autenticacao.mapper.UsuarioMapper;
import com.postechfiap.faculdade.autenticacao.repository.UsuarioRepository;
import com.postechfiap.faculdade.autenticacao.security.JwtService;
import com.postechfiap.faculdade.autenticacao.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementação do Serviço de gestão de usuários (CRUD e validação de domínio).
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              UsuarioMapper usuarioMapper,
                              PasswordEncoder passwordEncoder,
                              @Lazy AuthenticationManager authenticationManager,
                              JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Cria um novo usuário, aplicando criptografia de senha e validações de domínio.
     */
    @Override
    @Transactional
    public UsuarioResponse criarUsuario(UsuarioRegisterRequest request) {
        log.info("Processando criação de novo usuário. E-mail: {}, Role: {}", request.email(), request.role());

        validarUnicidade(request.email(), request.cpf());
        validarCamposCondicionais(request);

        Usuario novoUsuario = usuarioMapper.toEntity(request);
        novoUsuario.setSenha(passwordEncoder.encode(request.senha()));

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        log.info("Usuário ID {} salvo no banco de dados.", usuarioSalvo.getId());

        return usuarioMapper.toResponse(usuarioSalvo);
    }

    /**
     * Busca um usuário pelo ID.
     */
    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse buscarUsuarioPorId(UUID id) {
        log.debug("Buscando usuário por ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + id + " não encontrado."));
        return usuarioMapper.toResponse(usuario);
    }


    /**
     * Busca um usuário pelo e-mail (usado principalmente pelo Spring Security e AuthController).
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        log.debug("Tentativa de buscar usuário para autenticação por e-mail: {}", email);
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public LoginResponse autenticarUsuario(LoginRequest request) {
        log.info("Iniciando processo de autenticação para o e-mail: {}", request.email());

        try {
            // 1. Autenticação (Verificação de senha)
            var authToken = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
            Authentication authentication = authenticationManager.authenticate(authToken);

            // 2. Geração do Token
            String token = jwtService.generateToken(authentication);
            log.debug("JWT gerado para o usuário: {}", request.email());

            // 3. Busca de Detalhes para Resposta
            UsuarioResponse usuarioResponse = usuarioRepository.findByEmail(request.email())
                    .map(usuarioMapper::toResponse)
                    .orElseThrow(() -> {
                        log.error("ERRO GRAVE: Usuário autenticado ({}) não encontrado no banco.", request.email());
                        return new RecursoNaoEncontradoException("Usuário não encontrado após autenticação.");
                    });

            log.info("SUCESSO: Login concluído. JWT e dados do usuário retornados.");
            return new LoginResponse(token, usuarioResponse);

        } catch (BadCredentialsException e) {
            log.warn("FALHA LOGIN: Credenciais inválidas para o e-mail: {}", request.email());
            throw e;
        }
    }

    /**
     * Garante que o e-mail e o CPF são únicos antes de salvar.
     */
    private void validarUnicidade(String email, String cpf) {
        log.debug("Validando unicidade para E-mail: {} e CPF: {}", email, cpf);
        if (usuarioRepository.existsByEmail(email)) {
            log.warn("Falha de unicidade: E-mail já existe.");
            throw new UsuarioExistenteException("Email já cadastrado no sistema.");
        }
        if (usuarioRepository.existsByCpf(cpf)) {
            log.warn("Falha de unicidade: CPF já existe.");
            throw new UsuarioExistenteException("CPF já cadastrado no sistema.");
        }
    }

    /**
     * Implementa as regras de validação de domínio baseadas na Role.
     */
    private void validarCamposCondicionais(UsuarioRegisterRequest request) {
        Role role = request.role();
        log.debug("Validando campos condicionais para a Role: {}", role);

        if (role == null) {
            throw new IllegalArgumentException("A Role (perfil) é obrigatória.");
        }

        // Validação de Matrícula (Aluno)
        if (role == Role.ALUNO) {
            if (request.matricula() == null || request.matricula().isBlank()) {
                log.warn("Validação falhou: Aluno sem matrícula.");
                throw new IllegalArgumentException("Aluno deve fornecer a matrícula.");
            }
            if (request.dataNascimento() == null) {
                log.warn("Validação falhou: Aluno sem data de nascimento.");
                throw new IllegalArgumentException("Aluno deve fornecer a data de nascimento.");
            }
            if (request.dataNascimento().isAfter(LocalDate.now())) {
                log.warn("Validação falhou: Data de nascimento futura.");
                throw new IllegalArgumentException("A data de nascimento não pode ser no futuro.");
            }
        }

        // Validação de Departamento (Professor e Coordenador)
        if (role == Role.PROFESSOR || role == Role.COORDENADOR) {
            if (request.departamento() == null || request.departamento().isBlank()) {
                log.warn("Validação falhou: {} sem departamento.", role);
                throw new IllegalArgumentException(role.name() + " deve fornecer o departamento.");
            }
        }
    }

}