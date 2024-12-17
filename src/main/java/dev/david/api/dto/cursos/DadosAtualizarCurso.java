package dev.david.api.dto.cursos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarCurso(
        @NotNull Long id,
        @NotBlank String nome,
        @NotBlank String categoria) {
}
