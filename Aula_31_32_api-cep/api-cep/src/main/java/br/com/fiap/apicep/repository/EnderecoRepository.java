package br.com.fiap.apicep.repository;

import br.com.fiap.apicep.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
