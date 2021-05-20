package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Sudaderas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Sudaderas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SudaderasRepository extends JpaRepository<Sudaderas, Long> {}
