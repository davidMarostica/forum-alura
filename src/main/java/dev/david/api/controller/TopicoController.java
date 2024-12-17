package dev.david.api.controller;

import dev.david.api.dto.topico.*;
import dev.david.api.modelo.Curso;
import dev.david.api.modelo.Topico;
import dev.david.api.modelo.Usuario;
import dev.david.api.repository.CursoRepository;
import dev.david.api.repository.TopicoRepository;
import dev.david.api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<Page<DadosListadoTopico>> listadoTopicos(@PageableDefault(size = 5, sort = "titulo") Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAllByAutorActivoTrue(paginacion).map(DadosListadoTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosCompletosTopico> listarTopicoPorId(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        DadosCompletosTopico dadosCompletosTopico = new DadosCompletosTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus().toString(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        );
        return ResponseEntity.ok(dadosCompletosTopico);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosRespostaTopico> agregar(@RequestBody @Valid DadosRegistroTopico dadosRegistroTopico,
                                                       UriComponentsBuilder uriComponentsBuilder) {
        Long idCurso = dadosRegistroTopico.id_curso();
        Long idUsuario = dadosRegistroTopico.id_usuario();

        Curso curso = cursoRepository.findById(idCurso).orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Topico topico = new Topico(dadosRegistroTopico, curso, usuario);
        topicoRepository.save(topico);
        DadosRespostaTopico dadosRespostaTopico = new DadosRespostaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus().toString(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        );

        URI url = uriComponentsBuilder.path("topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(dadosRespostaTopico);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosRespostaTopico> atualizarTopico(@RequestBody @Valid DadosAtualizarTopico dadosAtualizarTopico) {
        Topico topico = topicoRepository.getReferenceById(dadosAtualizarTopico.id());
        topico.atualizarDados(dadosAtualizarTopico);
        DadosRespostaTopico dadosRespostaTopico = new DadosRespostaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus().toString(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        );
        return ResponseEntity.ok(dadosRespostaTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarMedico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }
}
