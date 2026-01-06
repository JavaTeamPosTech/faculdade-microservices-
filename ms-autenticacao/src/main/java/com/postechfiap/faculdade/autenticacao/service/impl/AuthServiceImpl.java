package com.postechfiap.faculdade.autenticacao.service.impl;

import com.postechfiap.faculdade.autenticacao.dto.LoginRequest;
import com.postechfiap.faculdade.autenticacao.dto.LoginResponse;
import com.postechfiap.faculdade.autenticacao.dto.UsuarioRegisterRequest;
import com.postechfiap.faculdade.autenticacao.dto.UsuarioResponse;
import com.postechfiap.faculdade.autenticacao.service.AuthService;
import com.postechfiap.faculdade.autenticacao.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioService usuarioService;

    public AuthServiceImpl(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    @Transactional
    public UsuarioResponse registrar(UsuarioRegisterRequest request) {
        return usuarioService.criarUsuario(request);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        return usuarioService.autenticarUsuario(request);
    }
}