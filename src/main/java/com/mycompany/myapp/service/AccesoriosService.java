package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Accesorios;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Accesorios}.
 */
public interface AccesoriosService {
    /**
     * Save a accesorios.
     *
     * @param accesorios the entity to save.
     * @return the persisted entity.
     */
    Accesorios save(Accesorios accesorios);

    /**
     * Partially updates a accesorios.
     *
     * @param accesorios the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Accesorios> partialUpdate(Accesorios accesorios);

    /**
     * Get all the accesorios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Accesorios> findAll(Pageable pageable);

    /**
     * Get the "id" accesorios.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Accesorios> findOne(Long id);

    /**
     * Delete the "id" accesorios.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
