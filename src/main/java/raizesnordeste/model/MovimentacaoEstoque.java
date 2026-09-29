package raizesnordeste.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "movimentacoes_estoque")
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Produto produto;

    @ManyToOne(optional = false)
    private Unidade unidade;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo;

    @NotNull
    @Positive
    private Integer quantidade;

    /** Saldo do produto na unidade logo apos esta movimentacao (auditoria/historico). */
    private Integer saldoResultante;

    private LocalDateTime dataHora = LocalDateTime.now();

    /** E-mail do usuario que realizou a movimentacao (extraido do token JWT). */
    private String usuarioResponsavel;

    public MovimentacaoEstoque() {
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getSaldoResultante() {
        return saldoResultante;
    }

    public void setSaldoResultante(Integer saldoResultante) {
        this.saldoResultante = saldoResultante;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getUsuarioResponsavel() {
        return usuarioResponsavel;
    }

    public void setUsuarioResponsavel(String usuarioResponsavel) {
        this.usuarioResponsavel = usuarioResponsavel;
    }
}
