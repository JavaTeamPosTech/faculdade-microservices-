package com.postechfiap.faculdade.autenticacao.service;

import com.postechfiap.faculdade.autenticacao.dto.LoginRequest;
import com.postechfiap.faculdade.autenticacao.dto.LoginResponse;
import com.postechfiap.faculdade.autenticacao.dto.UsuarioRegisterRequest;
import com.postechfiap.faculdade.autenticacao.dto.UsuarioResponse;

public interface AuthService {
    UsuarioResponse registrar(UsuarioRegisterRequest request);
    LoginResponse login(LoginRequest request);
}