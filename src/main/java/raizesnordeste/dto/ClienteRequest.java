package raizesnordeste.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        String telefone,
        @NotBlank String cpf,
        @AssertTrue(message = "e necessario registrar o consentimento LGPD para cadastrar o cliente")
        boolean consentimentoLgpd
) {
}
