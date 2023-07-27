package br.com.elfotec.domain;

import br.com.elfotec.domain.enumeration.EstadoCivil;
import br.com.elfotec.domain.enumeration.Sexo;
import br.com.elfotec.domain.enumeration.TipoPessoa;
import br.com.elfotec.domain.enumeration.TipoSanguineo;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * Pessoas podem ser PJ ou PF\nApenas PF podem ser Atendidas\nMas um cadastro unificado incluindo PJ servirá para o módulo financeiro\nInclusive os profissionais são PF neste cadastro
 */
@Entity
@Table(name = "pessoa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pessoa implements Serializable {

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

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "nome_social")
    private String nomeSocial;

    @Column(name = "possui_nome_social")
    private Boolean possuiNomeSocial;

    /**
     * Valor preferido em todas as telas, exceto a emissão de documentos médicos
     */
    @Column(name = "apelido_nome_fantasia")
    private String apelidoNomeFantasia;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "nome_pai")
    private String nomePai;

    @Column(name = "nome_mae")
    private String nomeMae;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_sanguineo")
    private TipoSanguineo tipoSanguineo;

    /**
     * Obrigatorio para TipoPessoa::PESSOA_FISICA, valor default F
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo_biologico_ao_nascer")
    private Sexo sexoBiologicoAoNascer;

    /**
     * default PF
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa", nullable = false)
    private TipoPessoa tipoPessoa;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "cnpj")
    private String cnpj;

    /**
     * RG Caso TipoPessoa::PESSOA_FISICA, e Inscrição Estadual em caso contrário
     */
    @Column(name = "rg")
    private String rg;

    /**
     * Inscricao estadual caso TipoPessoa::PESSOA_JURIDCA
     */
    @Column(name = "ie")
    private String ie;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil")
    private EstadoCivil estadoCivil;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "naturalidade")
    private String naturalidade;

    @Column(name = "raca")
    private String raca;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pessoa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataRegistro() {
        return this.dataRegistro;
    }

    public Pessoa dataRegistro(Instant dataRegistro) {
        this.setDataRegistro(dataRegistro);
        return this;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getNome() {
        return this.nome;
    }

    public Pessoa nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeSocial() {
        return this.nomeSocial;
    }

    public Pessoa nomeSocial(String nomeSocial) {
        this.setNomeSocial(nomeSocial);
        return this;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public Boolean getPossuiNomeSocial() {
        return this.possuiNomeSocial;
    }

    public Pessoa possuiNomeSocial(Boolean possuiNomeSocial) {
        this.setPossuiNomeSocial(possuiNomeSocial);
        return this;
    }

    public void setPossuiNomeSocial(Boolean possuiNomeSocial) {
        this.possuiNomeSocial = possuiNomeSocial;
    }

    public String getApelidoNomeFantasia() {
        return this.apelidoNomeFantasia;
    }

    public Pessoa apelidoNomeFantasia(String apelidoNomeFantasia) {
        this.setApelidoNomeFantasia(apelidoNomeFantasia);
        return this;
    }

    public void setApelidoNomeFantasia(String apelidoNomeFantasia) {
        this.apelidoNomeFantasia = apelidoNomeFantasia;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public Pessoa dataNascimento(LocalDate dataNascimento) {
        this.setDataNascimento(dataNascimento);
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomePai() {
        return this.nomePai;
    }

    public Pessoa nomePai(String nomePai) {
        this.setNomePai(nomePai);
        return this;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return this.nomeMae;
    }

    public Pessoa nomeMae(String nomeMae) {
        this.setNomeMae(nomeMae);
        return this;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public TipoSanguineo getTipoSanguineo() {
        return this.tipoSanguineo;
    }

    public Pessoa tipoSanguineo(TipoSanguineo tipoSanguineo) {
        this.setTipoSanguineo(tipoSanguineo);
        return this;
    }

    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public Sexo getSexoBiologicoAoNascer() {
        return this.sexoBiologicoAoNascer;
    }

    public Pessoa sexoBiologicoAoNascer(Sexo sexoBiologicoAoNascer) {
        this.setSexoBiologicoAoNascer(sexoBiologicoAoNascer);
        return this;
    }

    public void setSexoBiologicoAoNascer(Sexo sexoBiologicoAoNascer) {
        this.sexoBiologicoAoNascer = sexoBiologicoAoNascer;
    }

    public TipoPessoa getTipoPessoa() {
        return this.tipoPessoa;
    }

    public Pessoa tipoPessoa(TipoPessoa tipoPessoa) {
        this.setTipoPessoa(tipoPessoa);
        return this;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Pessoa cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public Pessoa cnpj(String cnpj) {
        this.setCnpj(cnpj);
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRg() {
        return this.rg;
    }

    public Pessoa rg(String rg) {
        this.setRg(rg);
        return this;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getIe() {
        return this.ie;
    }

    public Pessoa ie(String ie) {
        this.setIe(ie);
        return this;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public EstadoCivil getEstadoCivil() {
        return this.estadoCivil;
    }

    public Pessoa estadoCivil(EstadoCivil estadoCivil) {
        this.setEstadoCivil(estadoCivil);
        return this;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public Pessoa observacoes(String observacoes) {
        this.setObservacoes(observacoes);
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getNaturalidade() {
        return this.naturalidade;
    }

    public Pessoa naturalidade(String naturalidade) {
        this.setNaturalidade(naturalidade);
        return this;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getRaca() {
        return this.raca;
    }

    public Pessoa raca(String raca) {
        this.setRaca(raca);
        return this;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pessoa)) {
            return false;
        }
        return id != null && id.equals(((Pessoa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pessoa{" +
            "id=" + getId() +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", nome='" + getNome() + "'" +
            ", nomeSocial='" + getNomeSocial() + "'" +
            ", possuiNomeSocial='" + getPossuiNomeSocial() + "'" +
            ", apelidoNomeFantasia='" + getApelidoNomeFantasia() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", nomePai='" + getNomePai() + "'" +
            ", nomeMae='" + getNomeMae() + "'" +
            ", tipoSanguineo='" + getTipoSanguineo() + "'" +
            ", sexoBiologicoAoNascer='" + getSexoBiologicoAoNascer() + "'" +
            ", tipoPessoa='" + getTipoPessoa() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", rg='" + getRg() + "'" +
            ", ie='" + getIe() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", naturalidade='" + getNaturalidade() + "'" +
            ", raca='" + getRaca() + "'" +
            "}";
    }
}
