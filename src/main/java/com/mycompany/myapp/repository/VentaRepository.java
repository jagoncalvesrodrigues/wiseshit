package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Venta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Venta entity.
 */
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    @Query(
        value = "select distinct venta from Venta venta left join fetch venta.camisetas left join fetch venta.sudaderas left join fetch venta.accesorios",
        countQuery = "select count(distinct venta) from Venta venta"
    )
    Page<Venta> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct venta from Venta venta left join fetch venta.camisetas left join fetch venta.sudaderas left join fetch venta.accesorios"
    )
    List<Venta> findAllWithEagerRelationships();

    @Query(
        "select venta from Venta venta left join fetch venta.camisetas left join fetch venta.sudaderas left join fetch venta.accesorios where venta.id =:id"
    )
    Optional<Venta> findOneWithEagerRelationships(@Param("id") Long id);
}
