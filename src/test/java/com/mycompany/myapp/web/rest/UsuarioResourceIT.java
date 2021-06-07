// package com.mycompany.myapp.web.rest;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.hamcrest.Matchers.hasItem;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import com.mycompany.myapp.IntegrationTest;
// import com.mycompany.myapp.domain.Usuario;
// import com.mycompany.myapp.repository.UsuarioRepository;
// import java.util.List;
// import java.util.Random;
// import java.util.concurrent.atomic.AtomicLong;
// import javax.persistence.EntityManager;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.transaction.annotation.Transactional;

// /**
//  * Integration tests for the {@link UsuarioResource} REST controller.
//  */
// @IntegrationTest
// @AutoConfigureMockMvc
// @WithMockUser
// class UsuarioResourceIT {

//     private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
//     private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

//     private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
//     private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

//     private static final String DEFAULT_CORREO = "AAAAAAAAAA";
//     private static final String UPDATED_CORREO = "BBBBBBBBBB";

//     private static final Integer DEFAULT_NUMERO_TELEFONO = 1;
//     private static final Integer UPDATED_NUMERO_TELEFONO = 2;

//     private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
//     private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

//     private static final Integer DEFAULT_CODIGO_POSTAL = 1;
//     private static final Integer UPDATED_CODIGO_POSTAL = 2;

//     private static final String ENTITY_API_URL = "/api/usuarios";
//     private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

//     private static Random random = new Random();
//     private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

//     @Autowired
//     private UsuarioRepository usuarioRepository;

//     @Autowired
//     private EntityManager em;

//     @Autowired
//     private MockMvc restUsuarioMockMvc;

//     private Usuario usuario;

//     /**
//      * Create an entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static Usuario createEntity(EntityManager em) {
//         Usuario usuario = new Usuario()
//             .nombre(DEFAULT_NOMBRE)
//             .apellido(DEFAULT_APELLIDO)
//             .correo(DEFAULT_CORREO)
//             .numeroTelefono(DEFAULT_NUMERO_TELEFONO)
//             .direccion(DEFAULT_DIRECCION)
//             .codigoPostal(DEFAULT_CODIGO_POSTAL);
//         return usuario;
//     }

//     /**
//      * Create an updated entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static Usuario createUpdatedEntity(EntityManager em) {
//         Usuario usuario = new Usuario()
//             .nombre(UPDATED_NOMBRE)
//             .apellido(UPDATED_APELLIDO)
//             .correo(UPDATED_CORREO)
//             .numeroTelefono(UPDATED_NUMERO_TELEFONO)
//             .direccion(UPDATED_DIRECCION)
//             .codigoPostal(UPDATED_CODIGO_POSTAL);
//         return usuario;
//     }

//     @BeforeEach
//     public void initTest() {
//         usuario = createEntity(em);
//     }

//     @Test
//     @Transactional
//     void createUsuario() throws Exception {
//         int databaseSizeBeforeCreate = usuarioRepository.findAll().size();
//         // Create the Usuario
//         restUsuarioMockMvc
//             .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
//             .andExpect(status().isCreated());

//         // Validate the Usuario in the database
//         List<Usuario> usuarioList = usuarioRepository.findAll();
//         assertThat(usuarioList).hasSize(databaseSizeBeforeCreate + 1);
//         Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
//         assertThat(testUsuario.getNombre()).isEqualTo(DEFAULT_NOMBRE);
//         assertThat(testUsuario.getApellido()).isEqualTo(DEFAULT_APELLIDO);
//         assertThat(testUsuario.getCorreo()).isEqualTo(DEFAULT_CORREO);
//         assertThat(testUsuario.getNumeroTelefono()).isEqualTo(DEFAULT_NUMERO_TELEFONO);
//         assertThat(testUsuario.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
//         assertThat(testUsuario.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
//     }

//     @Test
//     @Transactional
//     void createUsuarioWithExistingId() throws Exception {
//         // Create the Usuario with an existing ID
//         usuario.setId(1L);

//         int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

//         // An entity with an existing ID cannot be created, so this API call must fail
//         restUsuarioMockMvc
//             .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
//             .andExpect(status().isBadRequest());

//         // Validate the Usuario in the database
//         List<Usuario> usuarioList = usuarioRepository.findAll();
//         assertThat(usuarioList).hasSize(databaseSizeBeforeCreate);
//     }

