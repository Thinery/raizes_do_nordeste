package raizesnordeste.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import raizesnordeste.dto.ProdutoRequest;
import raizesnordeste.exception.RecursoNaoEncontradoException;
import raizesnordeste.model.Produto;
import raizesnordeste.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    /** page e 1-based (page=1 = primeira pagina), conforme exemplo do roteiro. */
    @GetMapping
    public Page<Produto> listar(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        int paginaZeroIndexed = Math.max(page - 1, 0);
        return repository.findAll(PageRequest.of(paginaZeroIndexed, limit));
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id) {
        return buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto salvar(@Valid @RequestBody ProdutoRequest request) {
        Produto produto = new Produto();
        produto.setNome(request.nome());
        produto.setDescricao(request.descricao());
        produto.setPreco(request.preco());
        return repository.save(produto);
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequest request) {
        Produto existente = buscarOuFalhar(id);
        existente.setNome(request.nome());
        existente.setDescricao(request.descricao());
        existente.setPreco(request.preco());
        return repository.save(existente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        buscarOuFalhar(id);
        repository.deleteById(id);
    }

    private Produto buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("PRODUTO_NAO_ENCONTRADO", "produto nao encontrado."));
    }
}
