package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Camisetas;
import com.mycompany.myapp.repository.CamisetasRepository;
import com.mycompany.myapp.service.CamisetasService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Camisetas}.
 */
@RestController
@RequestMapping("/api")
public class CamisetasResource {

    private final Logger log = LoggerFactory.getLogger(CamisetasResource.class);

    private static final String ENTITY_NAME = "camisetas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CamisetasService camisetasService;

    private final CamisetasRepository camisetasRepository;

    public CamisetasResource(CamisetasService camisetasService, CamisetasRepository camisetasRepository) {
        this.camisetasService = camisetasService;
        this.camisetasRepository = camisetasRepository;
    }

    /**
     * {@code POST  /camisetas} : Create a new camisetas.
     *
     * @param camisetas the camisetas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new camisetas, or with status {@code 400 (Bad Request)} if the camisetas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/camisetas")
    public ResponseEntity<Camisetas> createCamisetas(@RequestBody Camisetas camisetas) throws URISyntaxException {
        log.debug("REST request to save Camisetas : {}", camisetas);
        if (camisetas.getId() != null) {
            throw new BadRequestAlertException("A new camisetas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Camisetas result = camisetasService.save(camisetas);
        return ResponseEntity
            .created(new URI("/api/camisetas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /camisetas/:id} : Updates an existing camisetas.
     *
     * @param id the id of the camisetas to save.
     * @param camisetas the camisetas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated camisetas,
     * or with status {@code 400 (Bad Request)} if the camisetas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the camisetas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/camisetas/{id}")
    public ResponseEntity<Camisetas> updateCamisetas(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Camisetas camisetas
    ) throws URISyntaxException {
        log.debug("REST request to update Camisetas : {}, {}", id, camisetas);
        if (camisetas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, camisetas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!camisetasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Camisetas result = camisetasService.save(camisetas);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, camisetas.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /camisetas/:id} : Partial updates given fields of an existing camisetas, field will ignore if it is null
     *
     * @param id the id of the camisetas to save.
     * @param camisetas the camisetas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated camisetas,
     * or with status {@code 400 (Bad Request)} if the camisetas is not valid,
     * or with status {@code 404 (Not Found)} if the camisetas is not found,
     * or with status {@code 500 (Internal Server Error)} if the camisetas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/camisetas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Camisetas> partialUpdateCamisetas(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Camisetas camisetas
    ) throws URISyntaxException {
        log.debug("REST request to partial update Camisetas partially : {}, {}", id, camisetas);
        if (camisetas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, camisetas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!camisetasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Camisetas> result = camisetasService.partialUpdate(camisetas);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, camisetas.getId().toString())
        );
    }

    /**
     * {@code GET  /camisetas} : get all the camisetas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of camisetas in body.
     */
    @GetMapping("/camisetas")
    public ResponseEntity<List<Camisetas>> getAllCamisetas(Pageable pageable) {
        log.debug("REST request to get a page of Camisetas");
        Page<Camisetas> page = camisetasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /camisetas/:id} : get the "id" camisetas.
     *
     * @param id the id of the camisetas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the camisetas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/camisetas/{id}")
    public ResponseEntity<Camisetas> getCamisetas(@PathVariable Long id) {
        log.debug("REST request to get Camisetas : {}", id);
        Optional<Camisetas> camisetas = camisetasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(camisetas);
    }

    /**
     * {@code DELETE  /camisetas/:id} : delete the "id" camisetas.
     *
     * @param id the id of the camisetas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/camisetas/{id}")
    public ResponseEntity<Void> deleteCamisetas(@PathVariable Long id) {
        log.debug("REST request to delete Camisetas : {}", id);
        camisetasService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
