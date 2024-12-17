package dev.david.api.dto.cursos;

import jakarta.validation.constraints.NotBlank;


    public record DadosRegistroCurso(
            @NotBlank
            String nome,

            @NotBlank
            String categoria) {
    }
