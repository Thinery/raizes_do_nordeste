package raizesnordeste.dto;

import raizesnordeste.model.Cliente;

public record ClienteResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpfMascarado,
        Integer pontos,
        Boolean consentimentoLgpd
) {
    public static ClienteResponse de(Cliente c) {
        return new ClienteResponse(
                c.getId(), c.getNome(), c.getEmail(), c.getTelefone(),
                mascarar(c.getCpf()), c.getPontos(), c.getConsentimentoLgpd());
    }

    // LGPD: nunca expor o CPF completo nas respostas da API.
    private static String mascarar(String cpf) {
        if (cpf == null || cpf.length() < 4) {
            return "***";
        }
        return "***.***.***-" + cpf.substring(cpf.length() - 2);
    }
}
