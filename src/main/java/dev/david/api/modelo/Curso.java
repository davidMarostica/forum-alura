package dev.david.api.modelo;

import dev.david.api.dto.cursos.DadosAtualizarCurso;
import dev.david.api.dto.cursos.DadosRegistroCurso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "cursos")
@Entity(name = "Curso")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nome;
    private String categoria;

    @OneToMany(mappedBy = "curso",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topico> topicos = new ArrayList<>();

    public Curso(DadosRegistroCurso DadosRegistroCurso) {
        this.nome = DadosRegistroCurso.nome();
        this.categoria = DadosRegistroCurso.categoria();
    }

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
        Curso other = (Curso) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getnome() {
        return nome;
    }

    public void setnome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public void AtualizarDados(DadosAtualizarCurso DadosAtualizarCurso) {
        if (DadosAtualizarCurso.nome() !=null) {
            this.nome = DadosAtualizarCurso.nome();
        }
        if (DadosAtualizarCurso.categoria() !=null) {
            this.categoria = DadosAtualizarCurso.categoria();
        }
    }

    public String getNome() {
        return null;
    }
}

