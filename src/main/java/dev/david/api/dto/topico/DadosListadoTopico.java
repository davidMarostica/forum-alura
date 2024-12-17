package dev.david.api.dto.topico;

import dev.david.api.modelo.Topico;
import java.time.LocalDateTime;

public record DadosListadoTopico(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, String status, String autor, String curso) {

    public DadosListadoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus().toString(),
                topico.getAutor().getNome(),  // Correção: deve chamar getNome()
                topico.getCurso().getNome()  // Correção: deve chamar getNome()
        );
    }
}
