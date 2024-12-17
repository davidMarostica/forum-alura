package dev.david.api.modelo;

import dev.david.api.dto.usuarios.DadosAtualizarUsuario;
import dev.david.api.dto.usuarios.DadosRegiostroUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;
    private String email;
    private String senha;
    private Boolean activo;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topico> topicos = new ArrayList<>();

    public Usuario(DadosRegiostroUsuario dadosRegiostroUsuario) {
        this.nome = dadosRegiostroUsuario.nome();
        this.email = dadosRegiostroUsuario.email();
        this.senha = dadosRegiostroUsuario.senha();
        this.activo = true;
    }

    public void atualizarDados(DadosAtualizarUsuario dadosAtualizarUsuario) {
        if (dadosAtualizarUsuario.nome() != null) {
            this.nome = dadosAtualizarUsuario.nome();
        }
        if (dadosAtualizarUsuario.email() != null) {
            this.email = dadosAtualizarUsuario.email();
        }
        if (dadosAtualizarUsuario.senha() != null) {
            this.senha = dadosAtualizarUsuario.senha();
        }
        if (dadosAtualizarUsuario.activo() != null) {
            this.activo = dadosAtualizarUsuario.activo();
        }
    }

    public void desativarUsuario() {
        this.activo = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nome;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public String getNome() {
        return nome;
    }
}
