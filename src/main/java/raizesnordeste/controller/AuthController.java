package raizesnordeste.controller;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import raizesnordeste.audit.AuditoriaService;
import raizesnordeste.dto.LoginRequest;
import raizesnordeste.dto.LoginResponse;
import raizesnordeste.model.Usuario;
import raizesnordeste.repository.UsuarioRepository;
import raizesnordeste.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuditoriaService auditoriaService;

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil, AuditoriaService auditoriaService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.auditoriaService = auditoriaService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("credenciais invalidas"));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new BadCredentialsException("credenciais invalidas");
        }

        String token = jwtUtil.gerarToken(usuario.getEmail(), usuario.getPerfil().name());
        auditoriaService.registrar(usuario.getEmail(), "LOGIN", "login realizado com sucesso");

        return new LoginResponse(
                token, "Bearer", jwtUtil.getExpiracaoMs() / 1000,
                new LoginResponse.UsuarioResumo(usuario.getId(), usuario.getNome(), usuario.getPerfil().name()));
    }
}
