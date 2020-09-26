package com.example.appsoa.web.rest;

import com.example.appsoa.AppsoaApp;
import com.example.appsoa.domain.DetalleVenta;
import com.example.appsoa.domain.Producto;
import com.example.appsoa.repository.DetalleVentaRepository;
import com.example.appsoa.service.DetalleVentaService;
import com.example.appsoa.service.dto.DetalleVentaDTO;
import com.example.appsoa.service.mapper.DetalleVentaMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DetalleVentaResource} REST controller.
 */
@SpringBootTest(classes = AppsoaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DetalleVentaResourceIT {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private DetalleVentaMapper detalleVentaMapper;

    @Autowired
    private DetalleVentaService detalleVentaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetalleVentaMockMvc;

    private DetalleVenta detalleVenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalleVenta createEntity(EntityManager em) {
        DetalleVenta detalleVenta = new DetalleVenta();
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        detalleVenta.setProducto(producto);
        return detalleVenta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalleVenta createUpdatedEntity(EntityManager em) {
        DetalleVenta detalleVenta = new DetalleVenta();
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        detalleVenta.setProducto(producto);
        return detalleVenta;
    }

    @BeforeEach
    public void initTest() {
        detalleVenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalleVenta() throws Exception {
        int databaseSizeBeforeCreate = detalleVentaRepository.findAll().size();
        // Create the DetalleVenta
        DetalleVentaDTO detalleVentaDTO = detalleVentaMapper.toDto(detalleVenta);
        restDetalleVentaMockMvc.perform(post("/api/detalle-ventas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detalleVentaDTO)))
            .andExpect(status().isCreated());

        // Validate the DetalleVenta in the database
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeCreate + 1);
        DetalleVenta testDetalleVenta = detalleVentaList.get(detalleVentaList.size() - 1);
    }

    @Test
    @Transactional
    public void createDetalleVentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalleVentaRepository.findAll().size();

        // Create the DetalleVenta with an existing ID
        detalleVenta.setId(1L);
        DetalleVentaDTO detalleVentaDTO = detalleVentaMapper.toDto(detalleVenta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalleVentaMockMvc.perform(post("/api/detalle-ventas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detalleVentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleVenta in the database
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDetalleVentas() throws Exception {
        // Initialize the database
        detalleVentaRepository.saveAndFlush(detalleVenta);

        // Get all the detalleVentaList
        restDetalleVentaMockMvc.perform(get("/api/detalle-ventas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleVenta.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDetalleVenta() throws Exception {
        // Initialize the database
        detalleVentaRepository.saveAndFlush(detalleVenta);

        // Get the detalleVenta
        restDetalleVentaMockMvc.perform(get("/api/detalle-ventas/{id}", detalleVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detalleVenta.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDetalleVenta() throws Exception {
        // Get the detalleVenta
        restDetalleVentaMockMvc.perform(get("/api/detalle-ventas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalleVenta() throws Exception {
        // Initialize the database
        detalleVentaRepository.saveAndFlush(detalleVenta);

        int databaseSizeBeforeUpdate = detalleVentaRepository.findAll().size();

        // Update the detalleVenta
        DetalleVenta updatedDetalleVenta = detalleVentaRepository.findById(detalleVenta.getId()).get();
        // Disconnect from session so that the updates on updatedDetalleVenta are not directly saved in db
        em.detach(updatedDetalleVenta);
        DetalleVentaDTO detalleVentaDTO = detalleVentaMapper.toDto(updatedDetalleVenta);

        restDetalleVentaMockMvc.perform(put("/api/detalle-ventas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detalleVentaDTO)))
            .andExpect(status().isOk());

        // Validate the DetalleVenta in the database
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeUpdate);
        DetalleVenta testDetalleVenta = detalleVentaList.get(detalleVentaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalleVenta() throws Exception {
        int databaseSizeBeforeUpdate = detalleVentaRepository.findAll().size();

        // Create the DetalleVenta
        DetalleVentaDTO detalleVentaDTO = detalleVentaMapper.toDto(detalleVenta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalleVentaMockMvc.perform(put("/api/detalle-ventas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detalleVentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleVenta in the database
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetalleVenta() throws Exception {
        // Initialize the database
        detalleVentaRepository.saveAndFlush(detalleVenta);

        int databaseSizeBeforeDelete = detalleVentaRepository.findAll().size();

        // Delete the detalleVenta
        restDetalleVentaMockMvc.perform(delete("/api/detalle-ventas/{id}", detalleVenta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
