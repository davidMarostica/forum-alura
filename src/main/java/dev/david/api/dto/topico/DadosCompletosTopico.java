package dev.david.api.dto.topico;

import dev.david.api.modelo.Topico;

import java.time.LocalDateTime;

public record DadosCompletosTopico(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, String status, String autor, String curso) {

    // Construtor principal
    public DadosCompletosTopico(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, String status, String autor, String curso) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.autor = autor;
        this.curso = curso;
    }

    // Construtor adicional delegando para o construtor principal
    public DadosCompletosTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                LocalDateTime.from(topico.getDataCriacao()),
                topico.getStatus().toString(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        );
    }
}
