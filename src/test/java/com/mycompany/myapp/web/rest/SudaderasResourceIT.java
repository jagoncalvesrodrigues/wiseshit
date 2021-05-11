package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Sudaderas;
import com.mycompany.myapp.repository.SudaderasRepository;
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
 * Integration tests for the {@link SudaderasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SudaderasResourceIT {

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

    private static final String ENTITY_API_URL = "/api/sudaderas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SudaderasRepository sudaderasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSudaderasMockMvc;

    private Sudaderas sudaderas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sudaderas createEntity(EntityManager em) {
        Sudaderas sudaderas = new Sudaderas()
            .stock(DEFAULT_STOCK)
            .imagen(DEFAULT_IMAGEN)
            .talla(DEFAULT_TALLA)
            .color(DEFAULT_COLOR)
            .coleccion(DEFAULT_COLECCION);
        return sudaderas;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sudaderas createUpdatedEntity(EntityManager em) {
        Sudaderas sudaderas = new Sudaderas()
            .stock(UPDATED_STOCK)
            .imagen(UPDATED_IMAGEN)
            .talla(UPDATED_TALLA)
            .color(UPDATED_COLOR)
            .coleccion(UPDATED_COLECCION);
        return sudaderas;
    }

    @BeforeEach
    public void initTest() {
        sudaderas = createEntity(em);
    }

    @Test
    @Transactional
    void createSudaderas() throws Exception {
        int databaseSizeBeforeCreate = sudaderasRepository.findAll().size();
        // Create the Sudaderas
        restSudaderasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sudaderas)))
            .andExpect(status().isCreated());

        // Validate the Sudaderas in the database
        List<Sudaderas> sudaderasList = sudaderasRepository.findAll();
        assertThat(sudaderasList).hasSize(databaseSizeBeforeCreate + 1);
        Sudaderas testSudaderas = sudaderasList.get(sudaderasList.size() - 1);
        assertThat(testSudaderas.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testSudaderas.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testSudaderas.getTalla()).isEqualTo(DEFAULT_TALLA);
        assertThat(testSudaderas.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testSudaderas.getColeccion()).isEqualTo(DEFAULT_COLECCION);
    }

    @Test
    @Transactional
    void createSudaderasWithExistingId() throws Exception {
        // Create the Sudaderas with an existing ID
        sudaderas.setId(1L);

        int databaseSizeBeforeCreate = sudaderasRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSudaderasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sudaderas)))
            .andExpect(status().isBadRequest());

        // Validate the Sudaderas in the database
        List<Sudaderas> sudaderasList = sudaderasRepository.findAll();
        assertThat(sudaderasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSudaderas() throws Exception {
        // Initialize the database
        sudaderasRepository.saveAndFlush(sudaderas);

        // Get all the sudaderasList
        restSudaderasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sudaderas.getId().intValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.[*].talla").value(hasItem(DEFAULT_TALLA)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].coleccion").value(hasItem(DEFAULT_COLECCION)));
    }

    @Test
    @Transactional
    void getSudaderas() throws Exception {
        // Initialize the database
        sudaderasRepository.saveAndFlush(sudaderas);

        // Get the sudaderas
        restSudaderasMockMvc
            .perform(get(ENTITY_API_URL_ID, sudaderas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sudaderas.getId().intValue()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK))
            .andExpect(jsonPath("$.imagen").value(DEFAULT_IMAGEN))
            .andExpect(jsonPath("$.talla").value(DEFAULT_TALLA))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.coleccion").value(DEFAULT_COLECCION));
    }

    @Test
    @Transactional
    void getNonExistingSudaderas() throws Exception {
        // Get the sudaderas
        restSudaderasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSudaderas() throws Exception {
        // Initialize the database
        sudaderasRepository.saveAndFlush(sudaderas);

        int databaseSizeBeforeUpdate = sudaderasRepository.findAll().size();

        // Update the sudaderas
        Sudaderas updatedSudaderas = sudaderasRepository.findById(sudaderas.getId()).get();
        // Disconnect from session so that the updates on updatedSudaderas are not directly saved in db
        em.detach(updatedSudaderas);
        updatedSudaderas.stock(UPDATED_STOCK).imagen(UPDATED_IMAGEN).talla(UPDATED_TALLA).color(UPDATED_COLOR).coleccion(UPDATED_COLECCION);

        restSudaderasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSudaderas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSudaderas))
            )
            .andExpect(status().isOk());

        // Validate the Sudaderas in the database
        List<Sudaderas> sudaderasList = sudaderasRepository.findAll();
        assertThat(sudaderasList).hasSize(databaseSizeBeforeUpdate);
        Sudaderas testSudaderas = sudaderasList.get(sudaderasList.size() - 1);
        assertThat(testSudaderas.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testSudaderas.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testSudaderas.getTalla()).isEqualTo(UPDATED_TALLA);
        assertThat(testSudaderas.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testSudaderas.getColeccion()).isEqualTo(UPDATED_COLECCION);
    }

    @Test
    @Transactional
    void putNonExistingSudaderas() throws Exception {
        int databaseSizeBeforeUpdate = sudaderasRepository.findAll().size();
        sudaderas.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSudaderasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sudaderas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sudaderas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sudaderas in the database
        List<Sudaderas> sudaderasList = sudaderasRepository.findAll();
        assertThat(sudaderasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSudaderas() throws Exception {
        int databaseSizeBeforeUpdate = sudaderasRepository.findAll().size();
        sudaderas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSudaderasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sudaderas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sudaderas in the database
        List<Sudaderas> sudaderasList = sudaderasRepository.findAll();
        assertThat(sudaderasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSudaderas() throws Exception {
        int databaseSizeBeforeUpdate = sudaderasRepository.findAll().size();
        sudaderas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSudaderasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sudaderas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sudaderas in the database
        List<Sudaderas> sudaderasList = sudaderasRepository.findAll();
        assertThat(sudaderasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSudaderasWithPatch() throws Exception {
        // Initialize the database
        sudaderasRepository.saveAndFlush(sudaderas);

        int databaseSizeBeforeUpdate = sudaderasRepository.findAll().size();

        // Update the sudaderas using partial update
        Sudaderas partialUpdatedSudaderas = new Sudaderas();
        partialUpdatedSudaderas.setId(sudaderas.getId());

        restSudaderasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSudaderas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSudaderas))
            )
            .andExpect(status().isOk());

        // Validate the Sudaderas in the database
        List<Sudaderas> sudaderasList = sudaderasRepository.findAll();
        assertThat(sudaderasList).hasSize(databaseSizeBeforeUpdate);
        Sudaderas testSudaderas = sudaderasList.get(sudaderasList.size() - 1);
        assertThat(testSudaderas.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testSudaderas.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testSudaderas.getTalla()).isEqualTo(DEFAULT_TALLA);
        assertThat(testSudaderas.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testSudaderas.getColeccion()).isEqualTo(DEFAULT_COLECCION);
    }

    @Test
    @Transactional
    void fullUpdateSudaderasWithPatch() throws Exception {
        // Initialize the database
        sudaderasRepository.saveAndFlush(sudaderas);

        int databaseSizeBeforeUpdate = sudaderasRepository.findAll().size();

        // Update the sudaderas using partial update
        Sudaderas partialUpdatedSudaderas = new Sudaderas();
        partialUpdatedSudaderas.setId(sudaderas.getId());

        partialUpdatedSudaderas
            .stock(UPDATED_STOCK)
            .imagen(UPDATED_IMAGEN)
            .talla(UPDATED_TALLA)
            .color(UPDATED_COLOR)
            .coleccion(UPDATED_COLECCION);

        restSudaderasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSudaderas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSudaderas))
            )
            .andExpect(status().isOk());

        // Validate the Sudaderas in the database
        List<Sudaderas> sudaderasList = sudaderasRepository.findAll();
        assertThat(sudaderasList).hasSize(databaseSizeBeforeUpdate);
        Sudaderas testSudaderas = sudaderasList.get(sudaderasList.size() - 1);
        assertThat(testSudaderas.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testSudaderas.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testSudaderas.getTalla()).isEqualTo(UPDATED_TALLA);
        assertThat(testSudaderas.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testSudaderas.getColeccion()).isEqualTo(UPDATED_COLECCION);
    }

    @Test
    @Transactional
    void patchNonExistingSudaderas() throws Exception {
        int databaseSizeBeforeUpdate = sudaderasRepository.findAll().size();
        sudaderas.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSudaderasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sudaderas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sudaderas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sudaderas in the database
        List<Sudaderas> sudaderasList = sudaderasRepository.findAll();
        assertThat(sudaderasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSudaderas() throws Exception {
        int databaseSizeBeforeUpdate = sudaderasRepository.findAll().size();
        sudaderas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSudaderasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sudaderas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sudaderas in the database
        List<Sudaderas> sudaderasList = sudaderasRepository.findAll();
        assertThat(sudaderasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSudaderas() throws Exception {
        int databaseSizeBeforeUpdate = sudaderasRepository.findAll().size();
        sudaderas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSudaderasMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sudaderas))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sudaderas in the database
        List<Sudaderas> sudaderasList = sudaderasRepository.findAll();
        assertThat(sudaderasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSudaderas() throws Exception {
        // Initialize the database
        sudaderasRepository.saveAndFlush(sudaderas);

        int databaseSizeBeforeDelete = sudaderasRepository.findAll().size();

        // Delete the sudaderas
        restSudaderasMockMvc
            .perform(delete(ENTITY_API_URL_ID, sudaderas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sudaderas> sudaderasList = sudaderasRepository.findAll();
        assertThat(sudaderasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
