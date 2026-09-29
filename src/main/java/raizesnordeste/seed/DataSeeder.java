package raizesnordeste.seed;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import raizesnordeste.model.Perfil;
import raizesnordeste.model.Produto;
import raizesnordeste.model.Unidade;
import raizesnordeste.model.Usuario;
import raizesnordeste.repository.ProdutoRepository;
import raizesnordeste.repository.UnidadeRepository;
import raizesnordeste.repository.UsuarioRepository;

/**
 * Popula dados minimos para o corretor conseguir testar a API imediatamente
 * apos subir o projeto, sem precisar cadastrar tudo manualmente.
 *
 * Credenciais de teste (documentadas tambem no README):
 *   ADMIN:   admin@raizesdonordeste.com   / admin123
 *   GERENTE: gerente@raizesdonordeste.com / gerente123
 *   CLIENTE: cliente@raizesdonordeste.com / cliente123
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final UnidadeRepository unidadeRepository;
    private final ProdutoRepository produtoRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UsuarioRepository usuarioRepository, UnidadeRepository unidadeRepository,
                       ProdutoRepository produtoRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.unidadeRepository = unidadeRepository;
        this.produtoRepository = produtoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        criarUsuarioSeNaoExiste("Administrador", "admin@raizesdonordeste.com", "admin123", Perfil.ADMIN);
        criarUsuarioSeNaoExiste("Gerente Unidade Recife", "gerente@raizesdonordeste.com", "gerente123", Perfil.GERENTE);
        criarUsuarioSeNaoExiste("Cliente Teste", "cliente@raizesdonordeste.com", "cliente123", Perfil.CLIENTE);

        if (unidadeRepository.count() == 0) {
            Unidade recife = new Unidade();
            recife.setNome("Raizes do Nordeste - Recife Centro");
            recife.setCidade("Recife");
            recife.setEndereco("Rua da Aurora, 100");
            unidadeRepository.save(recife);

            Unidade fortaleza = new Unidade();
            fortaleza.setNome("Raizes do Nordeste - Fortaleza Praia");
            fortaleza.setCidade("Fortaleza");
            fortaleza.setEndereco("Av. Beira Mar, 500");
            unidadeRepository.save(fortaleza);
        }

        if (produtoRepository.count() == 0) {
            Produto batataDoce = new Produto();
            batataDoce.setNome("Batata Doce Roxa");
            batataDoce.setDescricao("Porcao de batata doce roxa assada");
            batataDoce.setPreco(8.5);
            produtoRepository.save(batataDoce);

            Produto tapioca = new Produto();
            tapioca.setNome("Tapioca de Queijo Coalho");
            tapioca.setDescricao("Tapioca recheada com queijo coalho");
            tapioca.setPreco(12.9);
            produtoRepository.save(tapioca);
        }
    }

    private void criarUsuarioSeNaoExiste(String nome, String email, String senha, Perfil perfil) {
        if (usuarioRepository.existsByEmail(email)) {
            return;
        }
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setPerfil(perfil);
        usuarioRepository.save(usuario);
    }
}
