package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Accesorios;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Accesorios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccesoriosRepository extends JpaRepository<Accesorios, Long> {}
