package br.com.elfotec.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.elfotec.domain.PessoaRedeSocial} entity.
 */
public class PessoaRedeSocialDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomeRede;

    @Size(max = 2000)
    private String endereco;

    @NotNull
    private Instant dataRegistro;

    private Instant dataExclusao;

    private Boolean divulgarNoAplicativo;

    private PessoaDTO redeSocial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeRede() {
        return nomeRede;
    }

    public void setNomeRede(String nomeRede) {
        this.nomeRede = nomeRede;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    public Boolean getDivulgarNoAplicativo() {
        return divulgarNoAplicativo;
    }

    public void setDivulgarNoAplicativo(Boolean divulgarNoAplicativo) {
        this.divulgarNoAplicativo = divulgarNoAplicativo;
    }

    public PessoaDTO getRedeSocial() {
        return redeSocial;
    }

    public void setRedeSocial(PessoaDTO redeSocial) {
        this.redeSocial = redeSocial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PessoaRedeSocialDTO)) {
            return false;
        }

        PessoaRedeSocialDTO pessoaRedeSocialDTO = (PessoaRedeSocialDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pessoaRedeSocialDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaRedeSocialDTO{" +
            "id=" + getId() +
            ", nomeRede='" + getNomeRede() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", dataExclusao='" + getDataExclusao() + "'" +
            ", divulgarNoAplicativo='" + getDivulgarNoAplicativo() + "'" +
            ", redeSocial=" + getRedeSocial() +
            "}";
    }
}
