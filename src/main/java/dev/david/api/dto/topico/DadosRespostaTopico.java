package dev.david.api.dto.topico;

import java.time.LocalDateTime;

public record DadosRespostaTopico(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, String status, String autor , String curso) {
}
