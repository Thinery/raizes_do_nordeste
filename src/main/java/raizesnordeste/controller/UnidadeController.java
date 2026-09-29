package raizesnordeste.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import raizesnordeste.dto.SaldoEstoqueResponse;
import raizesnordeste.dto.UnidadeRequest;
import raizesnordeste.exception.RecursoNaoEncontradoException;
import raizesnordeste.model.Unidade;
import raizesnordeste.repository.EstoqueRepository;
import raizesnordeste.repository.UnidadeRepository;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {

    private final UnidadeRepository repository;
    private final EstoqueRepository estoqueRepository;

    public UnidadeController(UnidadeRepository repository, EstoqueRepository estoqueRepository) {
        this.repository = repository;
        this.estoqueRepository = estoqueRepository;
    }

    @GetMapping
    public List<Unidade> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Unidade buscarPorId(@PathVariable Long id) {
        return buscarOuFalhar(id);
    }

    /** Saldo de estoque (todos os produtos) da unidade - usado no fluxo B. */
    @GetMapping("/{id}/estoque")
    public List<SaldoEstoqueResponse> saldoPorUnidade(@PathVariable Long id) {
        buscarOuFalhar(id);
        return estoqueRepository.findByUnidadeId(id).stream().map(SaldoEstoqueResponse::de).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Unidade salvar(@Valid @RequestBody UnidadeRequest request) {
        Unidade unidade = new Unidade();
        unidade.setNome(request.nome());
        unidade.setCidade(request.cidade());
        unidade.setEndereco(request.endereco());
        return repository.save(unidade);
    }

    @PutMapping("/{id}")
    public Unidade atualizar(@PathVariable Long id, @Valid @RequestBody UnidadeRequest request) {
        Unidade existente = buscarOuFalhar(id);
        existente.setNome(request.nome());
        existente.setCidade(request.cidade());
        existente.setEndereco(request.endereco());
        return repository.save(existente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        buscarOuFalhar(id);
        repository.deleteById(id);
    }

    private Unidade buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("UNIDADE_NAO_ENCONTRADA", "unidade nao encontrada."));
    }
}
