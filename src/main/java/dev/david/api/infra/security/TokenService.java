package dev.david.api.infra.security;

import dev.david.api.modelo.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        System.out.println(apiSecret);
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            System.out.println(usuario.getNome());
            return JWT.create()
                    .withIssuer("foro alura")
                    .withSubject(usuario.getNome())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new TokenCreationException("Erro ao criar token JWT", exception);
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new InvalidTokenException("Token não pode ser nulo");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("foro alura")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String subject = decodedJWT.getSubject();
            if (subject == null) {
                throw new InvalidTokenException("Token inválido: subject ausente");
            }
            return subject;
        } catch (JWTVerificationException exception) {
            throw new InvalidTokenException("Erro ao verificar token JWT", exception);
        }
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }

    // Exceções personalizadas
    public static class TokenCreationException extends RuntimeException {
        public TokenCreationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InvalidTokenException extends RuntimeException {
        public InvalidTokenException(String message) {
            super(message);
        }

        public InvalidTokenException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
