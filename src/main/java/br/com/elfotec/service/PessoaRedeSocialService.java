package br.com.elfotec.service;

import br.com.elfotec.service.dto.PessoaRedeSocialDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.elfotec.domain.PessoaRedeSocial}.
 */
public interface PessoaRedeSocialService {
    /**
     * Save a pessoaRedeSocial.
     *
     * @param pessoaRedeSocialDTO the entity to save.
     * @return the persisted entity.
     */
    PessoaRedeSocialDTO save(PessoaRedeSocialDTO pessoaRedeSocialDTO);

    /**
     * Partially updates a pessoaRedeSocial.
     *
     * @param pessoaRedeSocialDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PessoaRedeSocialDTO> partialUpdate(PessoaRedeSocialDTO pessoaRedeSocialDTO);

    /**
     * Get all the pessoaRedeSocials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PessoaRedeSocialDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pessoaRedeSocial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PessoaRedeSocialDTO> findOne(Long id);

    /**
     * Delete the "id" pessoaRedeSocial.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
