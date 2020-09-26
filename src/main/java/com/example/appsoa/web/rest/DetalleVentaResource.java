package com.example.appsoa.web.rest;

import com.example.appsoa.security.AuthoritiesConstants;
import com.example.appsoa.service.DetalleVentaService;
import com.example.appsoa.web.rest.errors.BadRequestAlertException;
import com.example.appsoa.service.dto.DetalleVentaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rx.Single;
import rx.schedulers.Schedulers;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.example.appsoa.domain.DetalleVenta}.
 */
@RestController
@RequestMapping("/api")
public class DetalleVentaResource {

    private final Logger log = LoggerFactory.getLogger(DetalleVentaResource.class);

    private static final String ENTITY_NAME = "detalleVenta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetalleVentaService detalleVentaService;

    public DetalleVentaResource(DetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    /**
     * {@code POST  /detalle-ventas} : Create a new detalleVenta.
     *
     * @param detalleVentaDTO the detalleVentaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detalleVentaDTO, or with status {@code 400 (Bad Request)} if the detalleVenta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detalle-ventas")
    public ResponseEntity<DetalleVentaDTO> createDetalleVenta(@Valid @RequestBody DetalleVentaDTO detalleVentaDTO) throws URISyntaxException {
        log.debug("REST request to save DetalleVenta : {}", detalleVentaDTO);
        if (detalleVentaDTO.getId() != null) {
            throw new BadRequestAlertException("A new detalleVenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetalleVentaDTO result = detalleVentaService.save(detalleVentaDTO);
        return ResponseEntity.created(new URI("/api/detalle-ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalle-ventas} : Updates an existing detalleVenta.
     *
     * @param detalleVentaDTO the detalleVentaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalleVentaDTO,
     * or with status {@code 400 (Bad Request)} if the detalleVentaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detalleVentaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detalle-ventas")
    public ResponseEntity<DetalleVentaDTO> updateDetalleVenta(@Valid @RequestBody DetalleVentaDTO detalleVentaDTO) throws URISyntaxException {
        log.debug("REST request to update DetalleVenta : {}", detalleVentaDTO);
        if (detalleVentaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetalleVentaDTO result = detalleVentaService.save(detalleVentaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, detalleVentaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /detalle-ventas} : get all the detalleVentas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detalleVentas in body.
     */
    @GetMapping("/detalle-ventas")
    public ResponseEntity<List<DetalleVentaDTO>> getAllDetalleVentas(Pageable pageable) {
        log.debug("REST request to get a page of DetalleVentas");
        Page<DetalleVentaDTO> page = detalleVentaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detalle-ventas/:id} : get the "id" detalleVenta.
     *
     * @param id the id of the detalleVentaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detalleVentaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detalle-ventas/{id}")
    public ResponseEntity<DetalleVentaDTO> getDetalleVenta(@PathVariable Long id) {
        log.debug("REST request to get DetalleVenta : {}", id);
        Optional<DetalleVentaDTO> detalleVentaDTO = detalleVentaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalleVentaDTO);
    }

    /**
     * {@code DELETE  /detalle-ventas/:id} : delete the "id" detalleVenta.
     *
     * @param id the id of the detalleVentaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detalle-ventas/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteDetalleVenta(@PathVariable Long id) {
        log.debug("REST request to delete DetalleVenta : {}", id);
        detalleVentaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/detalle-ventas/venta/{ventaId}")
    public Single<ResponseEntity<List<DetalleVentaDTO>>> getAllByVenta(@PathVariable Long ventaId) {
        return detalleVentaService.findByVentas(ventaId).subscribeOn(Schedulers.io())
            .map(data -> ResponseEntity.ok().body(detalleVentaService.mapListToDTO(data)));
    }

    @GetMapping("/detalle-ventas/cliente/{clienteId}")
    public Single<ResponseEntity<List<DetalleVentaDTO>>> getAllByCliente(@PathVariable Long clienteId) {
        return detalleVentaService.findByCliente(clienteId).subscribeOn(Schedulers.io())
            .map(data -> ResponseEntity.ok().body(detalleVentaService.mapListToDTO(data)));
    }
}
