package br.com.elfotec.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.elfotec.IntegrationTest;
import br.com.elfotec.domain.MediaFile;
import br.com.elfotec.repository.MediaFileRepository;
import br.com.elfotec.service.dto.MediaFileDTO;
import br.com.elfotec.service.mapper.MediaFileMapper;
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
 * Integration tests for the {@link MediaFileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MediaFileResourceIT {

    private static final String DEFAULT_MEDIA_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_FILE_EXTENSION = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIA_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIA_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PUBLICO = false;
    private static final Boolean UPDATED_PUBLICO = true;

    private static final String DEFAULT_REPO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REPO_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REPO_UUID = "AAAAAAAAAA";
    private static final String UPDATED_REPO_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_REPO_FOLDER = "AAAAAAAAAA";
    private static final String UPDATED_REPO_FOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_REPO_PATH = "AAAAAAAAAA";
    private static final String UPDATED_REPO_PATH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_THUMBNAIL = false;
    private static final Boolean UPDATED_THUMBNAIL = true;

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_EXCLUSAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_EXCLUSAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TAMANHO_BYTES = 1;
    private static final Integer UPDATED_TAMANHO_BYTES = 2;

    private static final String ENTITY_API_URL = "/api/media-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MediaFileRepository mediaFileRepository;

    @Autowired
    private MediaFileMapper mediaFileMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMediaFileMockMvc;

    private MediaFile mediaFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MediaFile createEntity(EntityManager em) {
        MediaFile mediaFile = new MediaFile()
            .mediaType(DEFAULT_MEDIA_TYPE)
            .fileName(DEFAULT_FILE_NAME)
            .fileExtension(DEFAULT_FILE_EXTENSION)
            .mediaTitle(DEFAULT_MEDIA_TITLE)
            .mediaDescription(DEFAULT_MEDIA_DESCRIPTION)
            .publico(DEFAULT_PUBLICO)
            .repoName(DEFAULT_REPO_NAME)
            .repoUuid(DEFAULT_REPO_UUID)
            .repoFolder(DEFAULT_REPO_FOLDER)
            .repoPath(DEFAULT_REPO_PATH)
            .thumbnail(DEFAULT_THUMBNAIL)
            .width(DEFAULT_WIDTH)
            .height(DEFAULT_HEIGHT)
            .dataRegistro(DEFAULT_DATA_REGISTRO)
            .dataExclusao(DEFAULT_DATA_EXCLUSAO)
            .tamanhoBytes(DEFAULT_TAMANHO_BYTES);
        return mediaFile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MediaFile createUpdatedEntity(EntityManager em) {
        MediaFile mediaFile = new MediaFile()
            .mediaType(UPDATED_MEDIA_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .mediaTitle(UPDATED_MEDIA_TITLE)
            .mediaDescription(UPDATED_MEDIA_DESCRIPTION)
            .publico(UPDATED_PUBLICO)
            .repoName(UPDATED_REPO_NAME)
            .repoUuid(UPDATED_REPO_UUID)
            .repoFolder(UPDATED_REPO_FOLDER)
            .repoPath(UPDATED_REPO_PATH)
            .thumbnail(UPDATED_THUMBNAIL)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataExclusao(UPDATED_DATA_EXCLUSAO)
            .tamanhoBytes(UPDATED_TAMANHO_BYTES);
        return mediaFile;
    }

    @BeforeEach
    public void initTest() {
        mediaFile = createEntity(em);
    }

    @Test
    @Transactional
    void createMediaFile() throws Exception {
        int databaseSizeBeforeCreate = mediaFileRepository.findAll().size();
        // Create the MediaFile
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);
        restMediaFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeCreate + 1);
        MediaFile testMediaFile = mediaFileList.get(mediaFileList.size() - 1);
        assertThat(testMediaFile.getMediaType()).isEqualTo(DEFAULT_MEDIA_TYPE);
        assertThat(testMediaFile.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testMediaFile.getFileExtension()).isEqualTo(DEFAULT_FILE_EXTENSION);
        assertThat(testMediaFile.getMediaTitle()).isEqualTo(DEFAULT_MEDIA_TITLE);
        assertThat(testMediaFile.getMediaDescription()).isEqualTo(DEFAULT_MEDIA_DESCRIPTION);
        assertThat(testMediaFile.getPublico()).isEqualTo(DEFAULT_PUBLICO);
        assertThat(testMediaFile.getRepoName()).isEqualTo(DEFAULT_REPO_NAME);
        assertThat(testMediaFile.getRepoUuid()).isEqualTo(DEFAULT_REPO_UUID);
        assertThat(testMediaFile.getRepoFolder()).isEqualTo(DEFAULT_REPO_FOLDER);
        assertThat(testMediaFile.getRepoPath()).isEqualTo(DEFAULT_REPO_PATH);
        assertThat(testMediaFile.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testMediaFile.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testMediaFile.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testMediaFile.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testMediaFile.getDataExclusao()).isEqualTo(DEFAULT_DATA_EXCLUSAO);
        assertThat(testMediaFile.getTamanhoBytes()).isEqualTo(DEFAULT_TAMANHO_BYTES);
    }

    @Test
    @Transactional
    void createMediaFileWithExistingId() throws Exception {
        // Create the MediaFile with an existing ID
        mediaFile.setId(1L);
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);

        int databaseSizeBeforeCreate = mediaFileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMediaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaFileRepository.findAll().size();
        // set the field null
        mediaFile.setMediaType(null);

        // Create the MediaFile, which fails.
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);

        restMediaFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPublicoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaFileRepository.findAll().size();
        // set the field null
        mediaFile.setPublico(null);

        // Create the MediaFile, which fails.
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);

        restMediaFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRepoNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaFileRepository.findAll().size();
        // set the field null
        mediaFile.setRepoName(null);

        // Create the MediaFile, which fails.
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);

        restMediaFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRepoUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaFileRepository.findAll().size();
        // set the field null
        mediaFile.setRepoUuid(null);

        // Create the MediaFile, which fails.
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);

        restMediaFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRepoFolderIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaFileRepository.findAll().size();
        // set the field null
        mediaFile.setRepoFolder(null);

        // Create the MediaFile, which fails.
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);

        restMediaFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThumbnailIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaFileRepository.findAll().size();
        // set the field null
        mediaFile.setThumbnail(null);

        // Create the MediaFile, which fails.
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);

        restMediaFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaFileRepository.findAll().size();
        // set the field null
        mediaFile.setDataRegistro(null);

        // Create the MediaFile, which fails.
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);

        restMediaFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMediaFiles() throws Exception {
        // Initialize the database
        mediaFileRepository.saveAndFlush(mediaFile);

        // Get all the mediaFileList
        restMediaFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mediaFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileExtension").value(hasItem(DEFAULT_FILE_EXTENSION)))
            .andExpect(jsonPath("$.[*].mediaTitle").value(hasItem(DEFAULT_MEDIA_TITLE)))
            .andExpect(jsonPath("$.[*].mediaDescription").value(hasItem(DEFAULT_MEDIA_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].publico").value(hasItem(DEFAULT_PUBLICO.booleanValue())))
            .andExpect(jsonPath("$.[*].repoName").value(hasItem(DEFAULT_REPO_NAME)))
            .andExpect(jsonPath("$.[*].repoUuid").value(hasItem(DEFAULT_REPO_UUID)))
            .andExpect(jsonPath("$.[*].repoFolder").value(hasItem(DEFAULT_REPO_FOLDER)))
            .andExpect(jsonPath("$.[*].repoPath").value(hasItem(DEFAULT_REPO_PATH)))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataExclusao").value(hasItem(DEFAULT_DATA_EXCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].tamanhoBytes").value(hasItem(DEFAULT_TAMANHO_BYTES)));
    }

    @Test
    @Transactional
    void getMediaFile() throws Exception {
        // Initialize the database
        mediaFileRepository.saveAndFlush(mediaFile);

        // Get the mediaFile
        restMediaFileMockMvc
            .perform(get(ENTITY_API_URL_ID, mediaFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mediaFile.getId().intValue()))
            .andExpect(jsonPath("$.mediaType").value(DEFAULT_MEDIA_TYPE))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.fileExtension").value(DEFAULT_FILE_EXTENSION))
            .andExpect(jsonPath("$.mediaTitle").value(DEFAULT_MEDIA_TITLE))
            .andExpect(jsonPath("$.mediaDescription").value(DEFAULT_MEDIA_DESCRIPTION))
            .andExpect(jsonPath("$.publico").value(DEFAULT_PUBLICO.booleanValue()))
            .andExpect(jsonPath("$.repoName").value(DEFAULT_REPO_NAME))
            .andExpect(jsonPath("$.repoUuid").value(DEFAULT_REPO_UUID))
            .andExpect(jsonPath("$.repoFolder").value(DEFAULT_REPO_FOLDER))
            .andExpect(jsonPath("$.repoPath").value(DEFAULT_REPO_PATH))
            .andExpect(jsonPath("$.thumbnail").value(DEFAULT_THUMBNAIL.booleanValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.dataExclusao").value(DEFAULT_DATA_EXCLUSAO.toString()))
            .andExpect(jsonPath("$.tamanhoBytes").value(DEFAULT_TAMANHO_BYTES));
    }

    @Test
    @Transactional
    void getNonExistingMediaFile() throws Exception {
        // Get the mediaFile
        restMediaFileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMediaFile() throws Exception {
        // Initialize the database
        mediaFileRepository.saveAndFlush(mediaFile);

        int databaseSizeBeforeUpdate = mediaFileRepository.findAll().size();

        // Update the mediaFile
        MediaFile updatedMediaFile = mediaFileRepository.findById(mediaFile.getId()).get();
        // Disconnect from session so that the updates on updatedMediaFile are not directly saved in db
        em.detach(updatedMediaFile);
        updatedMediaFile
            .mediaType(UPDATED_MEDIA_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .mediaTitle(UPDATED_MEDIA_TITLE)
            .mediaDescription(UPDATED_MEDIA_DESCRIPTION)
            .publico(UPDATED_PUBLICO)
            .repoName(UPDATED_REPO_NAME)
            .repoUuid(UPDATED_REPO_UUID)
            .repoFolder(UPDATED_REPO_FOLDER)
            .repoPath(UPDATED_REPO_PATH)
            .thumbnail(UPDATED_THUMBNAIL)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataExclusao(UPDATED_DATA_EXCLUSAO)
            .tamanhoBytes(UPDATED_TAMANHO_BYTES);
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(updatedMediaFile);

        restMediaFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mediaFileDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isOk());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeUpdate);
        MediaFile testMediaFile = mediaFileList.get(mediaFileList.size() - 1);
        assertThat(testMediaFile.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);
        assertThat(testMediaFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testMediaFile.getFileExtension()).isEqualTo(UPDATED_FILE_EXTENSION);
        assertThat(testMediaFile.getMediaTitle()).isEqualTo(UPDATED_MEDIA_TITLE);
        assertThat(testMediaFile.getMediaDescription()).isEqualTo(UPDATED_MEDIA_DESCRIPTION);
        assertThat(testMediaFile.getPublico()).isEqualTo(UPDATED_PUBLICO);
        assertThat(testMediaFile.getRepoName()).isEqualTo(UPDATED_REPO_NAME);
        assertThat(testMediaFile.getRepoUuid()).isEqualTo(UPDATED_REPO_UUID);
        assertThat(testMediaFile.getRepoFolder()).isEqualTo(UPDATED_REPO_FOLDER);
        assertThat(testMediaFile.getRepoPath()).isEqualTo(UPDATED_REPO_PATH);
        assertThat(testMediaFile.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testMediaFile.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testMediaFile.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testMediaFile.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testMediaFile.getDataExclusao()).isEqualTo(UPDATED_DATA_EXCLUSAO);
        assertThat(testMediaFile.getTamanhoBytes()).isEqualTo(UPDATED_TAMANHO_BYTES);
    }

    @Test
    @Transactional
    void putNonExistingMediaFile() throws Exception {
        int databaseSizeBeforeUpdate = mediaFileRepository.findAll().size();
        mediaFile.setId(count.incrementAndGet());

        // Create the MediaFile
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMediaFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mediaFileDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMediaFile() throws Exception {
        int databaseSizeBeforeUpdate = mediaFileRepository.findAll().size();
        mediaFile.setId(count.incrementAndGet());

        // Create the MediaFile
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMediaFile() throws Exception {
        int databaseSizeBeforeUpdate = mediaFileRepository.findAll().size();
        mediaFile.setId(count.incrementAndGet());

        // Create the MediaFile
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaFileMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMediaFileWithPatch() throws Exception {
        // Initialize the database
        mediaFileRepository.saveAndFlush(mediaFile);

        int databaseSizeBeforeUpdate = mediaFileRepository.findAll().size();

        // Update the mediaFile using partial update
        MediaFile partialUpdatedMediaFile = new MediaFile();
        partialUpdatedMediaFile.setId(mediaFile.getId());

        partialUpdatedMediaFile
            .mediaType(UPDATED_MEDIA_TYPE)
            .mediaTitle(UPDATED_MEDIA_TITLE)
            .mediaDescription(UPDATED_MEDIA_DESCRIPTION)
            .repoName(UPDATED_REPO_NAME)
            .height(UPDATED_HEIGHT)
            .dataExclusao(UPDATED_DATA_EXCLUSAO)
            .tamanhoBytes(UPDATED_TAMANHO_BYTES);

        restMediaFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMediaFile.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMediaFile))
            )
            .andExpect(status().isOk());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeUpdate);
        MediaFile testMediaFile = mediaFileList.get(mediaFileList.size() - 1);
        assertThat(testMediaFile.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);
        assertThat(testMediaFile.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testMediaFile.getFileExtension()).isEqualTo(DEFAULT_FILE_EXTENSION);
        assertThat(testMediaFile.getMediaTitle()).isEqualTo(UPDATED_MEDIA_TITLE);
        assertThat(testMediaFile.getMediaDescription()).isEqualTo(UPDATED_MEDIA_DESCRIPTION);
        assertThat(testMediaFile.getPublico()).isEqualTo(DEFAULT_PUBLICO);
        assertThat(testMediaFile.getRepoName()).isEqualTo(UPDATED_REPO_NAME);
        assertThat(testMediaFile.getRepoUuid()).isEqualTo(DEFAULT_REPO_UUID);
        assertThat(testMediaFile.getRepoFolder()).isEqualTo(DEFAULT_REPO_FOLDER);
        assertThat(testMediaFile.getRepoPath()).isEqualTo(DEFAULT_REPO_PATH);
        assertThat(testMediaFile.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testMediaFile.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testMediaFile.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testMediaFile.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testMediaFile.getDataExclusao()).isEqualTo(UPDATED_DATA_EXCLUSAO);
        assertThat(testMediaFile.getTamanhoBytes()).isEqualTo(UPDATED_TAMANHO_BYTES);
    }

    @Test
    @Transactional
    void fullUpdateMediaFileWithPatch() throws Exception {
        // Initialize the database
        mediaFileRepository.saveAndFlush(mediaFile);

        int databaseSizeBeforeUpdate = mediaFileRepository.findAll().size();

        // Update the mediaFile using partial update
        MediaFile partialUpdatedMediaFile = new MediaFile();
        partialUpdatedMediaFile.setId(mediaFile.getId());

        partialUpdatedMediaFile
            .mediaType(UPDATED_MEDIA_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .mediaTitle(UPDATED_MEDIA_TITLE)
            .mediaDescription(UPDATED_MEDIA_DESCRIPTION)
            .publico(UPDATED_PUBLICO)
            .repoName(UPDATED_REPO_NAME)
            .repoUuid(UPDATED_REPO_UUID)
            .repoFolder(UPDATED_REPO_FOLDER)
            .repoPath(UPDATED_REPO_PATH)
            .thumbnail(UPDATED_THUMBNAIL)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataExclusao(UPDATED_DATA_EXCLUSAO)
            .tamanhoBytes(UPDATED_TAMANHO_BYTES);

        restMediaFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMediaFile.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMediaFile))
            )
            .andExpect(status().isOk());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeUpdate);
        MediaFile testMediaFile = mediaFileList.get(mediaFileList.size() - 1);
        assertThat(testMediaFile.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);
        assertThat(testMediaFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testMediaFile.getFileExtension()).isEqualTo(UPDATED_FILE_EXTENSION);
        assertThat(testMediaFile.getMediaTitle()).isEqualTo(UPDATED_MEDIA_TITLE);
        assertThat(testMediaFile.getMediaDescription()).isEqualTo(UPDATED_MEDIA_DESCRIPTION);
        assertThat(testMediaFile.getPublico()).isEqualTo(UPDATED_PUBLICO);
        assertThat(testMediaFile.getRepoName()).isEqualTo(UPDATED_REPO_NAME);
        assertThat(testMediaFile.getRepoUuid()).isEqualTo(UPDATED_REPO_UUID);
        assertThat(testMediaFile.getRepoFolder()).isEqualTo(UPDATED_REPO_FOLDER);
        assertThat(testMediaFile.getRepoPath()).isEqualTo(UPDATED_REPO_PATH);
        assertThat(testMediaFile.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testMediaFile.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testMediaFile.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testMediaFile.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testMediaFile.getDataExclusao()).isEqualTo(UPDATED_DATA_EXCLUSAO);
        assertThat(testMediaFile.getTamanhoBytes()).isEqualTo(UPDATED_TAMANHO_BYTES);
    }

    @Test
    @Transactional
    void patchNonExistingMediaFile() throws Exception {
        int databaseSizeBeforeUpdate = mediaFileRepository.findAll().size();
        mediaFile.setId(count.incrementAndGet());

        // Create the MediaFile
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMediaFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mediaFileDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMediaFile() throws Exception {
        int databaseSizeBeforeUpdate = mediaFileRepository.findAll().size();
        mediaFile.setId(count.incrementAndGet());

        // Create the MediaFile
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMediaFile() throws Exception {
        int databaseSizeBeforeUpdate = mediaFileRepository.findAll().size();
        mediaFile.setId(count.incrementAndGet());

        // Create the MediaFile
        MediaFileDTO mediaFileDTO = mediaFileMapper.toDto(mediaFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaFileMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mediaFileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMediaFile() throws Exception {
        // Initialize the database
        mediaFileRepository.saveAndFlush(mediaFile);

        int databaseSizeBeforeDelete = mediaFileRepository.findAll().size();

        // Delete the mediaFile
        restMediaFileMockMvc
            .perform(delete(ENTITY_API_URL_ID, mediaFile.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
