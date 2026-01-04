package com.postechfiap.faculdade.autenticacao.service;

import com.postechfiap.faculdade.autenticacao.dto.UsuarioRegisterRequest;
import com.postechfiap.faculdade.autenticacao.dto.UsuarioResponse;
import com.postechfiap.faculdade.autenticacao.entity.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {
    UsuarioResponse criarUsuario(UsuarioRegisterRequest request);
    UsuarioResponse buscarUsuarioPorId(UUID id);
    Optional<Usuario> buscarUsuarioPorEmail(String email);
}