//     @Test
//     @Transactional
//     void getAllUsuarios() throws Exception {
//         // Initialize the database
//         usuarioRepository.saveAndFlush(usuario);

//         // Get all the usuarioList
//         restUsuarioMockMvc
//             .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId().intValue())))
//             .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
//             .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
//             .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
//             .andExpect(jsonPath("$.[*].numeroTelefono").value(hasItem(DEFAULT_NUMERO_TELEFONO)))
//             .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
//             .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)));
//     }

//     @Test
//     @Transactional
//     void getUsuario() throws Exception {
//         // Initialize the database
//         usuarioRepository.saveAndFlush(usuario);

//         // Get the usuario
//         restUsuarioMockMvc
//             .perform(get(ENTITY_API_URL_ID, usuario.getId()))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.id").value(usuario.getId().intValue()))
//             .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
//             .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO))
//             .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
//             .andExpect(jsonPath("$.numeroTelefono").value(DEFAULT_NUMERO_TELEFONO))
//             .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
//             .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL));
//     }

//     @Test
//     @Transactional
//     void getNonExistingUsuario() throws Exception {
//         // Get the usuario
//         restUsuarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//     }

//     @Test
//     @Transactional
//     void putNewUsuario() throws Exception {
//         // Initialize the database
//         usuarioRepository.saveAndFlush(usuario);

//         int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

//         // Update the usuario
//         Usuario updatedUsuario = usuarioRepository.findById(usuario.getId()).get();
//         // Disconnect from session so that the updates on updatedUsuario are not directly saved in db
//         em.detach(updatedUsuario);
//         updatedUsuario
//             .nombre(UPDATED_NOMBRE)
//             .apellido(UPDATED_APELLIDO)
//             .correo(UPDATED_CORREO)
//             .numeroTelefono(UPDATED_NUMERO_TELEFONO)
//             .direccion(UPDATED_DIRECCION)
//             .codigoPostal(UPDATED_CODIGO_POSTAL);

//         restUsuarioMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, updatedUsuario.getId())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(updatedUsuario))
//             )
//             .andExpect(status().isOk());

//         // Validate the Usuario in the database
//         List<Usuario> usuarioList = usuarioRepository.findAll();
//         assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
//         Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
//         assertThat(testUsuario.getNombre()).isEqualTo(UPDATED_NOMBRE);
//         assertThat(testUsuario.getApellido()).isEqualTo(UPDATED_APELLIDO);
//         assertThat(testUsuario.getCorreo()).isEqualTo(UPDATED_CORREO);
//         assertThat(testUsuario.getNumeroTelefono()).isEqualTo(UPDATED_NUMERO_TELEFONO);
//         assertThat(testUsuario.getDireccion()).isEqualTo(UPDATED_DIRECCION);
//         assertThat(testUsuario.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
//     }

//     @Test
//     @Transactional
//     void putNonExistingUsuario() throws Exception {
//         int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
//         usuario.setId(count.incrementAndGet());

//         // If the entity doesn't have an ID, it will throw BadRequestAlertException
//         restUsuarioMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, usuario.getId())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(usuario))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the Usuario in the database
//         List<Usuario> usuarioList = usuarioRepository.findAll();
//         assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void putWithIdMismatchUsuario() throws Exception {
//         int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
//         usuario.setId(count.incrementAndGet());

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restUsuarioMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, count.incrementAndGet())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(usuario))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the Usuario in the database
//         List<Usuario> usuarioList = usuarioRepository.findAll();
//         assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void putWithMissingIdPathParamUsuario() throws Exception {
//         int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
//         usuario.setId(count.incrementAndGet());

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restUsuarioMockMvc
//             .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
//             .andExpect(status().isMethodNotAllowed());

//         // Validate the Usuario in the database
//         List<Usuario> usuarioList = usuarioRepository.findAll();
//         assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void partialUpdateUsuarioWithPatch() throws Exception {
//         // Initialize the database
//         usuarioRepository.saveAndFlush(usuario);

//         int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

//         // Update the usuario using partial update
//         Usuario partialUpdatedUsuario = new Usuario();
//         partialUpdatedUsuario.setId(usuario.getId());

