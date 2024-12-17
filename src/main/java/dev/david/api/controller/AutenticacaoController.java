package dev.david.api.controller;

import dev.david.api.dto.usuarios.DadosAutenticacaoUsuario;
import dev.david.api.infra.security.DadosJWTToken;
import dev.david.api.infra.security.TokenService;
import dev.david.api.modelo.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DadosAutenticacaoUsuario DadosAutenticacaoUsuario){
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                DadosAutenticacaoUsuario.nome(), DadosAutenticacaoUsuario.senha());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DadosJWTToken(JWTtoken));
    }
}
