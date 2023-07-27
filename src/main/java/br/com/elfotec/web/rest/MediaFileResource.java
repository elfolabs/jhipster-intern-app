package br.com.elfotec.web.rest;

import br.com.elfotec.repository.MediaFileRepository;
import br.com.elfotec.service.MediaFileService;
import br.com.elfotec.service.dto.MediaFileDTO;
import br.com.elfotec.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.elfotec.domain.MediaFile}.
 */
@RestController
@RequestMapping("/api")
public class MediaFileResource {

    private final Logger log = LoggerFactory.getLogger(MediaFileResource.class);

    private static final String ENTITY_NAME = "mediaFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MediaFileService mediaFileService;

    private final MediaFileRepository mediaFileRepository;

    public MediaFileResource(MediaFileService mediaFileService, MediaFileRepository mediaFileRepository) {
        this.mediaFileService = mediaFileService;
        this.mediaFileRepository = mediaFileRepository;
    }

    /**
     * {@code POST  /media-files} : Create a new mediaFile.
     *
     * @param mediaFileDTO the mediaFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mediaFileDTO, or with status {@code 400 (Bad Request)} if the mediaFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/media-files")
    public ResponseEntity<MediaFileDTO> createMediaFile(@Valid @RequestBody MediaFileDTO mediaFileDTO) throws URISyntaxException {
        log.debug("REST request to save MediaFile : {}", mediaFileDTO);
        if (mediaFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new mediaFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MediaFileDTO result = mediaFileService.save(mediaFileDTO);
        return ResponseEntity
            .created(new URI("/api/media-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /media-files/:id} : Updates an existing mediaFile.
     *
     * @param id the id of the mediaFileDTO to save.
     * @param mediaFileDTO the mediaFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mediaFileDTO,
     * or with status {@code 400 (Bad Request)} if the mediaFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mediaFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/media-files/{id}")
    public ResponseEntity<MediaFileDTO> updateMediaFile(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MediaFileDTO mediaFileDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MediaFile : {}, {}", id, mediaFileDTO);
        if (mediaFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mediaFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mediaFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MediaFileDTO result = mediaFileService.save(mediaFileDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mediaFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /media-files/:id} : Partial updates given fields of an existing mediaFile, field will ignore if it is null
     *
     * @param id the id of the mediaFileDTO to save.
     * @param mediaFileDTO the mediaFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mediaFileDTO,
     * or with status {@code 400 (Bad Request)} if the mediaFileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mediaFileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mediaFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/media-files/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MediaFileDTO> partialUpdateMediaFile(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MediaFileDTO mediaFileDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MediaFile partially : {}, {}", id, mediaFileDTO);
        if (mediaFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mediaFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mediaFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MediaFileDTO> result = mediaFileService.partialUpdate(mediaFileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mediaFileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /media-files} : get all the mediaFiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mediaFiles in body.
     */
    @GetMapping("/media-files")
    public ResponseEntity<List<MediaFileDTO>> getAllMediaFiles(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MediaFiles");
        Page<MediaFileDTO> page = mediaFileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /media-files/:id} : get the "id" mediaFile.
     *
     * @param id the id of the mediaFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mediaFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/media-files/{id}")
    public ResponseEntity<MediaFileDTO> getMediaFile(@PathVariable Long id) {
        log.debug("REST request to get MediaFile : {}", id);
        Optional<MediaFileDTO> mediaFileDTO = mediaFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mediaFileDTO);
    }

    /**
     * {@code DELETE  /media-files/:id} : delete the "id" mediaFile.
     *
     * @param id the id of the mediaFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/media-files/{id}")
    public ResponseEntity<Void> deleteMediaFile(@PathVariable Long id) {
        log.debug("REST request to delete MediaFile : {}", id);
        mediaFileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
