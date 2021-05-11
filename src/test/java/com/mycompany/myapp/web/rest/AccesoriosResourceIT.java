package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Accesorios;
import com.mycompany.myapp.repository.AccesoriosRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AccesoriosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccesoriosResourceIT {

    private static final Integer DEFAULT_STOCK = 1;
    private static final Integer UPDATED_STOCK = 2;

    private static final String DEFAULT_IMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEN = "BBBBBBBBBB";

    private static final String DEFAULT_TALLA = "AAAAAAAAAA";
    private static final String UPDATED_TALLA = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_COLECCION = 1;
    private static final Integer UPDATED_COLECCION = 2;

    private static final String ENTITY_API_URL = "/api/accesorios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccesoriosRepository accesoriosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccesoriosMockMvc;

    private Accesorios accesorios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accesorios createEntity(EntityManager em) {
        Accesorios accesorios = new Accesorios()
            .stock(DEFAULT_STOCK)
            .imagen(DEFAULT_IMAGEN)
            .talla(DEFAULT_TALLA)
            .color(DEFAULT_COLOR)
            .coleccion(DEFAULT_COLECCION);
        return accesorios;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accesorios createUpdatedEntity(EntityManager em) {
        Accesorios accesorios = new Accesorios()
            .stock(UPDATED_STOCK)
            .imagen(UPDATED_IMAGEN)
            .talla(UPDATED_TALLA)
            .color(UPDATED_COLOR)
            .coleccion(UPDATED_COLECCION);
        return accesorios;
    }

    @BeforeEach
    public void initTest() {
        accesorios = createEntity(em);
    }

    @Test
    @Transactional
    void createAccesorios() throws Exception {
        int databaseSizeBeforeCreate = accesoriosRepository.findAll().size();
        // Create the Accesorios
        restAccesoriosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accesorios)))
            .andExpect(status().isCreated());

        // Validate the Accesorios in the database
        List<Accesorios> accesoriosList = accesoriosRepository.findAll();
        assertThat(accesoriosList).hasSize(databaseSizeBeforeCreate + 1);
        Accesorios testAccesorios = accesoriosList.get(accesoriosList.size() - 1);
        assertThat(testAccesorios.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testAccesorios.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testAccesorios.getTalla()).isEqualTo(DEFAULT_TALLA);
        assertThat(testAccesorios.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testAccesorios.getColeccion()).isEqualTo(DEFAULT_COLECCION);
    }

    @Test
    @Transactional
    void createAccesoriosWithExistingId() throws Exception {
        // Create the Accesorios with an existing ID
        accesorios.setId(1L);

        int databaseSizeBeforeCreate = accesoriosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccesoriosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accesorios)))
            .andExpect(status().isBadRequest());

        // Validate the Accesorios in the database
        List<Accesorios> accesoriosList = accesoriosRepository.findAll();
        assertThat(accesoriosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAccesorios() throws Exception {
        // Initialize the database
        accesoriosRepository.saveAndFlush(accesorios);

        // Get all the accesoriosList
        restAccesoriosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accesorios.getId().intValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.[*].talla").value(hasItem(DEFAULT_TALLA)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].coleccion").value(hasItem(DEFAULT_COLECCION)));
    }

    @Test
    @Transactional
    void getAccesorios() throws Exception {
        // Initialize the database
        accesoriosRepository.saveAndFlush(accesorios);

        // Get the accesorios
        restAccesoriosMockMvc
            .perform(get(ENTITY_API_URL_ID, accesorios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accesorios.getId().intValue()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK))
            .andExpect(jsonPath("$.imagen").value(DEFAULT_IMAGEN))
            .andExpect(jsonPath("$.talla").value(DEFAULT_TALLA))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.coleccion").value(DEFAULT_COLECCION));
    }

    @Test
    @Transactional
    void getNonExistingAccesorios() throws Exception {
        // Get the accesorios
        restAccesoriosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAccesorios() throws Exception {
        // Initialize the database
        accesoriosRepository.saveAndFlush(accesorios);

        int databaseSizeBeforeUpdate = accesoriosRepository.findAll().size();

        // Update the accesorios
        Accesorios updatedAccesorios = accesoriosRepository.findById(accesorios.getId()).get();
        // Disconnect from session so that the updates on updatedAccesorios are not directly saved in db
        em.detach(updatedAccesorios);
        updatedAccesorios
            .stock(UPDATED_STOCK)
            .imagen(UPDATED_IMAGEN)
            .talla(UPDATED_TALLA)
            .color(UPDATED_COLOR)
            .coleccion(UPDATED_COLECCION);

        restAccesoriosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAccesorios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAccesorios))
            )
            .andExpect(status().isOk());

        // Validate the Accesorios in the database
        List<Accesorios> accesoriosList = accesoriosRepository.findAll();
        assertThat(accesoriosList).hasSize(databaseSizeBeforeUpdate);
        Accesorios testAccesorios = accesoriosList.get(accesoriosList.size() - 1);
        assertThat(testAccesorios.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testAccesorios.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testAccesorios.getTalla()).isEqualTo(UPDATED_TALLA);
        assertThat(testAccesorios.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testAccesorios.getColeccion()).isEqualTo(UPDATED_COLECCION);
    }

    @Test
    @Transactional
    void putNonExistingAccesorios() throws Exception {
        int databaseSizeBeforeUpdate = accesoriosRepository.findAll().size();
        accesorios.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccesoriosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accesorios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accesorios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accesorios in the database
        List<Accesorios> accesoriosList = accesoriosRepository.findAll();
        assertThat(accesoriosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccesorios() throws Exception {
        int databaseSizeBeforeUpdate = accesoriosRepository.findAll().size();
        accesorios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccesoriosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accesorios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accesorios in the database
        List<Accesorios> accesoriosList = accesoriosRepository.findAll();
        assertThat(accesoriosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccesorios() throws Exception {
        int databaseSizeBeforeUpdate = accesoriosRepository.findAll().size();
        accesorios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccesoriosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accesorios)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accesorios in the database
        List<Accesorios> accesoriosList = accesoriosRepository.findAll();
        assertThat(accesoriosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccesoriosWithPatch() throws Exception {
        // Initialize the database
        accesoriosRepository.saveAndFlush(accesorios);

        int databaseSizeBeforeUpdate = accesoriosRepository.findAll().size();

        // Update the accesorios using partial update
        Accesorios partialUpdatedAccesorios = new Accesorios();
        partialUpdatedAccesorios.setId(accesorios.getId());

        partialUpdatedAccesorios.stock(UPDATED_STOCK).talla(UPDATED_TALLA).color(UPDATED_COLOR).coleccion(UPDATED_COLECCION);

        restAccesoriosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccesorios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccesorios))
            )
            .andExpect(status().isOk());

        // Validate the Accesorios in the database
        List<Accesorios> accesoriosList = accesoriosRepository.findAll();
        assertThat(accesoriosList).hasSize(databaseSizeBeforeUpdate);
        Accesorios testAccesorios = accesoriosList.get(accesoriosList.size() - 1);
        assertThat(testAccesorios.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testAccesorios.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testAccesorios.getTalla()).isEqualTo(UPDATED_TALLA);
        assertThat(testAccesorios.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testAccesorios.getColeccion()).isEqualTo(UPDATED_COLECCION);
    }

    @Test
    @Transactional
    void fullUpdateAccesoriosWithPatch() throws Exception {
        // Initialize the database
        accesoriosRepository.saveAndFlush(accesorios);

        int databaseSizeBeforeUpdate = accesoriosRepository.findAll().size();

        // Update the accesorios using partial update
        Accesorios partialUpdatedAccesorios = new Accesorios();
        partialUpdatedAccesorios.setId(accesorios.getId());

        partialUpdatedAccesorios
            .stock(UPDATED_STOCK)
            .imagen(UPDATED_IMAGEN)
            .talla(UPDATED_TALLA)
            .color(UPDATED_COLOR)
            .coleccion(UPDATED_COLECCION);

        restAccesoriosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccesorios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccesorios))
            )
            .andExpect(status().isOk());

        // Validate the Accesorios in the database
        List<Accesorios> accesoriosList = accesoriosRepository.findAll();
        assertThat(accesoriosList).hasSize(databaseSizeBeforeUpdate);
        Accesorios testAccesorios = accesoriosList.get(accesoriosList.size() - 1);
        assertThat(testAccesorios.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testAccesorios.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testAccesorios.getTalla()).isEqualTo(UPDATED_TALLA);
        assertThat(testAccesorios.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testAccesorios.getColeccion()).isEqualTo(UPDATED_COLECCION);
    }

    @Test
    @Transactional
    void patchNonExistingAccesorios() throws Exception {
        int databaseSizeBeforeUpdate = accesoriosRepository.findAll().size();
        accesorios.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccesoriosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accesorios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accesorios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accesorios in the database
        List<Accesorios> accesoriosList = accesoriosRepository.findAll();
        assertThat(accesoriosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccesorios() throws Exception {
        int databaseSizeBeforeUpdate = accesoriosRepository.findAll().size();
        accesorios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccesoriosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accesorios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accesorios in the database
        List<Accesorios> accesoriosList = accesoriosRepository.findAll();
        assertThat(accesoriosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccesorios() throws Exception {
        int databaseSizeBeforeUpdate = accesoriosRepository.findAll().size();
        accesorios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccesoriosMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(accesorios))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accesorios in the database
        List<Accesorios> accesoriosList = accesoriosRepository.findAll();
        assertThat(accesoriosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccesorios() throws Exception {
        // Initialize the database
        accesoriosRepository.saveAndFlush(accesorios);

        int databaseSizeBeforeDelete = accesoriosRepository.findAll().size();

        // Delete the accesorios
        restAccesoriosMockMvc
            .perform(delete(ENTITY_API_URL_ID, accesorios.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accesorios> accesoriosList = accesoriosRepository.findAll();
        assertThat(accesoriosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
