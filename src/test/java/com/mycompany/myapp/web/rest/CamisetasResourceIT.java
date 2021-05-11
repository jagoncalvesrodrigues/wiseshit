package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Camisetas;
import com.mycompany.myapp.repository.CamisetasRepository;
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
 * Integration tests for the {@link CamisetasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CamisetasResourceIT {

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

    private static final String ENTITY_API_URL = "/api/camisetas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CamisetasRepository camisetasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCamisetasMockMvc;

    private Camisetas camisetas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Camisetas createEntity(EntityManager em) {
        Camisetas camisetas = new Camisetas()
            .stock(DEFAULT_STOCK)
            .imagen(DEFAULT_IMAGEN)
            .talla(DEFAULT_TALLA)
            .color(DEFAULT_COLOR)
            .coleccion(DEFAULT_COLECCION);
        return camisetas;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Camisetas createUpdatedEntity(EntityManager em) {
        Camisetas camisetas = new Camisetas()
            .stock(UPDATED_STOCK)
            .imagen(UPDATED_IMAGEN)
            .talla(UPDATED_TALLA)
            .color(UPDATED_COLOR)
            .coleccion(UPDATED_COLECCION);
        return camisetas;
    }

    @BeforeEach
    public void initTest() {
        camisetas = createEntity(em);
    }

    @Test
    @Transactional
    void createCamisetas() throws Exception {
        int databaseSizeBeforeCreate = camisetasRepository.findAll().size();
        // Create the Camisetas
        restCamisetasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(camisetas)))
            .andExpect(status().isCreated());

        // Validate the Camisetas in the database
        List<Camisetas> camisetasList = camisetasRepository.findAll();
        assertThat(camisetasList).hasSize(databaseSizeBeforeCreate + 1);
        Camisetas testCamisetas = camisetasList.get(camisetasList.size() - 1);
        assertThat(testCamisetas.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testCamisetas.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testCamisetas.getTalla()).isEqualTo(DEFAULT_TALLA);
        assertThat(testCamisetas.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testCamisetas.getColeccion()).isEqualTo(DEFAULT_COLECCION);
    }

    @Test
    @Transactional
    void createCamisetasWithExistingId() throws Exception {
        // Create the Camisetas with an existing ID
        camisetas.setId(1L);

        int databaseSizeBeforeCreate = camisetasRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCamisetasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(camisetas)))
            .andExpect(status().isBadRequest());

        // Validate the Camisetas in the database
        List<Camisetas> camisetasList = camisetasRepository.findAll();
        assertThat(camisetasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCamisetas() throws Exception {
        // Initialize the database
        camisetasRepository.saveAndFlush(camisetas);

        // Get all the camisetasList
        restCamisetasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(camisetas.getId().intValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.[*].talla").value(hasItem(DEFAULT_TALLA)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].coleccion").value(hasItem(DEFAULT_COLECCION)));
    }

    @Test
    @Transactional
    void getCamisetas() throws Exception {
        // Initialize the database
        camisetasRepository.saveAndFlush(camisetas);

        // Get the camisetas
        restCamisetasMockMvc
            .perform(get(ENTITY_API_URL_ID, camisetas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(camisetas.getId().intValue()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK))
            .andExpect(jsonPath("$.imagen").value(DEFAULT_IMAGEN))
            .andExpect(jsonPath("$.talla").value(DEFAULT_TALLA))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.coleccion").value(DEFAULT_COLECCION));
    }

    @Test
    @Transactional
    void getNonExistingCamisetas() throws Exception {
        // Get the camisetas
        restCamisetasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCamisetas() throws Exception {
        // Initialize the database
        camisetasRepository.saveAndFlush(camisetas);

        int databaseSizeBeforeUpdate = camisetasRepository.findAll().size();

        // Update the camisetas
        Camisetas updatedCamisetas = camisetasRepository.findById(camisetas.getId()).get();
        // Disconnect from session so that the updates on updatedCamisetas are not directly saved in db
        em.detach(updatedCamisetas);
        updatedCamisetas.stock(UPDATED_STOCK).imagen(UPDATED_IMAGEN).talla(UPDATED_TALLA).color(UPDATED_COLOR).coleccion(UPDATED_COLECCION);

        restCamisetasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCamisetas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCamisetas))
            )
            .andExpect(status().isOk());

        // Validate the Camisetas in the database
        List<Camisetas> camisetasList = camisetasRepository.findAll();
        assertThat(camisetasList).hasSize(databaseSizeBeforeUpdate);
        Camisetas testCamisetas = camisetasList.get(camisetasList.size() - 1);
        assertThat(testCamisetas.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testCamisetas.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testCamisetas.getTalla()).isEqualTo(UPDATED_TALLA);
        assertThat(testCamisetas.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testCamisetas.getColeccion()).isEqualTo(UPDATED_COLECCION);
    }

    @Test
    @Transactional
    void putNonExistingCamisetas() throws Exception {
        int databaseSizeBeforeUpdate = camisetasRepository.findAll().size();
        camisetas.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCamisetasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, camisetas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(camisetas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camisetas in the database
        List<Camisetas> camisetasList = camisetasRepository.findAll();
        assertThat(camisetasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCamisetas() throws Exception {
        int databaseSizeBeforeUpdate = camisetasRepository.findAll().size();
        camisetas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCamisetasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(camisetas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camisetas in the database
        List<Camisetas> camisetasList = camisetasRepository.findAll();
        assertThat(camisetasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCamisetas() throws Exception {
        int databaseSizeBeforeUpdate = camisetasRepository.findAll().size();
        camisetas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCamisetasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(camisetas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Camisetas in the database
        List<Camisetas> camisetasList = camisetasRepository.findAll();
        assertThat(camisetasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCamisetasWithPatch() throws Exception {
        // Initialize the database
        camisetasRepository.saveAndFlush(camisetas);

        int databaseSizeBeforeUpdate = camisetasRepository.findAll().size();

        // Update the camisetas using partial update
        Camisetas partialUpdatedCamisetas = new Camisetas();
        partialUpdatedCamisetas.setId(camisetas.getId());

        partialUpdatedCamisetas.stock(UPDATED_STOCK).imagen(UPDATED_IMAGEN);

        restCamisetasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCamisetas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCamisetas))
            )
            .andExpect(status().isOk());

        // Validate the Camisetas in the database
        List<Camisetas> camisetasList = camisetasRepository.findAll();
        assertThat(camisetasList).hasSize(databaseSizeBeforeUpdate);
        Camisetas testCamisetas = camisetasList.get(camisetasList.size() - 1);
        assertThat(testCamisetas.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testCamisetas.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testCamisetas.getTalla()).isEqualTo(DEFAULT_TALLA);
        assertThat(testCamisetas.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testCamisetas.getColeccion()).isEqualTo(DEFAULT_COLECCION);
    }

    @Test
    @Transactional
    void fullUpdateCamisetasWithPatch() throws Exception {
        // Initialize the database
        camisetasRepository.saveAndFlush(camisetas);

        int databaseSizeBeforeUpdate = camisetasRepository.findAll().size();

        // Update the camisetas using partial update
        Camisetas partialUpdatedCamisetas = new Camisetas();
        partialUpdatedCamisetas.setId(camisetas.getId());

        partialUpdatedCamisetas
            .stock(UPDATED_STOCK)
            .imagen(UPDATED_IMAGEN)
            .talla(UPDATED_TALLA)
            .color(UPDATED_COLOR)
            .coleccion(UPDATED_COLECCION);

        restCamisetasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCamisetas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCamisetas))
            )
            .andExpect(status().isOk());

        // Validate the Camisetas in the database
        List<Camisetas> camisetasList = camisetasRepository.findAll();
        assertThat(camisetasList).hasSize(databaseSizeBeforeUpdate);
        Camisetas testCamisetas = camisetasList.get(camisetasList.size() - 1);
        assertThat(testCamisetas.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testCamisetas.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testCamisetas.getTalla()).isEqualTo(UPDATED_TALLA);
        assertThat(testCamisetas.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testCamisetas.getColeccion()).isEqualTo(UPDATED_COLECCION);
    }

    @Test
    @Transactional
    void patchNonExistingCamisetas() throws Exception {
        int databaseSizeBeforeUpdate = camisetasRepository.findAll().size();
        camisetas.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCamisetasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, camisetas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(camisetas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camisetas in the database
        List<Camisetas> camisetasList = camisetasRepository.findAll();
        assertThat(camisetasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCamisetas() throws Exception {
        int databaseSizeBeforeUpdate = camisetasRepository.findAll().size();
        camisetas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCamisetasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(camisetas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camisetas in the database
        List<Camisetas> camisetasList = camisetasRepository.findAll();
        assertThat(camisetasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCamisetas() throws Exception {
        int databaseSizeBeforeUpdate = camisetasRepository.findAll().size();
        camisetas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCamisetasMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(camisetas))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Camisetas in the database
        List<Camisetas> camisetasList = camisetasRepository.findAll();
        assertThat(camisetasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCamisetas() throws Exception {
        // Initialize the database
        camisetasRepository.saveAndFlush(camisetas);

        int databaseSizeBeforeDelete = camisetasRepository.findAll().size();

        // Delete the camisetas
        restCamisetasMockMvc
            .perform(delete(ENTITY_API_URL_ID, camisetas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Camisetas> camisetasList = camisetasRepository.findAll();
        assertThat(camisetasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
