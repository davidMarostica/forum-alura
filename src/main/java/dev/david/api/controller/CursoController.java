package dev.david.api.controller;

import dev.david.api.dto.cursos.DadosAtualizarCurso;
import dev.david.api.dto.cursos.DadosCompletosCurso;
import dev.david.api.dto.cursos.DadosListadoCurso;
import dev.david.api.dto.cursos.DadosRegistroCurso;
import dev.david.api.modelo.*;
import dev.david.api.repository.CursoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {

    @Autowired
    public CursoRepository cursoRepository;


    @GetMapping
    public Page<DadosListadoCurso> listadoCursos(@PageableDefault(size = 2, sort = "nome") Pageable paginacion) {
        return cursoRepository.findAll(paginacion).map(DadosListadoCurso::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosCompletosCurso> listarCursoPorId(@PathVariable Long id) {
        Curso curso = cursoRepository.getReferenceById(id);
        DadosCompletosCurso DadosCompletosCurso = new DadosCompletosCurso(curso);
        return ResponseEntity.ok(DadosCompletosCurso);
    }

    @PostMapping
    public void agregar(@RequestBody @Valid DadosRegistroCurso DadosRegistroCurso) {
        Curso curso = new Curso(DadosRegistroCurso);
        cursoRepository.save(curso);
    }

    @PutMapping
    @Transactional
    public void AtualizarCurso(@RequestBody @Valid DadosAtualizarCurso DadosAtualizarCurso) {
        Curso curso = cursoRepository.getReferenceById(DadosAtualizarCurso.id());
        curso.AtualizarDados(DadosAtualizarCurso);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id) {
        Curso curso = cursoRepository.getReferenceById(id);
        cursoRepository.delete(curso);
    }
}

