package br.com.elfotec.repository;

import br.com.elfotec.domain.PessoaContato;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PessoaContato entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PessoaContatoRepository extends JpaRepository<PessoaContato, Long> {}
