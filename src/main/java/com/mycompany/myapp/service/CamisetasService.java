package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Camisetas;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Camisetas}.
 */
public interface CamisetasService {
    /**
     * Save a camisetas.
     *
     * @param camisetas the entity to save.
     * @return the persisted entity.
     */
    Camisetas save(Camisetas camisetas);

    /**
     * Partially updates a camisetas.
     *
     * @param camisetas the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Camisetas> partialUpdate(Camisetas camisetas);

    /**
     * Get all the camisetas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Camisetas> findAll(Pageable pageable);

    /**
     * Get the "id" camisetas.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Camisetas> findOne(Long id);

    /**
     * Delete the "id" camisetas.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
