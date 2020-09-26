package com.example.appsoa.repository;

import com.example.appsoa.domain.Cliente;
import com.example.appsoa.domain.Venta;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Spring Data  repository for the Venta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findAllByCliente(@NotNull Cliente cliente);

    @Query(value = "SELECT V.ID FROM VENTA V WHERE V.CLIENTE_ID = :clienteId", nativeQuery = true)
    List<Long> findAllByClienteId(@Param("clienteId") Long clienteId);
}
