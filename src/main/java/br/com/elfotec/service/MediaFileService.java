package br.com.elfotec.service;

import br.com.elfotec.service.dto.MediaFileDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.elfotec.domain.MediaFile}.
 */
public interface MediaFileService {
    /**
     * Save a mediaFile.
     *
     * @param mediaFileDTO the entity to save.
     * @return the persisted entity.
     */
    MediaFileDTO save(MediaFileDTO mediaFileDTO);

    /**
     * Partially updates a mediaFile.
     *
     * @param mediaFileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MediaFileDTO> partialUpdate(MediaFileDTO mediaFileDTO);

    /**
     * Get all the mediaFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MediaFileDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mediaFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MediaFileDTO> findOne(Long id);

    /**
     * Delete the "id" mediaFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    /**
     * Logically delete the "id" mediaFile.
     *
     * @param id the id of the entity.
     */

}
