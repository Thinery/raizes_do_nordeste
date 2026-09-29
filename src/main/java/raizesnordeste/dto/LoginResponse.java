package raizesnordeste.dto;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        UsuarioResumo user
) {
    public record UsuarioResumo(Long id, String nome, String perfil) {
    }
}
