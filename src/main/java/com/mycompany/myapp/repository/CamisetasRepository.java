package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Camisetas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Camisetas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CamisetasRepository extends JpaRepository<Camisetas, Long> {}
