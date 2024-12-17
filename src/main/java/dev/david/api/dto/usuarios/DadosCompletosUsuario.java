package dev.david.api.dto.usuarios;

import dev.david.api.modelo.Usuario;

public record DadosCompletosUsuario(Long id, String nome, String email, String senha, Boolean ativo) {

    public DadosCompletosUsuario(Usuario usuario) {
        this(usuario.getId(),
                usuario.getNome(),  // Corrigido para getNome()
                usuario.getEmail(),
                usuario.getSenha(), // Corrigido para getSenha()
                usuario.getActivo()); // Corrigido para getAtivo()
    }
}
