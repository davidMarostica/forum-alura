package dev.david.api.modelo;

import dev.david.api.dto.topico.DadosAtualizarTopico;
import dev.david.api.dto.topico.DadosRegistroTopico;
import dev.david.api.repository.CursoRepository;
import dev.david.api.repository.UsuarioRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String titulo;

    @Getter
    @NotNull
    private String mensagem;

    @Setter
    @Getter
    @NotNull
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatusTopico status = StatusTopico.NAO_RESPONDIDO;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario autor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resposta> respostas = new ArrayList<>();

    @Transient
    private CursoRepository cursoRepository;

    @Transient
    private UsuarioRepository usuarioRepository;

    // Construtores
    public Topico(String titulo, String mensagem, Curso curso) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.curso = curso;
    }

    public Topico() {
    }

    public Topico(DadosRegistroTopico dadosRegistroTopico, Curso curso, Usuario usuario) {
        this.titulo = dadosRegistroTopico.titulo();
        this.mensagem = dadosRegistroTopico.mensagem();
        this.curso = curso;
        this.autor = usuario;
    }

    // Métodos hashCode e equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Topico other = (Topico) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public StatusTopico getStatus() {
        return status;
    }

    public void setStatus(StatusTopico status) {
        this.status = status;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public void atualizarDados(@Valid DadosAtualizarTopico dadosAtualizarTopico) {
        if (dadosAtualizarTopico.titulo() != null) {
            this.titulo = dadosAtualizarTopico.titulo();
        }
        if (dadosAtualizarTopico.mensagem() != null) {
            this.mensagem = dadosAtualizarTopico.mensagem();
        }
        if (dadosAtualizarTopico.id_curso() != null) {
            this.curso.setId(dadosAtualizarTopico.id_curso());
        }
        if (dadosAtualizarTopico.id_usuario() != null) {
            this.autor.setId(dadosAtualizarTopico.id_usuario());
        }
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getdataCriacao() {
        return null;
    }

    public Object getNome() {
        return null;
    }
}
