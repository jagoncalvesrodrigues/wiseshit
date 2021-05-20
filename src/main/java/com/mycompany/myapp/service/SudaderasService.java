package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Sudaderas;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Sudaderas}.
 */

public interface SudaderasService {
    ///**
    // * Save a sudaderas.
    // *
    // * @param sudaderas the entity to save.
    // * @return the persisted entity.
    // */
    Sudaderas save(Sudaderas sudaderas);
    //
    ///**
    // * Partially updates a sudaderas.
    // *
    // * @param sudaderas the entity to update partially.
    // * @return the persisted entity.
    // */
    Optional<Sudaderas> partialUpdate(Sudaderas sudaderas);
    //
    ///**
    // * Get all the sudaderas.
    // *
    // * @param pageable the pagination information.
    // * @return the list of entities.
    // */
    Page<Sudaderas> findAll(Pageable pageable);
    //
    ///**
    // * Get the "id" sudaderas.
    // *
    // * @param id the id of the entity.
    // * @return the entity.
    // */
    Optional<Sudaderas> findOne(Long id);
    //
    ///**
    // * Delete the "id" sudaderas.
    // *
    // * @param id the id of the entity.
    // */
    void delete(Long id);
}
