package br.com.elfotec.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.elfotec.domain.PessoaContato} entity.
 */
public class PessoaContatoDTO implements Serializable {

    private Long id;

    /**
     * default current_date, replicando do histórico
     */
    @NotNull
    @Schema(description = "default current_date, replicando do histórico", required = true)
    private Instant dataRegistro;

    private Instant dataImportacao;

    private Instant dataExclusao;

    private String descricao;

    /**
     * identificação na Rede Social, exemplo \"superlauro\"
     */
    @Schema(description = "identificação na Rede Social, exemplo \"superlauro\"")
    private String contatoDigitalIdent;

    /**
     * pode ter o simbolo 'plus', ddi, ddd alem do numero
     */
    @Schema(description = "pode ter o simbolo 'plus', ddi, ddd alem do numero")
    private String telefoneNumeroCompleto;

    /**
     * Caso o contato seja um número de telefone
     */
    @Schema(description = "Caso o contato seja um número de telefone")
    private Integer telefoneDdd;

    /**
     * Caso o contato seja um número de telefone
     */
    @Schema(description = "Caso o contato seja um número de telefone")
    private Long telefoneNumero;

    @NotNull
    private Boolean preferido;

    @NotNull
    private Boolean receberPropagandas;

    @NotNull
    private Boolean receberConfirmacoes;

    @NotNull
    private Boolean possuiWhatsapp;

    private PessoaDTO contato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Instant getDataImportacao() {
        return dataImportacao;
    }

    public void setDataImportacao(Instant dataImportacao) {
        this.dataImportacao = dataImportacao;
    }

    public Instant getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(Instant dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getContatoDigitalIdent() {
        return contatoDigitalIdent;
    }

    public void setContatoDigitalIdent(String contatoDigitalIdent) {
        this.contatoDigitalIdent = contatoDigitalIdent;
    }

    public String getTelefoneNumeroCompleto() {
        return telefoneNumeroCompleto;
    }

    public void setTelefoneNumeroCompleto(String telefoneNumeroCompleto) {
        this.telefoneNumeroCompleto = telefoneNumeroCompleto;
    }

    public Integer getTelefoneDdd() {
        return telefoneDdd;
    }

    public void setTelefoneDdd(Integer telefoneDdd) {
        this.telefoneDdd = telefoneDdd;
    }

    public Long getTelefoneNumero() {
        return telefoneNumero;
    }

    public void setTelefoneNumero(Long telefoneNumero) {
        this.telefoneNumero = telefoneNumero;
    }

    public Boolean getPreferido() {
        return preferido;
    }

    public void setPreferido(Boolean preferido) {
        this.preferido = preferido;
    }

    public Boolean getReceberPropagandas() {
        return receberPropagandas;
    }

    public void setReceberPropagandas(Boolean receberPropagandas) {
        this.receberPropagandas = receberPropagandas;
    }

    public Boolean getReceberConfirmacoes() {
        return receberConfirmacoes;
    }

    public void setReceberConfirmacoes(Boolean receberConfirmacoes) {
        this.receberConfirmacoes = receberConfirmacoes;
    }

    public Boolean getPossuiWhatsapp() {
        return possuiWhatsapp;
    }

    public void setPossuiWhatsapp(Boolean possuiWhatsapp) {
        this.possuiWhatsapp = possuiWhatsapp;
    }

    public PessoaDTO getContato() {
        return contato;
    }

    public void setContato(PessoaDTO contato) {
        this.contato = contato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PessoaContatoDTO)) {
            return false;
        }

        PessoaContatoDTO pessoaContatoDTO = (PessoaContatoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pessoaContatoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaContatoDTO{" +
            "id=" + getId() +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", dataImportacao='" + getDataImportacao() + "'" +
            ", dataExclusao='" + getDataExclusao() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", contatoDigitalIdent='" + getContatoDigitalIdent() + "'" +
            ", telefoneNumeroCompleto='" + getTelefoneNumeroCompleto() + "'" +
            ", telefoneDdd=" + getTelefoneDdd() +
            ", telefoneNumero=" + getTelefoneNumero() +
            ", preferido='" + getPreferido() + "'" +
            ", receberPropagandas='" + getReceberPropagandas() + "'" +
            ", receberConfirmacoes='" + getReceberConfirmacoes() + "'" +
            ", possuiWhatsapp='" + getPossuiWhatsapp() + "'" +
            ", contato=" + getContato() +
            "}";
    }
}
