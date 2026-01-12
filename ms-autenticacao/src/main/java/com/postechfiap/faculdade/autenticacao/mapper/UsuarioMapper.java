package com.postechfiap.faculdade.autenticacao.mapper;

import com.postechfiap.faculdade.autenticacao.dto.UsuarioRegisterRequest;
import com.postechfiap.faculdade.autenticacao.dto.UsuarioResponse;
import com.postechfiap.faculdade.autenticacao.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRegisterRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setCpf(request.cpf());
        usuario.setTelefone(request.telefone());
        usuario.setRole(request.role());
        usuario.setMatricula(request.matricula());
        usuario.setDepartamento(request.departamento());
        usuario.setDataNascimento(request.dataNascimento());
        return usuario;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getRole(),
                usuario.getMatricula(),
                usuario.getDepartamento(),
                usuario.getDataNascimento(),
                usuario.isAtivo()
        );
    }
}