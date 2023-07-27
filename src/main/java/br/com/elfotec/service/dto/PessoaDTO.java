package br.com.elfotec.service.dto;

import br.com.elfotec.domain.enumeration.EstadoCivil;
import br.com.elfotec.domain.enumeration.Sexo;
import br.com.elfotec.domain.enumeration.TipoPessoa;
import br.com.elfotec.domain.enumeration.TipoSanguineo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.elfotec.domain.Pessoa} entity.
 */
@Schema(
    description = "Pessoas podem ser PJ ou PF\nApenas PF podem ser Atendidas\nMas um cadastro unificado incluindo PJ servirá para o módulo financeiro\nInclusive os profissionais são PF neste cadastro"
)
public class PessoaDTO implements Serializable {

    private Long id;

    /**
     * default current_date, replicando do histórico
     */
    @NotNull
    @Schema(description = "default current_date, replicando do histórico", required = true)
    private Instant dataRegistro;

    @NotNull
    private String nome;

    private String nomeSocial;

    private Boolean possuiNomeSocial;

    /**
     * Valor preferido em todas as telas, exceto a emissão de documentos médicos
     */
    @Schema(description = "Valor preferido em todas as telas, exceto a emissão de documentos médicos")
    private String apelidoNomeFantasia;

    private LocalDate dataNascimento;

    private String nomePai;

    private String nomeMae;

    private TipoSanguineo tipoSanguineo;

    /**
     * Obrigatorio para TipoPessoa::PESSOA_FISICA, valor default F
     */
    @Schema(description = "Obrigatorio para TipoPessoa::PESSOA_FISICA, valor default F")
    private Sexo sexoBiologicoAoNascer;

    /**
     * default PF
     */
    @NotNull
    @Schema(description = "default PF", required = true)
    private TipoPessoa tipoPessoa;

    private String cpf;

    private String cnpj;

    /**
     * RG Caso TipoPessoa::PESSOA_FISICA, e Inscrição Estadual em caso contrário
     */
    @Schema(description = "RG Caso TipoPessoa::PESSOA_FISICA, e Inscrição Estadual em caso contrário")
    private String rg;

    /**
     * Inscricao estadual caso TipoPessoa::PESSOA_JURIDCA
     */
    @Schema(description = "Inscricao estadual caso TipoPessoa::PESSOA_JURIDCA")
    private String ie;

    private EstadoCivil estadoCivil;

    @Lob
    private String observacoes;

    private String naturalidade;

    private String raca;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public Boolean getPossuiNomeSocial() {
        return possuiNomeSocial;
    }

    public void setPossuiNomeSocial(Boolean possuiNomeSocial) {
        this.possuiNomeSocial = possuiNomeSocial;
    }

    public String getApelidoNomeFantasia() {
        return apelidoNomeFantasia;
    }

    public void setApelidoNomeFantasia(String apelidoNomeFantasia) {
        this.apelidoNomeFantasia = apelidoNomeFantasia;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public TipoSanguineo getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public Sexo getSexoBiologicoAoNascer() {
        return sexoBiologicoAoNascer;
    }

    public void setSexoBiologicoAoNascer(Sexo sexoBiologicoAoNascer) {
        this.sexoBiologicoAoNascer = sexoBiologicoAoNascer;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PessoaDTO)) {
            return false;
        }

        PessoaDTO pessoaDTO = (PessoaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pessoaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaDTO{" +
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
