package raizesnordeste.audit;

import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {

    private final LogAuditoriaRepository repository;

    public AuditoriaService(LogAuditoriaRepository repository) {
        this.repository = repository;
    }

    public void registrar(String usuario, String acao, String detalhes) {
        String usuarioFinal = (usuario == null || usuario.isBlank()) ? "anonimo" : usuario;
        repository.save(new LogAuditoria(usuarioFinal, acao, detalhes));
    }
}
