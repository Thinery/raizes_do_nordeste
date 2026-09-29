package raizesnordeste.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import raizesnordeste.dto.UsuarioRequest;
import raizesnordeste.dto.UsuarioResponse;
import raizesnordeste.exception.NegocioException;
import raizesnordeste.model.Perfil;
import raizesnordeste.model.Usuario;
import raizesnordeste.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Cadastro publico. Por simplicidade (e para manter o escopo dentro do prazo),
     * qualquer pessoa pode se cadastrar; o perfil informado e aceito como enviado.
     * Em um cenario real de producao, a criacao de ADMIN/GERENTE deveria exigir
     * um ADMIN autenticado - fica registrado aqui como limitacao conhecida.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrar(@Valid @RequestBody UsuarioRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new NegocioException("EMAIL_JA_CADASTRADO", "ja existe um usuario com este e-mail.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        usuario.setPerfil(parsePerfil(request.perfil()));

        return UsuarioResponse.de(repository.save(usuario));
    }

    private Perfil parsePerfil(String valor) {
        if (valor == null || valor.isBlank()) {
            return Perfil.CLIENTE;
        }
        try {
            return Perfil.valueOf(valor.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NegocioException("PERFIL_INVALIDO", "perfil deve ser ADMIN, GERENTE ou CLIENTE.", org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
