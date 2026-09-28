package raizesnordeste.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import raizesnordeste.model.Estoque;
import raizesnordeste.repository.EstoqueRepository;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    @Autowired
    private EstoqueRepository repository;

    @GetMapping
    public List<Estoque> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Estoque buscarPorId(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public Estoque salvar(@RequestBody Estoque estoque) {
        return repository.save(estoque);
    }

    @PutMapping("/{id}")
    public Estoque atualizar(@PathVariable Long id, @RequestBody Estoque estoque) {
        Estoque existente = repository.findById(id).orElse(null);

        if (existente == null) {
            return null;
        }

        existente.setProduto(estoque.getProduto());
        existente.setUnidade(estoque.getUnidade());
        existente.setQuantidade(estoque.getQuantidade());

        return repository.save(existente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }
}