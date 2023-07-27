package br.com.elfotec.web.rest;

import br.com.elfotec.repository.PessoaRedeSocialRepository;
import br.com.elfotec.service.PessoaRedeSocialService;
import br.com.elfotec.service.dto.PessoaRedeSocialDTO;
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
 * REST controller for managing {@link br.com.elfotec.domain.PessoaRedeSocial}.
 */
@RestController
@RequestMapping("/api")
public class PessoaRedeSocialResource {

    private final Logger log = LoggerFactory.getLogger(PessoaRedeSocialResource.class);

    private static final String ENTITY_NAME = "pessoaRedeSocial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PessoaRedeSocialService pessoaRedeSocialService;

    private final PessoaRedeSocialRepository pessoaRedeSocialRepository;

    public PessoaRedeSocialResource(
        PessoaRedeSocialService pessoaRedeSocialService,
        PessoaRedeSocialRepository pessoaRedeSocialRepository
    ) {
        this.pessoaRedeSocialService = pessoaRedeSocialService;
        this.pessoaRedeSocialRepository = pessoaRedeSocialRepository;
    }

    /**
     * {@code POST  /pessoa-rede-socials} : Create a new pessoaRedeSocial.
     *
     * @param pessoaRedeSocialDTO the pessoaRedeSocialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoaRedeSocialDTO, or with status {@code 400 (Bad Request)} if the pessoaRedeSocial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pessoa-rede-socials")
    public ResponseEntity<PessoaRedeSocialDTO> createPessoaRedeSocial(@Valid @RequestBody PessoaRedeSocialDTO pessoaRedeSocialDTO)
        throws URISyntaxException {
        log.debug("REST request to save PessoaRedeSocial : {}", pessoaRedeSocialDTO);
        if (pessoaRedeSocialDTO.getId() != null) {
            throw new BadRequestAlertException("A new pessoaRedeSocial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PessoaRedeSocialDTO result = pessoaRedeSocialService.save(pessoaRedeSocialDTO);
        return ResponseEntity
            .created(new URI("/api/pessoa-rede-socials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pessoa-rede-socials/:id} : Updates an existing pessoaRedeSocial.
     *
     * @param id the id of the pessoaRedeSocialDTO to save.
     * @param pessoaRedeSocialDTO the pessoaRedeSocialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pessoaRedeSocialDTO,
     * or with status {@code 400 (Bad Request)} if the pessoaRedeSocialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pessoaRedeSocialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pessoa-rede-socials/{id}")
    public ResponseEntity<PessoaRedeSocialDTO> updatePessoaRedeSocial(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PessoaRedeSocialDTO pessoaRedeSocialDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PessoaRedeSocial : {}, {}", id, pessoaRedeSocialDTO);
        if (pessoaRedeSocialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pessoaRedeSocialDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pessoaRedeSocialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PessoaRedeSocialDTO result = pessoaRedeSocialService.save(pessoaRedeSocialDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pessoaRedeSocialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pessoa-rede-socials/:id} : Partial updates given fields of an existing pessoaRedeSocial, field will ignore if it is null
     *
     * @param id the id of the pessoaRedeSocialDTO to save.
     * @param pessoaRedeSocialDTO the pessoaRedeSocialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pessoaRedeSocialDTO,
     * or with status {@code 400 (Bad Request)} if the pessoaRedeSocialDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pessoaRedeSocialDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pessoaRedeSocialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pessoa-rede-socials/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PessoaRedeSocialDTO> partialUpdatePessoaRedeSocial(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PessoaRedeSocialDTO pessoaRedeSocialDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PessoaRedeSocial partially : {}, {}", id, pessoaRedeSocialDTO);
        if (pessoaRedeSocialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pessoaRedeSocialDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pessoaRedeSocialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PessoaRedeSocialDTO> result = pessoaRedeSocialService.partialUpdate(pessoaRedeSocialDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pessoaRedeSocialDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pessoa-rede-socials} : get all the pessoaRedeSocials.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pessoaRedeSocials in body.
     */
    @GetMapping("/pessoa-rede-socials")
    public ResponseEntity<List<PessoaRedeSocialDTO>> getAllPessoaRedeSocials(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PessoaRedeSocials");
        Page<PessoaRedeSocialDTO> page = pessoaRedeSocialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pessoa-rede-socials/:id} : get the "id" pessoaRedeSocial.
     *
     * @param id the id of the pessoaRedeSocialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pessoaRedeSocialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pessoa-rede-socials/{id}")
    public ResponseEntity<PessoaRedeSocialDTO> getPessoaRedeSocial(@PathVariable Long id) {
        log.debug("REST request to get PessoaRedeSocial : {}", id);
        Optional<PessoaRedeSocialDTO> pessoaRedeSocialDTO = pessoaRedeSocialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pessoaRedeSocialDTO);
    }

    /**
     * {@code DELETE  /pessoa-rede-socials/:id} : delete the "id" pessoaRedeSocial.
     *
     * @param id the id of the pessoaRedeSocialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pessoa-rede-socials/{id}")
    public ResponseEntity<Void> deletePessoaRedeSocial(@PathVariable Long id) {
        log.debug("REST request to delete PessoaRedeSocial : {}", id);
        pessoaRedeSocialService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
