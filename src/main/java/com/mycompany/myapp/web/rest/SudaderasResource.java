package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Sudaderas;
import com.mycompany.myapp.repository.SudaderasRepository;
import com.mycompany.myapp.service.SudaderasService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Sudaderas}.
 */
@RestController
@RequestMapping("/api")
public class SudaderasResource {

    private final Logger log = LoggerFactory.getLogger(SudaderasResource.class);

    private static final String ENTITY_NAME = "sudaderas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SudaderasService sudaderasService;

    private final SudaderasRepository sudaderasRepository;

    public SudaderasResource(SudaderasService sudaderasService, SudaderasRepository sudaderasRepository) {
        this.sudaderasService = sudaderasService;
        this.sudaderasRepository = sudaderasRepository;
    }

    /**
     * {@code POST  /sudaderas} : Create a new sudaderas.
     *
     * @param sudaderas the sudaderas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sudaderas, or with status {@code 400 (Bad Request)} if the sudaderas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sudaderas")
    public ResponseEntity<Sudaderas> createSudaderas(@RequestBody Sudaderas sudaderas) throws URISyntaxException {
        log.debug("REST request to save Sudaderas : {}", sudaderas);
        if (sudaderas.getId() != null) {
            throw new BadRequestAlertException("A new sudaderas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sudaderas result = sudaderasService.save(sudaderas);
        return ResponseEntity
            .created(new URI("/api/sudaderas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sudaderas/:id} : Updates an existing sudaderas.
     *
     * @param id the id of the sudaderas to save.
     * @param sudaderas the sudaderas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sudaderas,
     * or with status {@code 400 (Bad Request)} if the sudaderas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sudaderas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sudaderas/{id}")
    public ResponseEntity<Sudaderas> updateSudaderas(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sudaderas sudaderas
    ) throws URISyntaxException {
        log.debug("REST request to update Sudaderas : {}, {}", id, sudaderas);
        if (sudaderas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sudaderas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sudaderasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Sudaderas result = sudaderasService.save(sudaderas);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sudaderas.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sudaderas/:id} : Partial updates given fields of an existing sudaderas, field will ignore if it is null
     *
     * @param id the id of the sudaderas to save.
     * @param sudaderas the sudaderas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sudaderas,
     * or with status {@code 400 (Bad Request)} if the sudaderas is not valid,
     * or with status {@code 404 (Not Found)} if the sudaderas is not found,
     * or with status {@code 500 (Internal Server Error)} if the sudaderas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sudaderas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Sudaderas> partialUpdateSudaderas(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sudaderas sudaderas
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sudaderas partially : {}, {}", id, sudaderas);
        if (sudaderas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sudaderas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sudaderasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sudaderas> result = sudaderasService.partialUpdate(sudaderas);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sudaderas.getId().toString())
        );
    }

    /**
     * {@code GET  /sudaderas} : get all the sudaderas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sudaderas in body.
     */
    @GetMapping("/sudaderas")
    public ResponseEntity<List<Sudaderas>> getAllSudaderas(Pageable pageable) {
        log.debug("REST request to get a page of Sudaderas");
        Page<Sudaderas> page = sudaderasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sudaderas/:id} : get the "id" sudaderas.
     *
     * @param id the id of the sudaderas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sudaderas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sudaderas/{id}")
    public ResponseEntity<Sudaderas> getSudaderas(@PathVariable Long id) {
        log.debug("REST request to get Sudaderas : {}", id);
        Optional<Sudaderas> sudaderas = sudaderasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sudaderas);
    }

    /**
     * {@code DELETE  /sudaderas/:id} : delete the "id" sudaderas.
     *
     * @param id the id of the sudaderas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sudaderas/{id}")
    public ResponseEntity<Void> deleteSudaderas(@PathVariable Long id) {
        log.debug("REST request to delete Sudaderas : {}", id);
        sudaderasService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
