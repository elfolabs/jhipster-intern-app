package br.com.elfotec.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PessoaRedeSocial.
 */
@Entity
@Table(name = "pessoa_rede_social")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PessoaRedeSocial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome_rede", nullable = false)
    private String nomeRede;

    @Size(max = 2000)
    @Column(name = "endereco", length = 2000)
    private String endereco;

    @NotNull
    @Column(name = "data_registro", nullable = false)
    private Instant dataRegistro;

    @Column(name = "data_exclusao")
    private Instant dataExclusao;

    @Column(name = "divulgar_no_aplicativo")
    private Boolean divulgarNoAplicativo;

    @ManyToOne
    private Pessoa redeSocial;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PessoaRedeSocial id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeRede() {
        return this.nomeRede;
    }

    public PessoaRedeSocial nomeRede(String nomeRede) {
        this.setNomeRede(nomeRede);
        return this;
    }

    public void setNomeRede(String nomeRede) {
        this.nomeRede = nomeRede;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public PessoaRedeSocial endereco(String endereco) {
        this.setEndereco(endereco);
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Instant getDataRegistro() {
        return this.dataRegistro;
    }

    public PessoaRedeSocial dataRegistro(Instant dataRegistro) {
        this.setDataRegistro(dataRegistro);
        return this;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Instant getDataExclusao() {
        return this.dataExclusao;
    }

    public PessoaRedeSocial dataExclusao(Instant dataExclusao) {
        this.setDataExclusao(dataExclusao);
        return this;
    }

    public void setDataExclusao(Instant dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public Boolean getDivulgarNoAplicativo() {
        return this.divulgarNoAplicativo;
    }

    public PessoaRedeSocial divulgarNoAplicativo(Boolean divulgarNoAplicativo) {
        this.setDivulgarNoAplicativo(divulgarNoAplicativo);
        return this;
    }

    public void setDivulgarNoAplicativo(Boolean divulgarNoAplicativo) {
        this.divulgarNoAplicativo = divulgarNoAplicativo;
    }

    public Pessoa getRedeSocial() {
        return this.redeSocial;
    }

    public void setRedeSocial(Pessoa pessoa) {
        this.redeSocial = pessoa;
    }

    public PessoaRedeSocial redeSocial(Pessoa pessoa) {
        this.setRedeSocial(pessoa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PessoaRedeSocial)) {
            return false;
        }
        return id != null && id.equals(((PessoaRedeSocial) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaRedeSocial{" +
            "id=" + getId() +
            ", nomeRede='" + getNomeRede() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", dataExclusao='" + getDataExclusao() + "'" +
            ", divulgarNoAplicativo='" + getDivulgarNoAplicativo() + "'" +
            "}";
    }
}
