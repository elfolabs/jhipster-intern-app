package br.com.elfotec.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.elfotec.IntegrationTest;
import br.com.elfotec.domain.PessoaRedeSocial;
import br.com.elfotec.repository.PessoaRedeSocialRepository;
import br.com.elfotec.service.dto.PessoaRedeSocialDTO;
import br.com.elfotec.service.mapper.PessoaRedeSocialMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link PessoaRedeSocialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PessoaRedeSocialResourceIT {

    private static final String DEFAULT_NOME_REDE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_REDE = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_EXCLUSAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_EXCLUSAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_DIVULGAR_NO_APLICATIVO = false;
    private static final Boolean UPDATED_DIVULGAR_NO_APLICATIVO = true;

    private static final String ENTITY_API_URL = "/api/pessoa-rede-socials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PessoaRedeSocialRepository pessoaRedeSocialRepository;

    @Autowired
    private PessoaRedeSocialMapper pessoaRedeSocialMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPessoaRedeSocialMockMvc;

    private PessoaRedeSocial pessoaRedeSocial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PessoaRedeSocial createEntity(EntityManager em) {
        PessoaRedeSocial pessoaRedeSocial = new PessoaRedeSocial()
            .nomeRede(DEFAULT_NOME_REDE)
            .endereco(DEFAULT_ENDERECO)
            .dataRegistro(DEFAULT_DATA_REGISTRO)
            .dataExclusao(DEFAULT_DATA_EXCLUSAO)
            .divulgarNoAplicativo(DEFAULT_DIVULGAR_NO_APLICATIVO);
        return pessoaRedeSocial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PessoaRedeSocial createUpdatedEntity(EntityManager em) {
        PessoaRedeSocial pessoaRedeSocial = new PessoaRedeSocial()
            .nomeRede(UPDATED_NOME_REDE)
            .endereco(UPDATED_ENDERECO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataExclusao(UPDATED_DATA_EXCLUSAO)
            .divulgarNoAplicativo(UPDATED_DIVULGAR_NO_APLICATIVO);
        return pessoaRedeSocial;
    }

    @BeforeEach
    public void initTest() {
        pessoaRedeSocial = createEntity(em);
    }

    @Test
    @Transactional
    void createPessoaRedeSocial() throws Exception {
        int databaseSizeBeforeCreate = pessoaRedeSocialRepository.findAll().size();
        // Create the PessoaRedeSocial
        PessoaRedeSocialDTO pessoaRedeSocialDTO = pessoaRedeSocialMapper.toDto(pessoaRedeSocial);
        restPessoaRedeSocialMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaRedeSocialDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PessoaRedeSocial in the database
        List<PessoaRedeSocial> pessoaRedeSocialList = pessoaRedeSocialRepository.findAll();
        assertThat(pessoaRedeSocialList).hasSize(databaseSizeBeforeCreate + 1);
        PessoaRedeSocial testPessoaRedeSocial = pessoaRedeSocialList.get(pessoaRedeSocialList.size() - 1);
        assertThat(testPessoaRedeSocial.getNomeRede()).isEqualTo(DEFAULT_NOME_REDE);
        assertThat(testPessoaRedeSocial.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testPessoaRedeSocial.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testPessoaRedeSocial.getDataExclusao()).isEqualTo(DEFAULT_DATA_EXCLUSAO);
        assertThat(testPessoaRedeSocial.getDivulgarNoAplicativo()).isEqualTo(DEFAULT_DIVULGAR_NO_APLICATIVO);
    }

    @Test
    @Transactional
    void createPessoaRedeSocialWithExistingId() throws Exception {
        // Create the PessoaRedeSocial with an existing ID
        pessoaRedeSocial.setId(1L);
        PessoaRedeSocialDTO pessoaRedeSocialDTO = pessoaRedeSocialMapper.toDto(pessoaRedeSocial);

        int databaseSizeBeforeCreate = pessoaRedeSocialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPessoaRedeSocialMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaRedeSocialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaRedeSocial in the database
        List<PessoaRedeSocial> pessoaRedeSocialList = pessoaRedeSocialRepository.findAll();
        assertThat(pessoaRedeSocialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeRedeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRedeSocialRepository.findAll().size();
        // set the field null
        pessoaRedeSocial.setNomeRede(null);

        // Create the PessoaRedeSocial, which fails.
        PessoaRedeSocialDTO pessoaRedeSocialDTO = pessoaRedeSocialMapper.toDto(pessoaRedeSocial);

        restPessoaRedeSocialMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaRedeSocialDTO))
            )
            .andExpect(status().isBadRequest());

        List<PessoaRedeSocial> pessoaRedeSocialList = pessoaRedeSocialRepository.findAll();
        assertThat(pessoaRedeSocialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRedeSocialRepository.findAll().size();
        // set the field null
        pessoaRedeSocial.setDataRegistro(null);

        // Create the PessoaRedeSocial, which fails.
        PessoaRedeSocialDTO pessoaRedeSocialDTO = pessoaRedeSocialMapper.toDto(pessoaRedeSocial);

        restPessoaRedeSocialMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaRedeSocialDTO))
            )
            .andExpect(status().isBadRequest());

        List<PessoaRedeSocial> pessoaRedeSocialList = pessoaRedeSocialRepository.findAll();
        assertThat(pessoaRedeSocialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPessoaRedeSocials() throws Exception {
        // Initialize the database
        pessoaRedeSocialRepository.saveAndFlush(pessoaRedeSocial);

        // Get all the pessoaRedeSocialList
        restPessoaRedeSocialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoaRedeSocial.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeRede").value(hasItem(DEFAULT_NOME_REDE)))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataExclusao").value(hasItem(DEFAULT_DATA_EXCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].divulgarNoAplicativo").value(hasItem(DEFAULT_DIVULGAR_NO_APLICATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getPessoaRedeSocial() throws Exception {
        // Initialize the database
        pessoaRedeSocialRepository.saveAndFlush(pessoaRedeSocial);

        // Get the pessoaRedeSocial
        restPessoaRedeSocialMockMvc
            .perform(get(ENTITY_API_URL_ID, pessoaRedeSocial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pessoaRedeSocial.getId().intValue()))
            .andExpect(jsonPath("$.nomeRede").value(DEFAULT_NOME_REDE))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.dataExclusao").value(DEFAULT_DATA_EXCLUSAO.toString()))
            .andExpect(jsonPath("$.divulgarNoAplicativo").value(DEFAULT_DIVULGAR_NO_APLICATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPessoaRedeSocial() throws Exception {
        // Get the pessoaRedeSocial
        restPessoaRedeSocialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPessoaRedeSocial() throws Exception {
        // Initialize the database
        pessoaRedeSocialRepository.saveAndFlush(pessoaRedeSocial);

        int databaseSizeBeforeUpdate = pessoaRedeSocialRepository.findAll().size();

        // Update the pessoaRedeSocial
        PessoaRedeSocial updatedPessoaRedeSocial = pessoaRedeSocialRepository.findById(pessoaRedeSocial.getId()).get();
        // Disconnect from session so that the updates on updatedPessoaRedeSocial are not directly saved in db
        em.detach(updatedPessoaRedeSocial);
        updatedPessoaRedeSocial
            .nomeRede(UPDATED_NOME_REDE)
            .endereco(UPDATED_ENDERECO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataExclusao(UPDATED_DATA_EXCLUSAO)
            .divulgarNoAplicativo(UPDATED_DIVULGAR_NO_APLICATIVO);
        PessoaRedeSocialDTO pessoaRedeSocialDTO = pessoaRedeSocialMapper.toDto(updatedPessoaRedeSocial);

        restPessoaRedeSocialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pessoaRedeSocialDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaRedeSocialDTO))
            )
            .andExpect(status().isOk());

        // Validate the PessoaRedeSocial in the database
        List<PessoaRedeSocial> pessoaRedeSocialList = pessoaRedeSocialRepository.findAll();
        assertThat(pessoaRedeSocialList).hasSize(databaseSizeBeforeUpdate);
        PessoaRedeSocial testPessoaRedeSocial = pessoaRedeSocialList.get(pessoaRedeSocialList.size() - 1);
        assertThat(testPessoaRedeSocial.getNomeRede()).isEqualTo(UPDATED_NOME_REDE);
        assertThat(testPessoaRedeSocial.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testPessoaRedeSocial.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testPessoaRedeSocial.getDataExclusao()).isEqualTo(UPDATED_DATA_EXCLUSAO);
        assertThat(testPessoaRedeSocial.getDivulgarNoAplicativo()).isEqualTo(UPDATED_DIVULGAR_NO_APLICATIVO);
    }

    @Test
    @Transactional
    void putNonExistingPessoaRedeSocial() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRedeSocialRepository.findAll().size();
        pessoaRedeSocial.setId(count.incrementAndGet());

        // Create the PessoaRedeSocial
        PessoaRedeSocialDTO pessoaRedeSocialDTO = pessoaRedeSocialMapper.toDto(pessoaRedeSocial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaRedeSocialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pessoaRedeSocialDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaRedeSocialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaRedeSocial in the database
        List<PessoaRedeSocial> pessoaRedeSocialList = pessoaRedeSocialRepository.findAll();
        assertThat(pessoaRedeSocialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPessoaRedeSocial() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRedeSocialRepository.findAll().size();
        pessoaRedeSocial.setId(count.incrementAndGet());

        // Create the PessoaRedeSocial
        PessoaRedeSocialDTO pessoaRedeSocialDTO = pessoaRedeSocialMapper.toDto(pessoaRedeSocial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaRedeSocialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaRedeSocialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaRedeSocial in the database
        List<PessoaRedeSocial> pessoaRedeSocialList = pessoaRedeSocialRepository.findAll();
        assertThat(pessoaRedeSocialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPessoaRedeSocial() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRedeSocialRepository.findAll().size();
        pessoaRedeSocial.setId(count.incrementAndGet());

        // Create the PessoaRedeSocial
        PessoaRedeSocialDTO pessoaRedeSocialDTO = pessoaRedeSocialMapper.toDto(pessoaRedeSocial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaRedeSocialMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaRedeSocialDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PessoaRedeSocial in the database
        List<PessoaRedeSocial> pessoaRedeSocialList = pessoaRedeSocialRepository.findAll();
        assertThat(pessoaRedeSocialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePessoaRedeSocialWithPatch() throws Exception {
        // Initialize the database
        pessoaRedeSocialRepository.saveAndFlush(pessoaRedeSocial);

        int databaseSizeBeforeUpdate = pessoaRedeSocialRepository.findAll().size();

        // Update the pessoaRedeSocial using partial update
        PessoaRedeSocial partialUpdatedPessoaRedeSocial = new PessoaRedeSocial();
        partialUpdatedPessoaRedeSocial.setId(pessoaRedeSocial.getId());

        partialUpdatedPessoaRedeSocial.dataRegistro(UPDATED_DATA_REGISTRO).divulgarNoAplicativo(UPDATED_DIVULGAR_NO_APLICATIVO);

        restPessoaRedeSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoaRedeSocial.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPessoaRedeSocial))
            )
            .andExpect(status().isOk());

        // Validate the PessoaRedeSocial in the database
        List<PessoaRedeSocial> pessoaRedeSocialList = pessoaRedeSocialRepository.findAll();
        assertThat(pessoaRedeSocialList).hasSize(databaseSizeBeforeUpdate);
        PessoaRedeSocial testPessoaRedeSocial = pessoaRedeSocialList.get(pessoaRedeSocialList.size() - 1);
        assertThat(testPessoaRedeSocial.getNomeRede()).isEqualTo(DEFAULT_NOME_REDE);
        assertThat(testPessoaRedeSocial.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testPessoaRedeSocial.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testPessoaRedeSocial.getDataExclusao()).isEqualTo(DEFAULT_DATA_EXCLUSAO);
        assertThat(testPessoaRedeSocial.getDivulgarNoAplicativo()).isEqualTo(UPDATED_DIVULGAR_NO_APLICATIVO);
    }

    @Test
    @Transactional
    void fullUpdatePessoaRedeSocialWithPatch() throws Exception {
        // Initialize the database
        pessoaRedeSocialRepository.saveAndFlush(pessoaRedeSocial);

        int databaseSizeBeforeUpdate = pessoaRedeSocialRepository.findAll().size();

        // Update the pessoaRedeSocial using partial update
        PessoaRedeSocial partialUpdatedPessoaRedeSocial = new PessoaRedeSocial();
        partialUpdatedPessoaRedeSocial.setId(pessoaRedeSocial.getId());

        partialUpdatedPessoaRedeSocial
            .nomeRede(UPDATED_NOME_REDE)
            .endereco(UPDATED_ENDERECO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataExclusao(UPDATED_DATA_EXCLUSAO)
            .divulgarNoAplicativo(UPDATED_DIVULGAR_NO_APLICATIVO);

        restPessoaRedeSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoaRedeSocial.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPessoaRedeSocial))
            )
            .andExpect(status().isOk());

        // Validate the PessoaRedeSocial in the database
        List<PessoaRedeSocial> pessoaRedeSocialList = pessoaRedeSocialRepository.findAll();
        assertThat(pessoaRedeSocialList).hasSize(databaseSizeBeforeUpdate);
        PessoaRedeSocial testPessoaRedeSocial = pessoaRedeSocialList.get(pessoaRedeSocialList.size() - 1);
        assertThat(testPessoaRedeSocial.getNomeRede()).isEqualTo(UPDATED_NOME_REDE);
        assertThat(testPessoaRedeSocial.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testPessoaRedeSocial.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testPessoaRedeSocial.getDataExclusao()).isEqualTo(UPDATED_DATA_EXCLUSAO);
        assertThat(testPessoaRedeSocial.getDivulgarNoAplicativo()).isEqualTo(UPDATED_DIVULGAR_NO_APLICATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingPessoaRedeSocial() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRedeSocialRepository.findAll().size();
        pessoaRedeSocial.setId(count.incrementAndGet());

        // Create the PessoaRedeSocial
        PessoaRedeSocialDTO pessoaRedeSocialDTO = pessoaRedeSocialMapper.toDto(pessoaRedeSocial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaRedeSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pessoaRedeSocialDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pessoaRedeSocialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaRedeSocial in the database
        List<PessoaRedeSocial> pessoaRedeSocialList = pessoaRedeSocialRepository.findAll();
        assertThat(pessoaRedeSocialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPessoaRedeSocial() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRedeSocialRepository.findAll().size();
        pessoaRedeSocial.setId(count.incrementAndGet());

        // Create the PessoaRedeSocial
        PessoaRedeSocialDTO pessoaRedeSocialDTO = pessoaRedeSocialMapper.toDto(pessoaRedeSocial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaRedeSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pessoaRedeSocialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaRedeSocial in the database
        List<PessoaRedeSocial> pessoaRedeSocialList = pessoaRedeSocialRepository.findAll();
        assertThat(pessoaRedeSocialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPessoaRedeSocial() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRedeSocialRepository.findAll().size();
        pessoaRedeSocial.setId(count.incrementAndGet());

        // Create the PessoaRedeSocial
        PessoaRedeSocialDTO pessoaRedeSocialDTO = pessoaRedeSocialMapper.toDto(pessoaRedeSocial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaRedeSocialMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pessoaRedeSocialDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PessoaRedeSocial in the database
        List<PessoaRedeSocial> pessoaRedeSocialList = pessoaRedeSocialRepository.findAll();
        assertThat(pessoaRedeSocialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePessoaRedeSocial() throws Exception {
        // Initialize the database
        pessoaRedeSocialRepository.saveAndFlush(pessoaRedeSocial);

        int databaseSizeBeforeDelete = pessoaRedeSocialRepository.findAll().size();

        // Delete the pessoaRedeSocial
        restPessoaRedeSocialMockMvc
            .perform(delete(ENTITY_API_URL_ID, pessoaRedeSocial.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PessoaRedeSocial> pessoaRedeSocialList = pessoaRedeSocialRepository.findAll();
        assertThat(pessoaRedeSocialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
