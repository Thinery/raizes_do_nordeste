package raizesnordeste.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final Key chave;
    private final long expiracaoMs;

    public JwtUtil(@Value("${app.jwt.secret}") String segredo,
                    @Value("${app.jwt.expiration-ms}") long expiracaoMs) {
        // HMAC-SHA precisa de uma chave com pelo menos 256 bits (32+ caracteres).
        this.chave = Keys.hmacShaKeyFor(segredo.getBytes());
        this.expiracaoMs = expiracaoMs;
    }

    public String gerarToken(String email, String perfil) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expiracaoMs);
        return Jwts.builder()
                .setSubject(email)
                .claim("perfil", perfil)
                .setIssuedAt(agora)
                .setExpiration(expiracao)
                .signWith(chave, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validarEExtrairClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(chave)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public long getExpiracaoMs() {
        return expiracaoMs;
    }
}
