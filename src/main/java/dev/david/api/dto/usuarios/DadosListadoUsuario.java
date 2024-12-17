package dev.david.api.dto.usuarios;

import dev.david.api.modelo.Usuario;

public record DadosListadoUsuario(Long id, String nome, String email, String senha, Boolean ativo) {

    public DadosListadoUsuario(Usuario usuario) {
        this(usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getActivo());
    }
}
