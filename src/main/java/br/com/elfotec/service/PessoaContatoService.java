package br.com.elfotec.service;

import br.com.elfotec.service.dto.PessoaContatoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.elfotec.domain.PessoaContato}.
 */
public interface PessoaContatoService {
    /**
     * Save a pessoaContato.
     *
     * @param pessoaContatoDTO the entity to save.
     * @return the persisted entity.
     */
    PessoaContatoDTO save(PessoaContatoDTO pessoaContatoDTO);

    /**
     * Partially updates a pessoaContato.
     *
     * @param pessoaContatoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PessoaContatoDTO> partialUpdate(PessoaContatoDTO pessoaContatoDTO);

    /**
     * Get all the pessoaContatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PessoaContatoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pessoaContato.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PessoaContatoDTO> findOne(Long id);

    /**
     * Delete the "id" pessoaContato.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
