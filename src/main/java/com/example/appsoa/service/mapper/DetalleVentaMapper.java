package com.example.appsoa.service.mapper;


import com.example.appsoa.domain.*;
import com.example.appsoa.service.dto.DetalleVentaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetalleVenta} and its DTO {@link DetalleVentaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface DetalleVentaMapper extends EntityMapper<DetalleVentaDTO, DetalleVenta> {

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombre", target = "productoNombre")
    DetalleVentaDTO toDto(DetalleVenta detalleVenta);

    @Mapping(source = "productoId", target = "producto")
    DetalleVenta toEntity(DetalleVentaDTO detalleVentaDTO);

    default DetalleVenta fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setId(id);
        return detalleVenta;
    }
}
