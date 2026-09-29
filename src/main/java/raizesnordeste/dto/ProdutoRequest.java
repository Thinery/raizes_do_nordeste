package raizesnordeste.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProdutoRequest(
        @NotBlank String nome,
        String descricao,
        @Positive(message = "o preco deve ser maior que zero") Double preco
) {
}
