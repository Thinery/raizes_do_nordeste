package raizesnordeste.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovimentacaoRequest(
        @NotNull Long produtoId,
        @NotNull Long unidadeId,
        @NotNull String tipo,
        @NotNull @Positive Integer quantidade
) {
}
