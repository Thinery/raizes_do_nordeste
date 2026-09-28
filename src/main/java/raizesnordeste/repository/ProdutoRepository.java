package raizesnordeste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raizesnordeste.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}