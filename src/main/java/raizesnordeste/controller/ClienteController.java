package raizesnordeste.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import raizesnordeste.audit.AuditoriaService;
import raizesnordeste.dto.ClienteRequest;
import raizesnordeste.dto.ClienteResponse;
import raizesnordeste.exception.RecursoNaoEncontradoException;
import raizesnordeste.model.Cliente;
import raizesnordeste.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository repository;
    private final AuditoriaService auditoriaService;

    public ClienteController(ClienteRepository repository, AuditoriaService auditoriaService) {
        this.repository = repository;
        this.auditoriaService = auditoriaService;
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return repository.findAll().stream().map(ClienteResponse::de).toList();
    }

    @GetMapping("/{id}")
    public ClienteResponse buscarPorId(@PathVariable Long id) {
        return ClienteResponse.de(buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse salvar(@Valid @RequestBody ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNome(request.nome());
        cliente.setEmail(request.email());
        cliente.setTelefone(request.telefone());
        cliente.setCpf(request.cpf());
        cliente.setConsentimentoLgpd(request.consentimentoLgpd());
        cliente.setDataConsentimento(LocalDateTime.now());

        Cliente salvo = repository.save(cliente);
        // Acao sensivel (dado pessoal + LGPD): fica registrada na auditoria.
        auditoriaService.registrar(null, "CADASTRO_CLIENTE", "cliente id=" + salvo.getId() + " cadastrado com consentimento LGPD");
        return ClienteResponse.de(salvo);
    }

    private Cliente buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("CLIENTE_NAO_ENCONTRADO", "cliente nao encontrado."));
    }
}
