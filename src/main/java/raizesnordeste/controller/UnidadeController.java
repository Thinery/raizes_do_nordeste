package raizesnordeste.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import raizesnordeste.model.Unidade;
import raizesnordeste.repository.UnidadeRepository;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {

    @Autowired
    private UnidadeRepository repository;

    @GetMapping
    public List<Unidade> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Unidade salvar(@RequestBody Unidade unidade) {
        return repository.save(unidade);
    }


@GetMapping("/{id}")
public Unidade buscarPorId(@PathVariable Long id) {
    return repository.findById(id).orElse(null);
}

@PutMapping("/{id}")
public Unidade atualizar(@PathVariable Long id, @RequestBody Unidade unidade) {
    Unidade existente = repository.findById(id).orElse(null);

    if (existente == null) {
        return null;
    }

    existente.setNome(unidade.getNome());
    existente.setCidade(unidade.getCidade());
    existente.setEndereco(unidade.getEndereco());

    return repository.save(existente);
}

@DeleteMapping("/{id}")
public void excluir(@PathVariable Long id) {
    repository.deleteById(id);

}

}