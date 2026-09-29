package raizesnordeste.dto;

import raizesnordeste.model.Usuario;

public record UsuarioResponse(Long id, String nome, String email, String perfil) {
    public static UsuarioResponse de(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNome(), u.getEmail(), u.getPerfil().name());
    }
}
