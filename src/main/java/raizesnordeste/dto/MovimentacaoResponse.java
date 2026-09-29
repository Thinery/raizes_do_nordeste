package raizesnordeste.dto;

import raizesnordeste.model.MovimentacaoEstoque;

public record MovimentacaoResponse(
        Long id,
        Long produtoId,
        String produtoNome,
        Long unidadeId,
        String unidadeNome,
        String tipo,
        Integer quantidade,
        Integer saldoResultante,
        String dataHora
) {
    public static MovimentacaoResponse de(MovimentacaoEstoque m) {
        return new MovimentacaoResponse(
                m.getId(),
                m.getProduto().getId(), m.getProduto().getNome(),
                m.getUnidade().getId(), m.getUnidade().getNome(),
                m.getTipo().name(), m.getQuantidade(), m.getSaldoResultante(),
                m.getDataHora().toString());
    }
}
