package raizesnordeste.dto;

import jakarta.validation.constraints.NotBlank;

public record UnidadeRequest(
        @NotBlank String nome,
        @NotBlank String cidade,
        String endereco
) {
}
