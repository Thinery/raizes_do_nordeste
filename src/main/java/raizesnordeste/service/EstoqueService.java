package raizesnordeste.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import raizesnordeste.audit.AuditoriaService;
import raizesnordeste.dto.MovimentacaoRequest;
import raizesnordeste.dto.MovimentacaoResponse;
import raizesnordeste.exception.NegocioException;
import raizesnordeste.exception.RecursoNaoEncontradoException;
import raizesnordeste.model.Estoque;
import raizesnordeste.model.MovimentacaoEstoque;
import raizesnordeste.model.Produto;
import raizesnordeste.model.TipoMovimentacao;
import raizesnordeste.model.Unidade;
import raizesnordeste.repository.EstoqueRepository;
import raizesnordeste.repository.MovimentacaoEstoqueRepository;
import raizesnordeste.repository.ProdutoRepository;
import raizesnordeste.repository.UnidadeRepository;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final MovimentacaoEstoqueRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final UnidadeRepository unidadeRepository;
    private final AuditoriaService auditoriaService;

    public EstoqueService(EstoqueRepository estoqueRepository,
                           MovimentacaoEstoqueRepository movimentacaoRepository,
                           ProdutoRepository produtoRepository,
                           UnidadeRepository unidadeRepository,
                           AuditoriaService auditoriaService) {
        this.estoqueRepository = estoqueRepository;
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoRepository = produtoRepository;
        this.unidadeRepository = unidadeRepository;
        this.auditoriaService = auditoriaService;
    }

    @Transactional
    public MovimentacaoResponse movimentar(MovimentacaoRequest request, String usuarioResponsavel) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("PRODUTO_NAO_ENCONTRADO", "produto nao encontrado."));
        Unidade unidade = unidadeRepository.findById(request.unidadeId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("UNIDADE_NAO_ENCONTRADA", "unidade nao encontrada."));

        TipoMovimentacao tipo;
        try {
            tipo = TipoMovimentacao.valueOf(request.tipo().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NegocioException("TIPO_INVALIDO", "tipo deve ser ENTRADA ou SAIDA.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Estoque saldo = estoqueRepository.findByProdutoIdAndUnidadeId(produto.getId(), unidade.getId())
                .orElseGet(() -> {
                    Estoque novo = new Estoque();
                    novo.setProduto(produto);
                    novo.setUnidade(unidade);
                    novo.setQuantidade(0);
                    return novo;
                });

        if (tipo == TipoMovimentacao.SAIDA && request.quantidade() > saldo.getQuantidade()) {
            throw new NegocioException("ESTOQUE_INSUFICIENTE",
                    "Nao ha quantidade suficiente em estoque para esta unidade. Disponivel: " + saldo.getQuantidade());
        }

        int novaQuantidade = tipo == TipoMovimentacao.ENTRADA
                ? saldo.getQuantidade() + request.quantidade()
                : saldo.getQuantidade() - request.quantidade();
        saldo.setQuantidade(novaQuantidade);
        estoqueRepository.save(saldo);

        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setProduto(produto);
        movimentacao.setUnidade(unidade);
        movimentacao.setTipo(tipo);
        movimentacao.setQuantidade(request.quantidade());
        movimentacao.setSaldoResultante(novaQuantidade);
        movimentacao.setUsuarioResponsavel(usuarioResponsavel);
        movimentacaoRepository.save(movimentacao);

        auditoriaService.registrar(usuarioResponsavel, "MOVIMENTACAO_ESTOQUE",
                tipo + " de " + request.quantidade() + " un. do produto " + produto.getId()
                        + " na unidade " + unidade.getId() + " (saldo final: " + novaQuantidade + ")");

        return MovimentacaoResponse.de(movimentacao);
    }
}
