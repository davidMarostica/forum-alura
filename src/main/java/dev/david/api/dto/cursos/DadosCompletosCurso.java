package dev.david.api.dto.cursos;
import dev.david.api.modelo.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class DadosCompletosCurso {

    public DadosCompletosCurso(Curso curso) {
    }

    public record DadosAtualizarCurso(
            @NotNull
            Long id,

            @NotBlank
            String nome,

            @NotBlank
            String categoria) {
    }


}