//         partialUpdatedUsuario
//             .nombre(UPDATED_NOMBRE)
//             .correo(UPDATED_CORREO)
//             .numeroTelefono(UPDATED_NUMERO_TELEFONO)
//             .direccion(UPDATED_DIRECCION)
//             .codigoPostal(UPDATED_CODIGO_POSTAL);

//         restUsuarioMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, partialUpdatedUsuario.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuario))
//             )
//             .andExpect(status().isOk());

//         // Validate the Usuario in the database
//         List<Usuario> usuarioList = usuarioRepository.findAll();
//         assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
//         Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
//         assertThat(testUsuario.getNombre()).isEqualTo(UPDATED_NOMBRE);
//         assertThat(testUsuario.getApellido()).isEqualTo(DEFAULT_APELLIDO);
//         assertThat(testUsuario.getCorreo()).isEqualTo(UPDATED_CORREO);
//         assertThat(testUsuario.getNumeroTelefono()).isEqualTo(UPDATED_NUMERO_TELEFONO);
//         assertThat(testUsuario.getDireccion()).isEqualTo(UPDATED_DIRECCION);
//         assertThat(testUsuario.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
//     }

//     @Test
//     @Transactional
//     void fullUpdateUsuarioWithPatch() throws Exception {
//         // Initialize the database
//         usuarioRepository.saveAndFlush(usuario);

//         int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

//         // Update the usuario using partial update
//         Usuario partialUpdatedUsuario = new Usuario();
//         partialUpdatedUsuario.setId(usuario.getId());

//         partialUpdatedUsuario
//             .nombre(UPDATED_NOMBRE)
//             .apellido(UPDATED_APELLIDO)
//             .correo(UPDATED_CORREO)
//             .numeroTelefono(UPDATED_NUMERO_TELEFONO)
//             .direccion(UPDATED_DIRECCION)
//             .codigoPostal(UPDATED_CODIGO_POSTAL);

//         restUsuarioMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, partialUpdatedUsuario.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuario))
//             )
//             .andExpect(status().isOk());

//         // Validate the Usuario in the database
//         List<Usuario> usuarioList = usuarioRepository.findAll();
//         assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
//         Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
//         assertThat(testUsuario.getNombre()).isEqualTo(UPDATED_NOMBRE);
//         assertThat(testUsuario.getApellido()).isEqualTo(UPDATED_APELLIDO);
//         assertThat(testUsuario.getCorreo()).isEqualTo(UPDATED_CORREO);
//         assertThat(testUsuario.getNumeroTelefono()).isEqualTo(UPDATED_NUMERO_TELEFONO);
//         assertThat(testUsuario.getDireccion()).isEqualTo(UPDATED_DIRECCION);
//         assertThat(testUsuario.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
//     }

//     @Test
//     @Transactional
//     void patchNonExistingUsuario() throws Exception {
//         int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
//         usuario.setId(count.incrementAndGet());

//         // If the entity doesn't have an ID, it will throw BadRequestAlertException
//         restUsuarioMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, usuario.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(usuario))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the Usuario in the database
//         List<Usuario> usuarioList = usuarioRepository.findAll();
//         assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void patchWithIdMismatchUsuario() throws Exception {
//         int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
//         usuario.setId(count.incrementAndGet());

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restUsuarioMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(usuario))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the Usuario in the database
//         List<Usuario> usuarioList = usuarioRepository.findAll();
//         assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void patchWithMissingIdPathParamUsuario() throws Exception {
//         int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
//         usuario.setId(count.incrementAndGet());

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restUsuarioMockMvc
//             .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(usuario)))
//             .andExpect(status().isMethodNotAllowed());

//         // Validate the Usuario in the database
//         List<Usuario> usuarioList = usuarioRepository.findAll();
//         assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void deleteUsuario() throws Exception {
//         // Initialize the database
//         usuarioRepository.saveAndFlush(usuario);

//         int databaseSizeBeforeDelete = usuarioRepository.findAll().size();

//         // Delete the usuario
//         restUsuarioMockMvc
//             .perform(delete(ENTITY_API_URL_ID, usuario.getId()).accept(MediaType.APPLICATION_JSON))
//             .andExpect(status().isNoContent());

//         // Validate the database contains one less item
//         List<Usuario> usuarioList = usuarioRepository.findAll();
//         assertThat(usuarioList).hasSize(databaseSizeBeforeDelete - 1);
//     }
// }
