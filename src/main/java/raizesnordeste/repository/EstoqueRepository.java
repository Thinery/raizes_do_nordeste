package raizesnordeste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import raizesnordeste.model.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

}