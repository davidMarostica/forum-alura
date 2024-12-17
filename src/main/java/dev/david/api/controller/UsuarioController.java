package dev.david.api.controller;

import dev.david.api.dto.usuarios.*;
import dev.david.api.modelo.Usuario;
import dev.david.api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.transaction.annotation.Transactional;  // Corrigi a importação de Transactional
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final AuthenticationManager authenticationManager;

    //CON PAGINACION
    @GetMapping
    public Page<DadosListadoUsuario> listadoUsuarios(@PageableDefault(size = 2, sort = "nome") Pageable paginacion) {
        return usuarioRepository.findAll(paginacion).map(DadosListadoUsuario::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosCompletosUsuario> listarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        DadosCompletosUsuario dadosCompletosUsuario = new DadosCompletosUsuario(usuario);
        return ResponseEntity.ok(dadosCompletosUsuario);
    }

    @PostMapping
    public String agregar(@RequestBody @Valid DadosRegiostroUsuario dadosRegiostroUsuario) {
        Usuario usuario = new Usuario(dadosRegiostroUsuario);
        usuarioRepository.save(usuario);
        return "Usuário cadastrado com sucesso!";
    }

    @PutMapping
    @Transactional
    public void atualizarUsuario(@RequestBody @Valid DadosAtualizarUsuario dadosAtualizarUsuario) {
        Usuario usuario = usuarioRepository.getReferenceById(dadosAtualizarUsuario.id());
        System.out.println(usuario);
        usuario.atualizarDados(dadosAtualizarUsuario);
    }

    //DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.desativarUsuario();  // Corrigi a capitalização para desativarUsuario
    }

    // Método de exemplo do outro controlador
    @GetMapping("/lista")
    public String getUsuario() {
        return "Lista de usuários";
    }
}
