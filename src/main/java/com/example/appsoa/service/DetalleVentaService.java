package com.example.appsoa.service;

import com.example.appsoa.domain.DetalleVenta;
import com.example.appsoa.domain.Venta;
import com.example.appsoa.repository.DetalleVentaRepository;
import com.example.appsoa.service.dto.DetalleVentaDTO;
import com.example.appsoa.service.mapper.DetalleVentaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.Single;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DetalleVenta}.
 */
@Service
@Transactional
public class DetalleVentaService {

    private final Logger log = LoggerFactory.getLogger(DetalleVentaService.class);

    private final DetalleVentaRepository detalleVentaRepository;

    private final DetalleVentaMapper detalleVentaMapper;

    private final VentaService ventaService;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository, DetalleVentaMapper detalleVentaMapper, VentaService ventaService) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.detalleVentaMapper = detalleVentaMapper;
        this.ventaService = ventaService;
    }

    /**
     * Save a detalleVenta.
     *
     * @param detalleVentaDTO the entity to save.
     * @return the persisted entity.
     */
    public DetalleVentaDTO save(DetalleVentaDTO detalleVentaDTO) {
        log.debug("Request to save DetalleVenta : {}", detalleVentaDTO);
        DetalleVenta detalleVenta = detalleVentaMapper.toEntity(detalleVentaDTO);
        detalleVenta = detalleVentaRepository.save(detalleVenta);
        return detalleVentaMapper.toDto(detalleVenta);
    }

    /**
     * Get all the detalleVentas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalleVentaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetalleVentas");
        return detalleVentaRepository.findAll(pageable)
            .map(detalleVentaMapper::toDto);
    }


    /**
     * Get one detalleVenta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DetalleVentaDTO> findOne(Long id) {
        log.debug("Request to get DetalleVenta : {}", id);
        return detalleVentaRepository.findById(id)
            .map(detalleVentaMapper::toDto);
    }

    /**
     * Delete the detalleVenta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DetalleVenta : {}", id);
        detalleVentaRepository.deleteById(id);
    }

    public Single<List<DetalleVenta>> findByVentas(Long ventaId) {
        return Single.create(byVentasSubscriber -> {
            List<DetalleVenta> detalleVentas = detalleVentaRepository.findAllByVenta(new Venta().id(ventaId));
            byVentasSubscriber.onSuccess(detalleVentas);
        });
    }

    public Single<List<DetalleVenta>> findByCliente(Long clienteId) {
        return Single.create(byClienteSubscriber -> {
            List<DetalleVenta> detalleVentas = this.getByCliente(clienteId);
            byClienteSubscriber.onSuccess(detalleVentas);
        });
    }

    public List<DetalleVenta> getByCliente(Long clienteId) {
        return detalleVentaRepository.findAllByVentaIn(ventaService.getVentasByCliente(clienteId));
    }


    public List<DetalleVentaDTO> mapListToDTO(List<DetalleVenta> data) {
        return data.stream().map(detalleVentaMapper::toDto).collect(Collectors.toList());
    }
}
