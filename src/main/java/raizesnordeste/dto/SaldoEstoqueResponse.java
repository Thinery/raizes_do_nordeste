package raizesnordeste.dto;

import raizesnordeste.model.Estoque;

public record SaldoEstoqueResponse(Long produtoId, String produtoNome, Long unidadeId, String unidadeNome, Integer quantidade) {
    public static SaldoEstoqueResponse de(Estoque e) {
        return new SaldoEstoqueResponse(
                e.getProduto().getId(), e.getProduto().getNome(),
                e.getUnidade().getId(), e.getUnidade().getNome(),
                e.getQuantidade());
    }
}
