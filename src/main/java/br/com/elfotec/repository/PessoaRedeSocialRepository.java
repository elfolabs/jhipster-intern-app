package br.com.elfotec.repository;

import br.com.elfotec.domain.PessoaRedeSocial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PessoaRedeSocial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PessoaRedeSocialRepository extends JpaRepository<PessoaRedeSocial, Long> {}
