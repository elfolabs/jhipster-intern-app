package br.com.elfotec.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PessoaContato.
 */
@Entity
@Table(name = "pessoa_contato")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PessoaContato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * default current_date, replicando do histórico
     */
    @NotNull
    @Column(name = "data_registro", nullable = false)
    private Instant dataRegistro;

    @Column(name = "data_importacao")
    private Instant dataImportacao;

    @Column(name = "data_exclusao")
    private Instant dataExclusao;

    @Column(name = "descricao")
    private String descricao;

    /**
     * identificação na Rede Social, exemplo \"superlauro\"
     */
    @Column(name = "contato_digital_ident")
    private String contatoDigitalIdent;

    /**
     * pode ter o simbolo 'plus', ddi, ddd alem do numero
     */
    @Column(name = "telefone_numero_completo")
    private String telefoneNumeroCompleto;

    /**
     * Caso o contato seja um número de telefone
     */
    @Column(name = "telefone_ddd")
    private Integer telefoneDdd;

    @Column(name = "telefone_ddi")
    private Integer telefoneDdi;

    /**
     * Caso o contato seja um número de telefone
     */
    @Column(name = "telefone_numero")
    private Long telefoneNumero;

    @NotNull
    @Column(name = "preferido", nullable = false)
    private Boolean preferido;

    @NotNull
    @Column(name = "receber_propagandas", nullable = false)
    private Boolean receberPropagandas;

    @NotNull
    @Column(name = "receber_confirmacoes", nullable = false)
    private Boolean receberConfirmacoes;

    @NotNull
    @Column(name = "possui_whatsapp", nullable = false)
    private Boolean possuiWhatsapp;

    @ManyToOne
    private Pessoa contato;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PessoaContato id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataRegistro() {
        return this.dataRegistro;
    }

    public PessoaContato dataRegistro(Instant dataRegistro) {
        this.setDataRegistro(dataRegistro);
        return this;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Instant getDataImportacao() {
        return this.dataImportacao;
    }

    public PessoaContato dataImportacao(Instant dataImportacao) {
        this.setDataImportacao(dataImportacao);
        return this;
    }

    public void setDataImportacao(Instant dataImportacao) {
        this.dataImportacao = dataImportacao;
    }

    public Instant getDataExclusao() {
        return this.dataExclusao;
    }

    public PessoaContato dataExclusao(Instant dataExclusao) {
        this.setDataExclusao(dataExclusao);
        return this;
    }

    public void setDataExclusao(Instant dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public PessoaContato descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getContatoDigitalIdent() {
        return this.contatoDigitalIdent;
    }

    public PessoaContato contatoDigitalIdent(String contatoDigitalIdent) {
        this.setContatoDigitalIdent(contatoDigitalIdent);
        return this;
    }

    public void setContatoDigitalIdent(String contatoDigitalIdent) {
        this.contatoDigitalIdent = contatoDigitalIdent;
    }

    public String getTelefoneNumeroCompleto() {
        return this.telefoneNumeroCompleto;
    }

    public PessoaContato telefoneNumeroCompleto(String telefoneNumeroCompleto) {
        this.setTelefoneNumeroCompleto(telefoneNumeroCompleto);
        return this;
    }

    public void setTelefoneNumeroCompleto(String telefoneNumeroCompleto) {
        this.telefoneNumeroCompleto = telefoneNumeroCompleto;
    }

    public Integer getTelefoneDdd() {
        return this.telefoneDdd;
    }

    public PessoaContato telefoneDdd(Integer telefoneDdd) {
        this.setTelefoneDdd(telefoneDdd);
        return this;
    }

    public void setTelefoneDdd(Integer telefoneDdd) {
        this.telefoneDdd = telefoneDdd;
    }

    //
    public Integer getTelefoneDdi() {
        return this.telefoneDdi;
    }

    public PessoaContato telefoneDdi(Integer telefoneDdi) {
        this.setTelefoneDdi(telefoneDdi);
        return this;
    }

    public void setTelefoneDdi(Integer telefoneDdi) {
        this.telefoneDdi = telefoneDdi;
    }

    public Long getTelefoneNumero() {
        return this.telefoneNumero;
    }

    public PessoaContato telefoneNumero(Long telefoneNumero) {
        this.setTelefoneNumero(telefoneNumero);
        return this;
    }

    public void setTelefoneNumero(Long telefoneNumero) {
        this.telefoneNumero = telefoneNumero;
    }

    public Boolean getPreferido() {
        return this.preferido;
    }

    public PessoaContato preferido(Boolean preferido) {
        this.setPreferido(preferido);
        return this;
    }

    public void setPreferido(Boolean preferido) {
        this.preferido = preferido;
    }

    public Boolean getReceberPropagandas() {
        return this.receberPropagandas;
    }

    public PessoaContato receberPropagandas(Boolean receberPropagandas) {
        this.setReceberPropagandas(receberPropagandas);
        return this;
    }

    public void setReceberPropagandas(Boolean receberPropagandas) {
        this.receberPropagandas = receberPropagandas;
    }

    public Boolean getReceberConfirmacoes() {
        return this.receberConfirmacoes;
    }

    public PessoaContato receberConfirmacoes(Boolean receberConfirmacoes) {
        this.setReceberConfirmacoes(receberConfirmacoes);
        return this;
    }

    public void setReceberConfirmacoes(Boolean receberConfirmacoes) {
        this.receberConfirmacoes = receberConfirmacoes;
    }

    public Boolean getPossuiWhatsapp() {
        return this.possuiWhatsapp;
    }

    public PessoaContato possuiWhatsapp(Boolean possuiWhatsapp) {
        this.setPossuiWhatsapp(possuiWhatsapp);
        return this;
    }

    public void setPossuiWhatsapp(Boolean possuiWhatsapp) {
        this.possuiWhatsapp = possuiWhatsapp;
    }

    public Pessoa getContato() {
        return this.contato;
    }

    public void setContato(Pessoa pessoa) {
        this.contato = pessoa;
    }

    public PessoaContato contato(Pessoa pessoa) {
        this.setContato(pessoa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PessoaContato)) {
            return false;
        }
        return id != null && id.equals(((PessoaContato) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaContato{" +
            "id=" + getId() +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", dataImportacao='" + getDataImportacao() + "'" +
            ", dataExclusao='" + getDataExclusao() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", contatoDigitalIdent='" + getContatoDigitalIdent() + "'" +
            ", telefoneNumeroCompleto='" + getTelefoneNumeroCompleto() + "'" +
            ", telefoneDdd=" + getTelefoneDdd() +
            ", telefoneDdi=" + getTelefoneDdi() +
            ", telefoneNumero=" + getTelefoneNumero() +
            ", preferido='" + getPreferido() + "'" +
            ", receberPropagandas='" + getReceberPropagandas() + "'" +
            ", receberConfirmacoes='" + getReceberConfirmacoes() + "'" +
            ", possuiWhatsapp='" + getPossuiWhatsapp() + "'" +
            "}";
    }
}
