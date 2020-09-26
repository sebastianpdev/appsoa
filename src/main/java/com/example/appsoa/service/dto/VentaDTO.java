package com.example.appsoa.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.example.appsoa.domain.Venta} entity.
 */
public class VentaDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant fecha;

    private Long clienteId;

    private String clienteNombre;

    private Set<DetalleVentaDTO> detalleVentas;


    // -------------------- GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public Set<DetalleVentaDTO> getDetalleVentas() {
        return detalleVentas;
    }

    public void setDetalleVentas(Set<DetalleVentaDTO> detalleVentas) {
        this.detalleVentas = detalleVentas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VentaDTO)) {
            return false;
        }

        return id != null && id.equals(((VentaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VentaDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", clienteId=" + getClienteId() +
            ", clienteNombre='" + getClienteNombre() + "'" +
            "}";
    }
}
