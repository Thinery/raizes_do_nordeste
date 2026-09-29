package raizesnordeste.model;

import jakarta.persistence.*;

/**
 * Representa o SALDO atual de um produto em uma unidade.
 * O historico de entradas/saidas fica em MovimentacaoEstoque.
 */
@Entity
@Table(name = "estoques", uniqueConstraints = @UniqueConstraint(columnNames = {"produto_id", "unidade_id"}))
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Produto produto;

    @ManyToOne(optional = false)
    private Unidade unidade;

    private Integer quantidade = 0;

    public Estoque() {
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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
