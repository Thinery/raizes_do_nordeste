package raizesnordeste.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import raizesnordeste.model.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    List<Estoque> findByUnidadeId(Long unidadeId);
    Optional<Estoque> findByProdutoIdAndUnidadeId(Long produtoId, Long unidadeId);
}
