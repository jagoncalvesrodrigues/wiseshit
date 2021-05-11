package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Sudaderas;
import com.mycompany.myapp.repository.SudaderasRepository;
import com.mycompany.myapp.service.SudaderasService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sudaderas}.
 */
@Service
@Transactional
public class SudaderasServiceImpl implements SudaderasService {

    private final Logger log = LoggerFactory.getLogger(SudaderasServiceImpl.class);

    private final SudaderasRepository sudaderasRepository;

    public SudaderasServiceImpl(SudaderasRepository sudaderasRepository) {
        this.sudaderasRepository = sudaderasRepository;
    }

    @Override
    public Sudaderas save(Sudaderas sudaderas) {
        log.debug("Request to save Sudaderas : {}", sudaderas);
        return sudaderasRepository.save(sudaderas);
    }

    @Override
    public Optional<Sudaderas> partialUpdate(Sudaderas sudaderas) {
        log.debug("Request to partially update Sudaderas : {}", sudaderas);

        return sudaderasRepository
            .findById(sudaderas.getId())
            .map(
                existingSudaderas -> {
                    if (sudaderas.getStock() != null) {
                        existingSudaderas.setStock(sudaderas.getStock());
                    }
                    if (sudaderas.getImagen() != null) {
                        existingSudaderas.setImagen(sudaderas.getImagen());
                    }
                    if (sudaderas.getTalla() != null) {
                        existingSudaderas.setTalla(sudaderas.getTalla());
                    }
                    if (sudaderas.getColor() != null) {
                        existingSudaderas.setColor(sudaderas.getColor());
                    }
                    if (sudaderas.getColeccion() != null) {
                        existingSudaderas.setColeccion(sudaderas.getColeccion());
                    }

                    return existingSudaderas;
                }
            )
            .map(sudaderasRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sudaderas> findAll(Pageable pageable) {
        log.debug("Request to get all Sudaderas");
        return sudaderasRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sudaderas> findOne(Long id) {
        log.debug("Request to get Sudaderas : {}", id);
        return sudaderasRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sudaderas : {}", id);
        sudaderasRepository.deleteById(id);
    }
}
