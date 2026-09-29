package raizesnordeste.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import raizesnordeste.dto.MovimentacaoRequest;
import raizesnordeste.dto.MovimentacaoResponse;
import raizesnordeste.dto.SaldoEstoqueResponse;
import raizesnordeste.exception.RecursoNaoEncontradoException;
import raizesnordeste.repository.EstoqueRepository;
import raizesnordeste.service.EstoqueService;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    private final EstoqueRepository repository;
    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueRepository repository, EstoqueService estoqueService) {
        this.repository = repository;
        this.estoqueService = estoqueService;
    }

    /** Consulta publica do saldo (todas as unidades/produtos). */
    @GetMapping
    public List<SaldoEstoqueResponse> listar() {
        return repository.findAll().stream().map(SaldoEstoqueResponse::de).toList();
    }

    @GetMapping("/{id}")
    public SaldoEstoqueResponse buscarPorId(@PathVariable Long id) {
        return SaldoEstoqueResponse.de(repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("ESTOQUE_NAO_ENCONTRADO", "registro de estoque nao encontrado.")));
    }

    /**
     * Fluxo critico (Fluxo B): registra entrada ou saida de estoque de um
     * produto em uma unidade e atualiza o saldo. Exige token JWT com perfil
     * ADMIN ou GERENTE (ver SecurityConfig) - retorna 401 sem token e 403
     * para outros perfis.
     */
    @PostMapping("/movimentacoes")
    @ResponseStatus(HttpStatus.CREATED)
    public MovimentacaoResponse movimentar(@Valid @RequestBody MovimentacaoRequest request, Authentication authentication) {
        String usuario = authentication != null ? authentication.getName() : null;
        return estoqueService.movimentar(request, usuario);
    }
}
