package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Accesorios;
import com.mycompany.myapp.repository.AccesoriosRepository;
import com.mycompany.myapp.service.AccesoriosService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Accesorios}.
 */
@Service
@Transactional
public class AccesoriosServiceImpl implements AccesoriosService {

    private final Logger log = LoggerFactory.getLogger(AccesoriosServiceImpl.class);

    private final AccesoriosRepository accesoriosRepository;

    public AccesoriosServiceImpl(AccesoriosRepository accesoriosRepository) {
        this.accesoriosRepository = accesoriosRepository;
    }

    @Override
    public Accesorios save(Accesorios accesorios) {
        log.debug("Request to save Accesorios : {}", accesorios);
        return accesoriosRepository.save(accesorios);
    }

    @Override
    public Optional<Accesorios> partialUpdate(Accesorios accesorios) {
        log.debug("Request to partially update Accesorios : {}", accesorios);

        return accesoriosRepository
            .findById(accesorios.getId())
            .map(
                existingAccesorios -> {
                    if (accesorios.getStock() != null) {
                        existingAccesorios.setStock(accesorios.getStock());
                    }
                    if (accesorios.getImagen() != null) {
                        existingAccesorios.setImagen(accesorios.getImagen());
                    }
                    if (accesorios.getTalla() != null) {
                        existingAccesorios.setTalla(accesorios.getTalla());
                    }
                    if (accesorios.getColor() != null) {
                        existingAccesorios.setColor(accesorios.getColor());
                    }
                    if (accesorios.getColeccion() != null) {
                        existingAccesorios.setColeccion(accesorios.getColeccion());
                    }

                    return existingAccesorios;
                }
            )
            .map(accesoriosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Accesorios> findAll(Pageable pageable) {
        log.debug("Request to get all Accesorios");
        return accesoriosRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Accesorios> findOne(Long id) {
        log.debug("Request to get Accesorios : {}", id);
        return accesoriosRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Accesorios : {}", id);
        accesoriosRepository.deleteById(id);
    }
}
