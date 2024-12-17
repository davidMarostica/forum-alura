package dev.david.api.dto.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarTopico(
        @NotNull Long id,
        @NotBlank String titulo,
        @NotBlank String mensagem,
        @NotNull Long id_curso,
        @NotNull Long id_usuario
) {
}
