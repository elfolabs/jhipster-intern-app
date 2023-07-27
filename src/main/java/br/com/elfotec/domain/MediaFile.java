package br.com.elfotec.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MediaFile.
 */
@Entity
@Table(name = "media_file")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MediaFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * Refs: www.iana.org/assignments/media-types/media-types.xhtml
     */
    @NotNull
    @Column(name = "media_type", nullable = false)
    private String mediaType;

    /**
     * Nome do arquivo
     */
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "media_title")
    private String mediaTitle;

    @Column(name = "media_description")
    private String mediaDescription;

    /**
     * default false
     */
    @NotNull
    @Column(name = "publico", nullable = false)
    private Boolean publico;

    /**
     * Nome do Bucket do repositório S3
     */
    @NotNull
    @Column(name = "repo_name", nullable = false)
    private String repoName;

    /**
     * UUID no repositório S3
     */
    @NotNull
    @Column(name = "repo_uuid", nullable = false)
    private String repoUuid;

    /**
     * Folder de destino no S3
     */
    @NotNull
    @Column(name = "repo_folder", nullable = false)
    private String repoFolder;

    /**
     * path completo com folder de destino e nome do arquivo no repositorio
     */
    @Column(name = "repo_path")
    private String repoPath;

    @NotNull
    @Column(name = "thumbnail", nullable = false)
    private Boolean thumbnail;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @NotNull
    @Column(name = "data_registro", nullable = false)
    private Instant dataRegistro;

    @Column(name = "data_exclusao")
    private Instant dataExclusao;

    @Column(name = "tamanho_bytes")
    private Integer tamanhoBytes;

    @ManyToOne
    private Pessoa fotoRosto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MediaFile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMediaType() {
        return this.mediaType;
    }

    public MediaFile mediaType(String mediaType) {
        this.setMediaType(mediaType);
        return this;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public MediaFile fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public MediaFile fileExtension(String fileExtension) {
        this.setFileExtension(fileExtension);
        return this;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getMediaTitle() {
        return this.mediaTitle;
    }

    public MediaFile mediaTitle(String mediaTitle) {
        this.setMediaTitle(mediaTitle);
        return this;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    public String getMediaDescription() {
        return this.mediaDescription;
    }

    public MediaFile mediaDescription(String mediaDescription) {
        this.setMediaDescription(mediaDescription);
        return this;
    }

    public void setMediaDescription(String mediaDescription) {
        this.mediaDescription = mediaDescription;
    }

    public Boolean getPublico() {
        return this.publico;
    }

    public MediaFile publico(Boolean publico) {
        this.setPublico(publico);
        return this;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }

    public String getRepoName() {
        return this.repoName;
    }

    public MediaFile repoName(String repoName) {
        this.setRepoName(repoName);
        return this;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoUuid() {
        return this.repoUuid;
    }

    public MediaFile repoUuid(String repoUuid) {
        this.setRepoUuid(repoUuid);
        return this;
    }

    public void setRepoUuid(String repoUuid) {
        this.repoUuid = repoUuid;
    }

    public String getRepoFolder() {
        return this.repoFolder;
    }

    public MediaFile repoFolder(String repoFolder) {
        this.setRepoFolder(repoFolder);
        return this;
    }

    public void setRepoFolder(String repoFolder) {
        this.repoFolder = repoFolder;
    }

    public String getRepoPath() {
        return this.repoPath;
    }

    public MediaFile repoPath(String repoPath) {
        this.setRepoPath(repoPath);
        return this;
    }

    public void setRepoPath(String repoPath) {
        this.repoPath = repoPath;
    }

    public Boolean getThumbnail() {
        return this.thumbnail;
    }

    public MediaFile thumbnail(Boolean thumbnail) {
        this.setThumbnail(thumbnail);
        return this;
    }

    public void setThumbnail(Boolean thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getWidth() {
        return this.width;
    }

    public MediaFile width(Integer width) {
        this.setWidth(width);
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public MediaFile height(Integer height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Instant getDataRegistro() {
        return this.dataRegistro;
    }

    public MediaFile dataRegistro(Instant dataRegistro) {
        this.setDataRegistro(dataRegistro);
        return this;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Instant getDataExclusao() {
        return this.dataExclusao;
    }

    public MediaFile dataExclusao(Instant dataExclusao) {
        this.setDataExclusao(dataExclusao);
        return this;
    }

    public void setDataExclusao(Instant dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public Integer getTamanhoBytes() {
        return this.tamanhoBytes;
    }

    public MediaFile tamanhoBytes(Integer tamanhoBytes) {
        this.setTamanhoBytes(tamanhoBytes);
        return this;
    }

    public void setTamanhoBytes(Integer tamanhoBytes) {
        this.tamanhoBytes = tamanhoBytes;
    }

    public Pessoa getFotoRosto() {
        return this.fotoRosto;
    }

    public void setFotoRosto(Pessoa pessoa) {
        this.fotoRosto = pessoa;
    }

    public MediaFile fotoRosto(Pessoa pessoa) {
        this.setFotoRosto(pessoa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MediaFile)) {
            return false;
        }
        return id != null && id.equals(((MediaFile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MediaFile{" +
            "id=" + getId() +
            ", mediaType='" + getMediaType() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fileExtension='" + getFileExtension() + "'" +
            ", mediaTitle='" + getMediaTitle() + "'" +
            ", mediaDescription='" + getMediaDescription() + "'" +
            ", publico='" + getPublico() + "'" +
            ", repoName='" + getRepoName() + "'" +
            ", repoUuid='" + getRepoUuid() + "'" +
            ", repoFolder='" + getRepoFolder() + "'" +
            ", repoPath='" + getRepoPath() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", dataExclusao='" + getDataExclusao() + "'" +
            ", tamanhoBytes=" + getTamanhoBytes() +
            "}";
    }
}
