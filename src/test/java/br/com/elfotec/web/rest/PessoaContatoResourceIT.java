package br.com.elfotec.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.elfotec.IntegrationTest;
import br.com.elfotec.domain.PessoaContato;
import br.com.elfotec.repository.PessoaContatoRepository;
import br.com.elfotec.service.dto.PessoaContatoDTO;
import br.com.elfotec.service.mapper.PessoaContatoMapper;
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
 * Integration tests for the {@link PessoaContatoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PessoaContatoResourceIT {

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_IMPORTACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_IMPORTACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_EXCLUSAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_EXCLUSAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTATO_DIGITAL_IDENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTATO_DIGITAL_IDENT = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_NUMERO_COMPLETO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_NUMERO_COMPLETO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEFONE_DDD = 1;
    private static final Integer UPDATED_TELEFONE_DDD = 2;

    private static final Long DEFAULT_TELEFONE_NUMERO = 1L;
    private static final Long UPDATED_TELEFONE_NUMERO = 2L;

    private static final Boolean DEFAULT_PREFERIDO = false;
    private static final Boolean UPDATED_PREFERIDO = true;

    private static final Boolean DEFAULT_RECEBER_PROPAGANDAS = false;
    private static final Boolean UPDATED_RECEBER_PROPAGANDAS = true;

    private static final Boolean DEFAULT_RECEBER_CONFIRMACOES = false;
    private static final Boolean UPDATED_RECEBER_CONFIRMACOES = true;

    private static final Boolean DEFAULT_POSSUI_WHATSAPP = false;
    private static final Boolean UPDATED_POSSUI_WHATSAPP = true;

    private static final String ENTITY_API_URL = "/api/pessoa-contatoes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PessoaContatoRepository pessoaContatoRepository;

    @Autowired
    private PessoaContatoMapper pessoaContatoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPessoaContatoMockMvc;

    private PessoaContato pessoaContato;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PessoaContato createEntity(EntityManager em) {
        PessoaContato pessoaContato = new PessoaContato()
            .dataRegistro(DEFAULT_DATA_REGISTRO)
            .dataImportacao(DEFAULT_DATA_IMPORTACAO)
            .dataExclusao(DEFAULT_DATA_EXCLUSAO)
            .descricao(DEFAULT_DESCRICAO)
            .contatoDigitalIdent(DEFAULT_CONTATO_DIGITAL_IDENT)
            .telefoneNumeroCompleto(DEFAULT_TELEFONE_NUMERO_COMPLETO)
            .telefoneDdd(DEFAULT_TELEFONE_DDD)
            .telefoneNumero(DEFAULT_TELEFONE_NUMERO)
            .preferido(DEFAULT_PREFERIDO)
            .receberPropagandas(DEFAULT_RECEBER_PROPAGANDAS)
            .receberConfirmacoes(DEFAULT_RECEBER_CONFIRMACOES)
            .possuiWhatsapp(DEFAULT_POSSUI_WHATSAPP);
        return pessoaContato;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PessoaContato createUpdatedEntity(EntityManager em) {
        PessoaContato pessoaContato = new PessoaContato()
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataImportacao(UPDATED_DATA_IMPORTACAO)
            .dataExclusao(UPDATED_DATA_EXCLUSAO)
            .descricao(UPDATED_DESCRICAO)
            .contatoDigitalIdent(UPDATED_CONTATO_DIGITAL_IDENT)
            .telefoneNumeroCompleto(UPDATED_TELEFONE_NUMERO_COMPLETO)
            .telefoneDdd(UPDATED_TELEFONE_DDD)
            .telefoneNumero(UPDATED_TELEFONE_NUMERO)
            .preferido(UPDATED_PREFERIDO)
            .receberPropagandas(UPDATED_RECEBER_PROPAGANDAS)
            .receberConfirmacoes(UPDATED_RECEBER_CONFIRMACOES)
            .possuiWhatsapp(UPDATED_POSSUI_WHATSAPP);
        return pessoaContato;
    }

    @BeforeEach
    public void initTest() {
        pessoaContato = createEntity(em);
    }

    @Test
    @Transactional
    void createPessoaContato() throws Exception {
        int databaseSizeBeforeCreate = pessoaContatoRepository.findAll().size();
        // Create the PessoaContato
        PessoaContatoDTO pessoaContatoDTO = pessoaContatoMapper.toDto(pessoaContato);
        restPessoaContatoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaContatoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PessoaContato in the database
        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeCreate + 1);
        PessoaContato testPessoaContato = pessoaContatoList.get(pessoaContatoList.size() - 1);
        assertThat(testPessoaContato.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testPessoaContato.getDataImportacao()).isEqualTo(DEFAULT_DATA_IMPORTACAO);
        assertThat(testPessoaContato.getDataExclusao()).isEqualTo(DEFAULT_DATA_EXCLUSAO);
        assertThat(testPessoaContato.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPessoaContato.getContatoDigitalIdent()).isEqualTo(DEFAULT_CONTATO_DIGITAL_IDENT);
        assertThat(testPessoaContato.getTelefoneNumeroCompleto()).isEqualTo(DEFAULT_TELEFONE_NUMERO_COMPLETO);
        assertThat(testPessoaContato.getTelefoneDdd()).isEqualTo(DEFAULT_TELEFONE_DDD);
        assertThat(testPessoaContato.getTelefoneNumero()).isEqualTo(DEFAULT_TELEFONE_NUMERO);
        assertThat(testPessoaContato.getPreferido()).isEqualTo(DEFAULT_PREFERIDO);
        assertThat(testPessoaContato.getReceberPropagandas()).isEqualTo(DEFAULT_RECEBER_PROPAGANDAS);
        assertThat(testPessoaContato.getReceberConfirmacoes()).isEqualTo(DEFAULT_RECEBER_CONFIRMACOES);
        assertThat(testPessoaContato.getPossuiWhatsapp()).isEqualTo(DEFAULT_POSSUI_WHATSAPP);
    }

    @Test
    @Transactional
    void createPessoaContatoWithExistingId() throws Exception {
        // Create the PessoaContato with an existing ID
        pessoaContato.setId(1L);
        PessoaContatoDTO pessoaContatoDTO = pessoaContatoMapper.toDto(pessoaContato);

        int databaseSizeBeforeCreate = pessoaContatoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPessoaContatoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaContatoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaContato in the database
        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaContatoRepository.findAll().size();
        // set the field null
        pessoaContato.setDataRegistro(null);

        // Create the PessoaContato, which fails.
        PessoaContatoDTO pessoaContatoDTO = pessoaContatoMapper.toDto(pessoaContato);

        restPessoaContatoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaContatoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPreferidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaContatoRepository.findAll().size();
        // set the field null
        pessoaContato.setPreferido(null);

        // Create the PessoaContato, which fails.
        PessoaContatoDTO pessoaContatoDTO = pessoaContatoMapper.toDto(pessoaContato);

        restPessoaContatoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaContatoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReceberPropagandasIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaContatoRepository.findAll().size();
        // set the field null
        pessoaContato.setReceberPropagandas(null);

        // Create the PessoaContato, which fails.
        PessoaContatoDTO pessoaContatoDTO = pessoaContatoMapper.toDto(pessoaContato);

        restPessoaContatoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaContatoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReceberConfirmacoesIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaContatoRepository.findAll().size();
        // set the field null
        pessoaContato.setReceberConfirmacoes(null);

        // Create the PessoaContato, which fails.
        PessoaContatoDTO pessoaContatoDTO = pessoaContatoMapper.toDto(pessoaContato);

        restPessoaContatoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaContatoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPossuiWhatsappIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaContatoRepository.findAll().size();
        // set the field null
        pessoaContato.setPossuiWhatsapp(null);

        // Create the PessoaContato, which fails.
        PessoaContatoDTO pessoaContatoDTO = pessoaContatoMapper.toDto(pessoaContato);

        restPessoaContatoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaContatoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPessoaContatoes() throws Exception {
        // Initialize the database
        pessoaContatoRepository.saveAndFlush(pessoaContato);

        // Get all the pessoaContatoList
        restPessoaContatoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoaContato.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataImportacao").value(hasItem(DEFAULT_DATA_IMPORTACAO.toString())))
            .andExpect(jsonPath("$.[*].dataExclusao").value(hasItem(DEFAULT_DATA_EXCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].contatoDigitalIdent").value(hasItem(DEFAULT_CONTATO_DIGITAL_IDENT)))
            .andExpect(jsonPath("$.[*].telefoneNumeroCompleto").value(hasItem(DEFAULT_TELEFONE_NUMERO_COMPLETO)))
            .andExpect(jsonPath("$.[*].telefoneDdd").value(hasItem(DEFAULT_TELEFONE_DDD)))
            .andExpect(jsonPath("$.[*].telefoneNumero").value(hasItem(DEFAULT_TELEFONE_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].preferido").value(hasItem(DEFAULT_PREFERIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].receberPropagandas").value(hasItem(DEFAULT_RECEBER_PROPAGANDAS.booleanValue())))
            .andExpect(jsonPath("$.[*].receberConfirmacoes").value(hasItem(DEFAULT_RECEBER_CONFIRMACOES.booleanValue())))
            .andExpect(jsonPath("$.[*].possuiWhatsapp").value(hasItem(DEFAULT_POSSUI_WHATSAPP.booleanValue())));
    }

    @Test
    @Transactional
    void getPessoaContato() throws Exception {
        // Initialize the database
        pessoaContatoRepository.saveAndFlush(pessoaContato);

        // Get the pessoaContato
        restPessoaContatoMockMvc
            .perform(get(ENTITY_API_URL_ID, pessoaContato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pessoaContato.getId().intValue()))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.dataImportacao").value(DEFAULT_DATA_IMPORTACAO.toString()))
            .andExpect(jsonPath("$.dataExclusao").value(DEFAULT_DATA_EXCLUSAO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.contatoDigitalIdent").value(DEFAULT_CONTATO_DIGITAL_IDENT))
            .andExpect(jsonPath("$.telefoneNumeroCompleto").value(DEFAULT_TELEFONE_NUMERO_COMPLETO))
            .andExpect(jsonPath("$.telefoneDdd").value(DEFAULT_TELEFONE_DDD))
            .andExpect(jsonPath("$.telefoneNumero").value(DEFAULT_TELEFONE_NUMERO.intValue()))
            .andExpect(jsonPath("$.preferido").value(DEFAULT_PREFERIDO.booleanValue()))
            .andExpect(jsonPath("$.receberPropagandas").value(DEFAULT_RECEBER_PROPAGANDAS.booleanValue()))
            .andExpect(jsonPath("$.receberConfirmacoes").value(DEFAULT_RECEBER_CONFIRMACOES.booleanValue()))
            .andExpect(jsonPath("$.possuiWhatsapp").value(DEFAULT_POSSUI_WHATSAPP.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPessoaContato() throws Exception {
        // Get the pessoaContato
        restPessoaContatoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPessoaContato() throws Exception {
        // Initialize the database
        pessoaContatoRepository.saveAndFlush(pessoaContato);

        int databaseSizeBeforeUpdate = pessoaContatoRepository.findAll().size();

        // Update the pessoaContato
        PessoaContato updatedPessoaContato = pessoaContatoRepository.findById(pessoaContato.getId()).get();
        // Disconnect from session so that the updates on updatedPessoaContato are not directly saved in db
        em.detach(updatedPessoaContato);
        updatedPessoaContato
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataImportacao(UPDATED_DATA_IMPORTACAO)
            .dataExclusao(UPDATED_DATA_EXCLUSAO)
            .descricao(UPDATED_DESCRICAO)
            .contatoDigitalIdent(UPDATED_CONTATO_DIGITAL_IDENT)
            .telefoneNumeroCompleto(UPDATED_TELEFONE_NUMERO_COMPLETO)
            .telefoneDdd(UPDATED_TELEFONE_DDD)
            .telefoneNumero(UPDATED_TELEFONE_NUMERO)
            .preferido(UPDATED_PREFERIDO)
            .receberPropagandas(UPDATED_RECEBER_PROPAGANDAS)
            .receberConfirmacoes(UPDATED_RECEBER_CONFIRMACOES)
            .possuiWhatsapp(UPDATED_POSSUI_WHATSAPP);
        PessoaContatoDTO pessoaContatoDTO = pessoaContatoMapper.toDto(updatedPessoaContato);

        restPessoaContatoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pessoaContatoDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaContatoDTO))
            )
            .andExpect(status().isOk());

        // Validate the PessoaContato in the database
        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeUpdate);
        PessoaContato testPessoaContato = pessoaContatoList.get(pessoaContatoList.size() - 1);
        assertThat(testPessoaContato.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testPessoaContato.getDataImportacao()).isEqualTo(UPDATED_DATA_IMPORTACAO);
        assertThat(testPessoaContato.getDataExclusao()).isEqualTo(UPDATED_DATA_EXCLUSAO);
        assertThat(testPessoaContato.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPessoaContato.getContatoDigitalIdent()).isEqualTo(UPDATED_CONTATO_DIGITAL_IDENT);
        assertThat(testPessoaContato.getTelefoneNumeroCompleto()).isEqualTo(UPDATED_TELEFONE_NUMERO_COMPLETO);
        assertThat(testPessoaContato.getTelefoneDdd()).isEqualTo(UPDATED_TELEFONE_DDD);
        assertThat(testPessoaContato.getTelefoneNumero()).isEqualTo(UPDATED_TELEFONE_NUMERO);
        assertThat(testPessoaContato.getPreferido()).isEqualTo(UPDATED_PREFERIDO);
        assertThat(testPessoaContato.getReceberPropagandas()).isEqualTo(UPDATED_RECEBER_PROPAGANDAS);
        assertThat(testPessoaContato.getReceberConfirmacoes()).isEqualTo(UPDATED_RECEBER_CONFIRMACOES);
        assertThat(testPessoaContato.getPossuiWhatsapp()).isEqualTo(UPDATED_POSSUI_WHATSAPP);
    }

    @Test
    @Transactional
    void putNonExistingPessoaContato() throws Exception {
        int databaseSizeBeforeUpdate = pessoaContatoRepository.findAll().size();
        pessoaContato.setId(count.incrementAndGet());

        // Create the PessoaContato
        PessoaContatoDTO pessoaContatoDTO = pessoaContatoMapper.toDto(pessoaContato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaContatoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pessoaContatoDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaContatoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaContato in the database
        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPessoaContato() throws Exception {
        int databaseSizeBeforeUpdate = pessoaContatoRepository.findAll().size();
        pessoaContato.setId(count.incrementAndGet());

        // Create the PessoaContato
        PessoaContatoDTO pessoaContatoDTO = pessoaContatoMapper.toDto(pessoaContato);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaContatoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaContatoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaContato in the database
        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPessoaContato() throws Exception {
        int databaseSizeBeforeUpdate = pessoaContatoRepository.findAll().size();
        pessoaContato.setId(count.incrementAndGet());

        // Create the PessoaContato
        PessoaContatoDTO pessoaContatoDTO = pessoaContatoMapper.toDto(pessoaContato);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaContatoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaContatoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PessoaContato in the database
        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePessoaContatoWithPatch() throws Exception {
        // Initialize the database
        pessoaContatoRepository.saveAndFlush(pessoaContato);

        int databaseSizeBeforeUpdate = pessoaContatoRepository.findAll().size();

        // Update the pessoaContato using partial update
        PessoaContato partialUpdatedPessoaContato = new PessoaContato();
        partialUpdatedPessoaContato.setId(pessoaContato.getId());

        partialUpdatedPessoaContato
            .dataImportacao(UPDATED_DATA_IMPORTACAO)
            .contatoDigitalIdent(UPDATED_CONTATO_DIGITAL_IDENT)
            .telefoneNumeroCompleto(UPDATED_TELEFONE_NUMERO_COMPLETO)
            .telefoneDdd(UPDATED_TELEFONE_DDD)
            .preferido(UPDATED_PREFERIDO)
            .receberPropagandas(UPDATED_RECEBER_PROPAGANDAS)
            .possuiWhatsapp(UPDATED_POSSUI_WHATSAPP);

        restPessoaContatoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoaContato.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPessoaContato))
            )
            .andExpect(status().isOk());

        // Validate the PessoaContato in the database
        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeUpdate);
        PessoaContato testPessoaContato = pessoaContatoList.get(pessoaContatoList.size() - 1);
        assertThat(testPessoaContato.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testPessoaContato.getDataImportacao()).isEqualTo(UPDATED_DATA_IMPORTACAO);
        assertThat(testPessoaContato.getDataExclusao()).isEqualTo(DEFAULT_DATA_EXCLUSAO);
        assertThat(testPessoaContato.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPessoaContato.getContatoDigitalIdent()).isEqualTo(UPDATED_CONTATO_DIGITAL_IDENT);
        assertThat(testPessoaContato.getTelefoneNumeroCompleto()).isEqualTo(UPDATED_TELEFONE_NUMERO_COMPLETO);
        assertThat(testPessoaContato.getTelefoneDdd()).isEqualTo(UPDATED_TELEFONE_DDD);
        assertThat(testPessoaContato.getTelefoneNumero()).isEqualTo(DEFAULT_TELEFONE_NUMERO);
        assertThat(testPessoaContato.getPreferido()).isEqualTo(UPDATED_PREFERIDO);
        assertThat(testPessoaContato.getReceberPropagandas()).isEqualTo(UPDATED_RECEBER_PROPAGANDAS);
        assertThat(testPessoaContato.getReceberConfirmacoes()).isEqualTo(DEFAULT_RECEBER_CONFIRMACOES);
        assertThat(testPessoaContato.getPossuiWhatsapp()).isEqualTo(UPDATED_POSSUI_WHATSAPP);
    }

    @Test
    @Transactional
    void fullUpdatePessoaContatoWithPatch() throws Exception {
        // Initialize the database
        pessoaContatoRepository.saveAndFlush(pessoaContato);

        int databaseSizeBeforeUpdate = pessoaContatoRepository.findAll().size();

        // Update the pessoaContato using partial update
        PessoaContato partialUpdatedPessoaContato = new PessoaContato();
        partialUpdatedPessoaContato.setId(pessoaContato.getId());

        partialUpdatedPessoaContato
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataImportacao(UPDATED_DATA_IMPORTACAO)
            .dataExclusao(UPDATED_DATA_EXCLUSAO)
            .descricao(UPDATED_DESCRICAO)
            .contatoDigitalIdent(UPDATED_CONTATO_DIGITAL_IDENT)
            .telefoneNumeroCompleto(UPDATED_TELEFONE_NUMERO_COMPLETO)
            .telefoneDdd(UPDATED_TELEFONE_DDD)
            .telefoneNumero(UPDATED_TELEFONE_NUMERO)
            .preferido(UPDATED_PREFERIDO)
            .receberPropagandas(UPDATED_RECEBER_PROPAGANDAS)
            .receberConfirmacoes(UPDATED_RECEBER_CONFIRMACOES)
            .possuiWhatsapp(UPDATED_POSSUI_WHATSAPP);

        restPessoaContatoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoaContato.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPessoaContato))
            )
            .andExpect(status().isOk());

        // Validate the PessoaContato in the database
        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeUpdate);
        PessoaContato testPessoaContato = pessoaContatoList.get(pessoaContatoList.size() - 1);
        assertThat(testPessoaContato.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testPessoaContato.getDataImportacao()).isEqualTo(UPDATED_DATA_IMPORTACAO);
        assertThat(testPessoaContato.getDataExclusao()).isEqualTo(UPDATED_DATA_EXCLUSAO);
        assertThat(testPessoaContato.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPessoaContato.getContatoDigitalIdent()).isEqualTo(UPDATED_CONTATO_DIGITAL_IDENT);
        assertThat(testPessoaContato.getTelefoneNumeroCompleto()).isEqualTo(UPDATED_TELEFONE_NUMERO_COMPLETO);
        assertThat(testPessoaContato.getTelefoneDdd()).isEqualTo(UPDATED_TELEFONE_DDD);
        assertThat(testPessoaContato.getTelefoneNumero()).isEqualTo(UPDATED_TELEFONE_NUMERO);
        assertThat(testPessoaContato.getPreferido()).isEqualTo(UPDATED_PREFERIDO);
        assertThat(testPessoaContato.getReceberPropagandas()).isEqualTo(UPDATED_RECEBER_PROPAGANDAS);
        assertThat(testPessoaContato.getReceberConfirmacoes()).isEqualTo(UPDATED_RECEBER_CONFIRMACOES);
        assertThat(testPessoaContato.getPossuiWhatsapp()).isEqualTo(UPDATED_POSSUI_WHATSAPP);
    }

    @Test
    @Transactional
    void patchNonExistingPessoaContato() throws Exception {
        int databaseSizeBeforeUpdate = pessoaContatoRepository.findAll().size();
        pessoaContato.setId(count.incrementAndGet());

        // Create the PessoaContato
        PessoaContatoDTO pessoaContatoDTO = pessoaContatoMapper.toDto(pessoaContato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaContatoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pessoaContatoDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pessoaContatoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaContato in the database
        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPessoaContato() throws Exception {
        int databaseSizeBeforeUpdate = pessoaContatoRepository.findAll().size();
        pessoaContato.setId(count.incrementAndGet());

        // Create the PessoaContato
        PessoaContatoDTO pessoaContatoDTO = pessoaContatoMapper.toDto(pessoaContato);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaContatoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pessoaContatoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaContato in the database
        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPessoaContato() throws Exception {
        int databaseSizeBeforeUpdate = pessoaContatoRepository.findAll().size();
        pessoaContato.setId(count.incrementAndGet());

        // Create the PessoaContato
        PessoaContatoDTO pessoaContatoDTO = pessoaContatoMapper.toDto(pessoaContato);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaContatoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pessoaContatoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PessoaContato in the database
        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePessoaContato() throws Exception {
        // Initialize the database
        pessoaContatoRepository.saveAndFlush(pessoaContato);

        int databaseSizeBeforeDelete = pessoaContatoRepository.findAll().size();

        // Delete the pessoaContato
        restPessoaContatoMockMvc
            .perform(delete(ENTITY_API_URL_ID, pessoaContato.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PessoaContato> pessoaContatoList = pessoaContatoRepository.findAll();
        assertThat(pessoaContatoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
