package br.com.elfotec.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.elfotec.domain.MediaFile} entity.
 */
public class MediaFileDTO implements Serializable {

    private Long id;

    /**
     * Refs: www.iana.org/assignments/media-types/media-types.xhtml
     */
    @NotNull
    @Schema(description = "Refs: www.iana.org/assignments/media-types/media-types.xhtml", required = true)
    private String mediaType;

    /**
     * Nome do arquivo
     */
    @Schema(description = "Nome do arquivo")
    private String fileName;

    private String fileExtension;

    private String mediaTitle;

    private String mediaDescription;

    /**
     * default false
     */
    @NotNull
    @Schema(description = "default false", required = true)
    private Boolean publico;

    /**
     * Nome do Bucket do repositório S3
     */
    @NotNull
    @Schema(description = "Nome do Bucket do repositório S3", required = true)
    private String repoName;

    /**
     * UUID no repositório S3
     */
    @NotNull
    @Schema(description = "UUID no repositório S3", required = true)
    private String repoUuid;

    /**
     * Folder de destino no S3
     */
    @NotNull
    @Schema(description = "Folder de destino no S3", required = true)
    private String repoFolder;

    /**
     * path completo com folder de destino e nome do arquivo no repositorio
     */
    @Schema(description = "path completo com folder de destino e nome do arquivo no repositorio")
    private String repoPath;

    @NotNull
    private Boolean thumbnail;

    private Integer width;

    private Integer height;

    @NotNull
    private Instant dataRegistro;

    private Instant dataExclusao;

    private Integer tamanhoBytes;

    private PessoaDTO fotoRosto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    public String getMediaDescription() {
        return mediaDescription;
    }

    public void setMediaDescription(String mediaDescription) {
        this.mediaDescription = mediaDescription;
    }

    public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoUuid() {
        return repoUuid;
    }

    public void setRepoUuid(String repoUuid) {
        this.repoUuid = repoUuid;
    }

    public String getRepoFolder() {
        return repoFolder;
    }

    public void setRepoFolder(String repoFolder) {
        this.repoFolder = repoFolder;
    }

    public String getRepoPath() {
        return repoPath;
    }

    public void setRepoPath(String repoPath) {
        this.repoPath = repoPath;
    }

    public Boolean getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Boolean thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Instant getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Instant getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(Instant dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public Integer getTamanhoBytes() {
        return tamanhoBytes;
    }

    public void setTamanhoBytes(Integer tamanhoBytes) {
        this.tamanhoBytes = tamanhoBytes;
    }

    public PessoaDTO getFotoRosto() {
        return fotoRosto;
    }

    public void setFotoRosto(PessoaDTO fotoRosto) {
        this.fotoRosto = fotoRosto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MediaFileDTO)) {
            return false;
        }

        MediaFileDTO mediaFileDTO = (MediaFileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mediaFileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MediaFileDTO{" +
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
            ", fotoRosto=" + getFotoRosto() +
            "}";
    }
}
