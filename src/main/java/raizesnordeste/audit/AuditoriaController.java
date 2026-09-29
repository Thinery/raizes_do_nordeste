package raizesnordeste.audit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoint apenas para ADMIN (ver SecurityConfig) consultar os logs de
 * auditoria de acoes sensiveis (login, movimentacao de estoque, cadastro
 * de cliente com dados pessoais).
 */
@RestController
@RequestMapping("/auditoria")
public class AuditoriaController {

    private final LogAuditoriaRepository repository;

    public AuditoriaController(LogAuditoriaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Page<LogAuditoria> listar(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        int paginaZeroIndexed = Math.max(page - 1, 0);
        return repository.findAllByOrderByDataHoraDesc(PageRequest.of(paginaZeroIndexed, limit));
    }
}
