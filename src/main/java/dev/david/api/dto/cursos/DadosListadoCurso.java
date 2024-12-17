package dev.david.api.dto.cursos;

import dev.david.api.modelo.Curso;

public record DadosListadoCurso(Long id, String nome, String categoria) {

    public DadosListadoCurso(Curso curso) {
        this(curso.getId(), curso.getnome(), curso.getCategoria());
    }
}