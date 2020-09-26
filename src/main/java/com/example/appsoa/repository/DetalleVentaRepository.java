package com.example.appsoa.repository;

import com.example.appsoa.domain.DetalleVenta;

import com.example.appsoa.domain.Venta;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Spring Data  repository for the DetalleVenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    List<DetalleVenta> findAllByVenta(@NotNull Venta venta);

    List<DetalleVenta> findAllByVentaIn(Collection<@NotNull Venta> venta);
}
