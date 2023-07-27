package br.com.elfotec.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.elfotec.IntegrationTest;
import br.com.elfotec.domain.Pessoa;
import br.com.elfotec.domain.enumeration.EstadoCivil;
import br.com.elfotec.domain.enumeration.Sexo;
import br.com.elfotec.domain.enumeration.TipoPessoa;
import br.com.elfotec.domain.enumeration.TipoSanguineo;
import br.com.elfotec.repository.PessoaRepository;
import br.com.elfotec.service.dto.PessoaDTO;
import br.com.elfotec.service.mapper.PessoaMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PessoaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PessoaResourceIT {

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_NOME_SOCIAL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_POSSUI_NOME_SOCIAL = false;
    private static final Boolean UPDATED_POSSUI_NOME_SOCIAL = true;

    private static final String DEFAULT_APELIDO_NOME_FANTASIA = "AAAAAAAAAA";
    private static final String UPDATED_APELIDO_NOME_FANTASIA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOME_PAI = "AAAAAAAAAA";
    private static final String UPDATED_NOME_PAI = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_MAE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MAE = "BBBBBBBBBB";

    private static final TipoSanguineo DEFAULT_TIPO_SANGUINEO = TipoSanguineo.A_POS;
    private static final TipoSanguineo UPDATED_TIPO_SANGUINEO = TipoSanguineo.A_NEG;

    private static final Sexo DEFAULT_SEXO_BIOLOGICO_AO_NASCER = Sexo.M;
    private static final Sexo UPDATED_SEXO_BIOLOGICO_AO_NASCER = Sexo.F;

    private static final TipoPessoa DEFAULT_TIPO_PESSOA = TipoPessoa.PF;
    private static final TipoPessoa UPDATED_TIPO_PESSOA = TipoPessoa.PJ;

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_RG = "AAAAAAAAAA";
    private static final String UPDATED_RG = "BBBBBBBBBB";

    private static final String DEFAULT_IE = "AAAAAAAAAA";
    private static final String UPDATED_IE = "BBBBBBBBBB";

    private static final EstadoCivil DEFAULT_ESTADO_CIVIL = EstadoCivil.SOLTEIRO;
    private static final EstadoCivil UPDATED_ESTADO_CIVIL = EstadoCivil.CASADO;

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final String DEFAULT_NATURALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NATURALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_RACA = "AAAAAAAAAA";
    private static final String UPDATED_RACA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pessoas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaMapper pessoaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPessoaMockMvc;

    private Pessoa pessoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pessoa createEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .dataRegistro(DEFAULT_DATA_REGISTRO)
            .nome(DEFAULT_NOME)
            .nomeSocial(DEFAULT_NOME_SOCIAL)
            .possuiNomeSocial(DEFAULT_POSSUI_NOME_SOCIAL)
            .apelidoNomeFantasia(DEFAULT_APELIDO_NOME_FANTASIA)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .nomePai(DEFAULT_NOME_PAI)
            .nomeMae(DEFAULT_NOME_MAE)
            .tipoSanguineo(DEFAULT_TIPO_SANGUINEO)
            .sexoBiologicoAoNascer(DEFAULT_SEXO_BIOLOGICO_AO_NASCER)
            .tipoPessoa(DEFAULT_TIPO_PESSOA)
            .cpf(DEFAULT_CPF)
            .cnpj(DEFAULT_CNPJ)
            .rg(DEFAULT_RG)
            .ie(DEFAULT_IE)
            .estadoCivil(DEFAULT_ESTADO_CIVIL)
            .observacoes(DEFAULT_OBSERVACOES)
            .naturalidade(DEFAULT_NATURALIDADE)
            .raca(DEFAULT_RACA);
        return pessoa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pessoa createUpdatedEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .nome(UPDATED_NOME)
            .nomeSocial(UPDATED_NOME_SOCIAL)
            .possuiNomeSocial(UPDATED_POSSUI_NOME_SOCIAL)
            .apelidoNomeFantasia(UPDATED_APELIDO_NOME_FANTASIA)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .nomePai(UPDATED_NOME_PAI)
            .nomeMae(UPDATED_NOME_MAE)
            .tipoSanguineo(UPDATED_TIPO_SANGUINEO)
            .sexoBiologicoAoNascer(UPDATED_SEXO_BIOLOGICO_AO_NASCER)
            .tipoPessoa(UPDATED_TIPO_PESSOA)
            .cpf(UPDATED_CPF)
            .cnpj(UPDATED_CNPJ)
            .rg(UPDATED_RG)
            .ie(UPDATED_IE)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .observacoes(UPDATED_OBSERVACOES)
            .naturalidade(UPDATED_NATURALIDADE)
            .raca(UPDATED_RACA);
        return pessoa;
    }

    @BeforeEach
    public void initTest() {
        pessoa = createEntity(em);
    }

    @Test
    @Transactional
    void createPessoa() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();
        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);
        restPessoaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate + 1);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testPessoa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPessoa.getNomeSocial()).isEqualTo(DEFAULT_NOME_SOCIAL);
        assertThat(testPessoa.getPossuiNomeSocial()).isEqualTo(DEFAULT_POSSUI_NOME_SOCIAL);
        assertThat(testPessoa.getApelidoNomeFantasia()).isEqualTo(DEFAULT_APELIDO_NOME_FANTASIA);
        assertThat(testPessoa.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testPessoa.getNomePai()).isEqualTo(DEFAULT_NOME_PAI);
        assertThat(testPessoa.getNomeMae()).isEqualTo(DEFAULT_NOME_MAE);
        assertThat(testPessoa.getTipoSanguineo()).isEqualTo(DEFAULT_TIPO_SANGUINEO);
        assertThat(testPessoa.getSexoBiologicoAoNascer()).isEqualTo(DEFAULT_SEXO_BIOLOGICO_AO_NASCER);
        assertThat(testPessoa.getTipoPessoa()).isEqualTo(DEFAULT_TIPO_PESSOA);
        assertThat(testPessoa.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testPessoa.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testPessoa.getRg()).isEqualTo(DEFAULT_RG);
        assertThat(testPessoa.getIe()).isEqualTo(DEFAULT_IE);
        assertThat(testPessoa.getEstadoCivil()).isEqualTo(DEFAULT_ESTADO_CIVIL);
        assertThat(testPessoa.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testPessoa.getNaturalidade()).isEqualTo(DEFAULT_NATURALIDADE);
        assertThat(testPessoa.getRaca()).isEqualTo(DEFAULT_RACA);
    }

    @Test
    @Transactional
    void createPessoaWithExistingId() throws Exception {
        // Create the Pessoa with an existing ID
        pessoa.setId(1L);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPessoaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setDataRegistro(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setNome(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoPessoaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setTipoPessoa(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPessoas() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList
        restPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nomeSocial").value(hasItem(DEFAULT_NOME_SOCIAL)))
            .andExpect(jsonPath("$.[*].possuiNomeSocial").value(hasItem(DEFAULT_POSSUI_NOME_SOCIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].apelidoNomeFantasia").value(hasItem(DEFAULT_APELIDO_NOME_FANTASIA)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].nomePai").value(hasItem(DEFAULT_NOME_PAI)))
            .andExpect(jsonPath("$.[*].nomeMae").value(hasItem(DEFAULT_NOME_MAE)))
            .andExpect(jsonPath("$.[*].tipoSanguineo").value(hasItem(DEFAULT_TIPO_SANGUINEO.toString())))
            .andExpect(jsonPath("$.[*].sexoBiologicoAoNascer").value(hasItem(DEFAULT_SEXO_BIOLOGICO_AO_NASCER.toString())))
            .andExpect(jsonPath("$.[*].tipoPessoa").value(hasItem(DEFAULT_TIPO_PESSOA.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG)))
            .andExpect(jsonPath("$.[*].ie").value(hasItem(DEFAULT_IE)))
            .andExpect(jsonPath("$.[*].estadoCivil").value(hasItem(DEFAULT_ESTADO_CIVIL.toString())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].naturalidade").value(hasItem(DEFAULT_NATURALIDADE)))
            .andExpect(jsonPath("$.[*].raca").value(hasItem(DEFAULT_RACA)));
    }

    @Test
    @Transactional
    void getPessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get the pessoa
        restPessoaMockMvc
            .perform(get(ENTITY_API_URL_ID, pessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pessoa.getId().intValue()))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.nomeSocial").value(DEFAULT_NOME_SOCIAL))
            .andExpect(jsonPath("$.possuiNomeSocial").value(DEFAULT_POSSUI_NOME_SOCIAL.booleanValue()))
            .andExpect(jsonPath("$.apelidoNomeFantasia").value(DEFAULT_APELIDO_NOME_FANTASIA))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.nomePai").value(DEFAULT_NOME_PAI))
            .andExpect(jsonPath("$.nomeMae").value(DEFAULT_NOME_MAE))
            .andExpect(jsonPath("$.tipoSanguineo").value(DEFAULT_TIPO_SANGUINEO.toString()))
            .andExpect(jsonPath("$.sexoBiologicoAoNascer").value(DEFAULT_SEXO_BIOLOGICO_AO_NASCER.toString()))
            .andExpect(jsonPath("$.tipoPessoa").value(DEFAULT_TIPO_PESSOA.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.rg").value(DEFAULT_RG))
            .andExpect(jsonPath("$.ie").value(DEFAULT_IE))
            .andExpect(jsonPath("$.estadoCivil").value(DEFAULT_ESTADO_CIVIL.toString()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()))
            .andExpect(jsonPath("$.naturalidade").value(DEFAULT_NATURALIDADE))
            .andExpect(jsonPath("$.raca").value(DEFAULT_RACA));
    }

    @Test
    @Transactional
    void getNonExistingPessoa() throws Exception {
        // Get the pessoa
        restPessoaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Update the pessoa
        Pessoa updatedPessoa = pessoaRepository.findById(pessoa.getId()).get();
        // Disconnect from session so that the updates on updatedPessoa are not directly saved in db
        em.detach(updatedPessoa);
        updatedPessoa
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .nome(UPDATED_NOME)
            .nomeSocial(UPDATED_NOME_SOCIAL)
            .possuiNomeSocial(UPDATED_POSSUI_NOME_SOCIAL)
            .apelidoNomeFantasia(UPDATED_APELIDO_NOME_FANTASIA)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .nomePai(UPDATED_NOME_PAI)
            .nomeMae(UPDATED_NOME_MAE)
            .tipoSanguineo(UPDATED_TIPO_SANGUINEO)
            .sexoBiologicoAoNascer(UPDATED_SEXO_BIOLOGICO_AO_NASCER)
            .tipoPessoa(UPDATED_TIPO_PESSOA)
            .cpf(UPDATED_CPF)
            .cnpj(UPDATED_CNPJ)
            .rg(UPDATED_RG)
            .ie(UPDATED_IE)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .observacoes(UPDATED_OBSERVACOES)
            .naturalidade(UPDATED_NATURALIDADE)
            .raca(UPDATED_RACA);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(updatedPessoa);

        restPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pessoaDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testPessoa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoa.getNomeSocial()).isEqualTo(UPDATED_NOME_SOCIAL);
        assertThat(testPessoa.getPossuiNomeSocial()).isEqualTo(UPDATED_POSSUI_NOME_SOCIAL);
        assertThat(testPessoa.getApelidoNomeFantasia()).isEqualTo(UPDATED_APELIDO_NOME_FANTASIA);
        assertThat(testPessoa.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testPessoa.getNomePai()).isEqualTo(UPDATED_NOME_PAI);
        assertThat(testPessoa.getNomeMae()).isEqualTo(UPDATED_NOME_MAE);
        assertThat(testPessoa.getTipoSanguineo()).isEqualTo(UPDATED_TIPO_SANGUINEO);
        assertThat(testPessoa.getSexoBiologicoAoNascer()).isEqualTo(UPDATED_SEXO_BIOLOGICO_AO_NASCER);
        assertThat(testPessoa.getTipoPessoa()).isEqualTo(UPDATED_TIPO_PESSOA);
        assertThat(testPessoa.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testPessoa.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testPessoa.getRg()).isEqualTo(UPDATED_RG);
        assertThat(testPessoa.getIe()).isEqualTo(UPDATED_IE);
        assertThat(testPessoa.getEstadoCivil()).isEqualTo(UPDATED_ESTADO_CIVIL);
        assertThat(testPessoa.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testPessoa.getNaturalidade()).isEqualTo(UPDATED_NATURALIDADE);
        assertThat(testPessoa.getRaca()).isEqualTo(UPDATED_RACA);
    }

    @Test
    @Transactional
    void putNonExistingPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();
        pessoa.setId(count.incrementAndGet());

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pessoaDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();
        pessoa.setId(count.incrementAndGet());

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();
        pessoa.setId(count.incrementAndGet());

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePessoaWithPatch() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Update the pessoa using partial update
        Pessoa partialUpdatedPessoa = new Pessoa();
        partialUpdatedPessoa.setId(pessoa.getId());

        partialUpdatedPessoa
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .nome(UPDATED_NOME)
            .possuiNomeSocial(UPDATED_POSSUI_NOME_SOCIAL)
            .apelidoNomeFantasia(UPDATED_APELIDO_NOME_FANTASIA)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .nomePai(UPDATED_NOME_PAI)
            .nomeMae(UPDATED_NOME_MAE)
            .tipoPessoa(UPDATED_TIPO_PESSOA)
            .cnpj(UPDATED_CNPJ)
            .ie(UPDATED_IE)
            .naturalidade(UPDATED_NATURALIDADE);

        restPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPessoa))
            )
            .andExpect(status().isOk());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testPessoa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoa.getNomeSocial()).isEqualTo(DEFAULT_NOME_SOCIAL);
        assertThat(testPessoa.getPossuiNomeSocial()).isEqualTo(UPDATED_POSSUI_NOME_SOCIAL);
        assertThat(testPessoa.getApelidoNomeFantasia()).isEqualTo(UPDATED_APELIDO_NOME_FANTASIA);
        assertThat(testPessoa.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testPessoa.getNomePai()).isEqualTo(UPDATED_NOME_PAI);
        assertThat(testPessoa.getNomeMae()).isEqualTo(UPDATED_NOME_MAE);
        assertThat(testPessoa.getTipoSanguineo()).isEqualTo(DEFAULT_TIPO_SANGUINEO);
        assertThat(testPessoa.getSexoBiologicoAoNascer()).isEqualTo(DEFAULT_SEXO_BIOLOGICO_AO_NASCER);
        assertThat(testPessoa.getTipoPessoa()).isEqualTo(UPDATED_TIPO_PESSOA);
        assertThat(testPessoa.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testPessoa.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testPessoa.getRg()).isEqualTo(DEFAULT_RG);
        assertThat(testPessoa.getIe()).isEqualTo(UPDATED_IE);
        assertThat(testPessoa.getEstadoCivil()).isEqualTo(DEFAULT_ESTADO_CIVIL);
        assertThat(testPessoa.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testPessoa.getNaturalidade()).isEqualTo(UPDATED_NATURALIDADE);
        assertThat(testPessoa.getRaca()).isEqualTo(DEFAULT_RACA);
    }

    @Test
    @Transactional
    void fullUpdatePessoaWithPatch() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Update the pessoa using partial update
        Pessoa partialUpdatedPessoa = new Pessoa();
        partialUpdatedPessoa.setId(pessoa.getId());

        partialUpdatedPessoa
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .nome(UPDATED_NOME)
            .nomeSocial(UPDATED_NOME_SOCIAL)
            .possuiNomeSocial(UPDATED_POSSUI_NOME_SOCIAL)
            .apelidoNomeFantasia(UPDATED_APELIDO_NOME_FANTASIA)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .nomePai(UPDATED_NOME_PAI)
            .nomeMae(UPDATED_NOME_MAE)
            .tipoSanguineo(UPDATED_TIPO_SANGUINEO)
            .sexoBiologicoAoNascer(UPDATED_SEXO_BIOLOGICO_AO_NASCER)
            .tipoPessoa(UPDATED_TIPO_PESSOA)
            .cpf(UPDATED_CPF)
            .cnpj(UPDATED_CNPJ)
            .rg(UPDATED_RG)
            .ie(UPDATED_IE)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .observacoes(UPDATED_OBSERVACOES)
            .naturalidade(UPDATED_NATURALIDADE)
            .raca(UPDATED_RACA);

        restPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPessoa))
            )
            .andExpect(status().isOk());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testPessoa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoa.getNomeSocial()).isEqualTo(UPDATED_NOME_SOCIAL);
        assertThat(testPessoa.getPossuiNomeSocial()).isEqualTo(UPDATED_POSSUI_NOME_SOCIAL);
        assertThat(testPessoa.getApelidoNomeFantasia()).isEqualTo(UPDATED_APELIDO_NOME_FANTASIA);
        assertThat(testPessoa.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testPessoa.getNomePai()).isEqualTo(UPDATED_NOME_PAI);
        assertThat(testPessoa.getNomeMae()).isEqualTo(UPDATED_NOME_MAE);
        assertThat(testPessoa.getTipoSanguineo()).isEqualTo(UPDATED_TIPO_SANGUINEO);
        assertThat(testPessoa.getSexoBiologicoAoNascer()).isEqualTo(UPDATED_SEXO_BIOLOGICO_AO_NASCER);
        assertThat(testPessoa.getTipoPessoa()).isEqualTo(UPDATED_TIPO_PESSOA);
        assertThat(testPessoa.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testPessoa.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testPessoa.getRg()).isEqualTo(UPDATED_RG);
        assertThat(testPessoa.getIe()).isEqualTo(UPDATED_IE);
        assertThat(testPessoa.getEstadoCivil()).isEqualTo(UPDATED_ESTADO_CIVIL);
        assertThat(testPessoa.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testPessoa.getNaturalidade()).isEqualTo(UPDATED_NATURALIDADE);
        assertThat(testPessoa.getRaca()).isEqualTo(UPDATED_RACA);
    }

    @Test
    @Transactional
    void patchNonExistingPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();
        pessoa.setId(count.incrementAndGet());

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pessoaDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();
        pessoa.setId(count.incrementAndGet());

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();
        pessoa.setId(count.incrementAndGet());

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeDelete = pessoaRepository.findAll().size();

        // Delete the pessoa
        restPessoaMockMvc
            .perform(delete(ENTITY_API_URL_ID, pessoa.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
