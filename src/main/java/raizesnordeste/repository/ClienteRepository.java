package raizesnordeste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import raizesnordeste.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}