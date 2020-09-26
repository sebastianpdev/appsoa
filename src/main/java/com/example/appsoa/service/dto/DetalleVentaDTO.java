package com.example.appsoa.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.appsoa.domain.DetalleVenta} entity.
 */
public class DetalleVentaDTO implements Serializable {

    private Long id;

    private Long productoId;

    private String productoNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalleVentaDTO)) {
            return false;
        }

        return id != null && id.equals(((DetalleVentaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
