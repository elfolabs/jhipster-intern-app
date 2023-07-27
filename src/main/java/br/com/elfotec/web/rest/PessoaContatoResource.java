package br.com.elfotec.web.rest;

import br.com.elfotec.repository.PessoaContatoRepository;
import br.com.elfotec.service.PessoaContatoService;
import br.com.elfotec.service.dto.PessoaContatoDTO;
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
 * REST controller for managing {@link br.com.elfotec.domain.PessoaContato}.
 */
@RestController
@RequestMapping("/api")
public class PessoaContatoResource {

    private final Logger log = LoggerFactory.getLogger(PessoaContatoResource.class);

    private static final String ENTITY_NAME = "pessoaContato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PessoaContatoService pessoaContatoService;

    private final PessoaContatoRepository pessoaContatoRepository;

    public PessoaContatoResource(PessoaContatoService pessoaContatoService, PessoaContatoRepository pessoaContatoRepository) {
        this.pessoaContatoService = pessoaContatoService;
        this.pessoaContatoRepository = pessoaContatoRepository;
    }

    /**
     * {@code POST  /pessoa-contatoes} : Create a new pessoaContato.
     *
     * @param pessoaContatoDTO the pessoaContatoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoaContatoDTO, or with status {@code 400 (Bad Request)} if the pessoaContato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pessoa-contatoes")
    public ResponseEntity<PessoaContatoDTO> createPessoaContato(@Valid @RequestBody PessoaContatoDTO pessoaContatoDTO)
        throws URISyntaxException {
        log.debug("REST request to save PessoaContato : {}", pessoaContatoDTO);
        if (pessoaContatoDTO.getId() != null) {
            throw new BadRequestAlertException("A new pessoaContato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PessoaContatoDTO result = pessoaContatoService.save(pessoaContatoDTO);
        return ResponseEntity
            .created(new URI("/api/pessoa-contatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pessoa-contatoes/:id} : Updates an existing pessoaContato.
     *
     * @param id the id of the pessoaContatoDTO to save.
     * @param pessoaContatoDTO the pessoaContatoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pessoaContatoDTO,
     * or with status {@code 400 (Bad Request)} if the pessoaContatoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pessoaContatoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pessoa-contatoes/{id}")
    public ResponseEntity<PessoaContatoDTO> updatePessoaContato(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PessoaContatoDTO pessoaContatoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PessoaContato : {}, {}", id, pessoaContatoDTO);
        if (pessoaContatoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pessoaContatoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pessoaContatoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PessoaContatoDTO result = pessoaContatoService.save(pessoaContatoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pessoaContatoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pessoa-contatoes/:id} : Partial updates given fields of an existing pessoaContato, field will ignore if it is null
     *
     * @param id the id of the pessoaContatoDTO to save.
     * @param pessoaContatoDTO the pessoaContatoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pessoaContatoDTO,
     * or with status {@code 400 (Bad Request)} if the pessoaContatoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pessoaContatoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pessoaContatoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pessoa-contatoes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PessoaContatoDTO> partialUpdatePessoaContato(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PessoaContatoDTO pessoaContatoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PessoaContato partially : {}, {}", id, pessoaContatoDTO);
        if (pessoaContatoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pessoaContatoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pessoaContatoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PessoaContatoDTO> result = pessoaContatoService.partialUpdate(pessoaContatoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pessoaContatoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pessoa-contatoes} : get all the pessoaContatoes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pessoaContatoes in body.
     */
    @GetMapping("/pessoa-contatoes")
    public ResponseEntity<List<PessoaContatoDTO>> getAllPessoaContatoes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PessoaContatoes");
        Page<PessoaContatoDTO> page = pessoaContatoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pessoa-contatoes/:id} : get the "id" pessoaContato.
     *
     * @param id the id of the pessoaContatoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pessoaContatoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pessoa-contatoes/{id}")
    public ResponseEntity<PessoaContatoDTO> getPessoaContato(@PathVariable Long id) {
        log.debug("REST request to get PessoaContato : {}", id);
        Optional<PessoaContatoDTO> pessoaContatoDTO = pessoaContatoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pessoaContatoDTO);
    }

    /**
     * {@code DELETE  /pessoa-contatoes/:id} : delete the "id" pessoaContato.
     *
     * @param id the id of the pessoaContatoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pessoa-contatoes/{id}")
    public ResponseEntity<Void> deletePessoaContato(@PathVariable Long id) {
        log.debug("REST request to delete PessoaContato : {}", id);
        pessoaContatoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
