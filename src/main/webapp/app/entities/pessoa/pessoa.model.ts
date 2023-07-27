import dayjs from 'dayjs/esm';
import { TipoSanguineo } from 'app/entities/enumerations/tipo-sanguineo.model';
import { Sexo } from 'app/entities/enumerations/sexo.model';
import { TipoPessoa } from 'app/entities/enumerations/tipo-pessoa.model';
import { EstadoCivil } from 'app/entities/enumerations/estado-civil.model';

export interface IPessoa {
  id?: number;
  dataRegistro?: dayjs.Dayjs;
  nome?: string;
  nomeSocial?: string | null;
  possuiNomeSocial?: boolean | null;
  apelidoNomeFantasia?: string | null;
  dataNascimento?: dayjs.Dayjs | null;
  nomePai?: string | null;
  nomeMae?: string | null;
  tipoSanguineo?: TipoSanguineo | null;
  sexoBiologicoAoNascer?: Sexo | null;
  tipoPessoa?: TipoPessoa;
  cpf?: string | null;
  cnpj?: string | null;
  rg?: string | null;
  ie?: string | null;
  estadoCivil?: EstadoCivil | null;
  observacoes?: string | null;
  naturalidade?: string | null;
  raca?: string | null;
}

export class Pessoa implements IPessoa {
  constructor(
    public id?: number,
    public dataRegistro?: dayjs.Dayjs,
    public nome?: string,
    public nomeSocial?: string | null,
    public possuiNomeSocial?: boolean | null,
    public apelidoNomeFantasia?: string | null,
    public dataNascimento?: dayjs.Dayjs | null,
    public nomePai?: string | null,
    public nomeMae?: string | null,
    public tipoSanguineo?: TipoSanguineo | null,
    public sexoBiologicoAoNascer?: Sexo | null,
    public tipoPessoa?: TipoPessoa,
    public cpf?: string | null,
    public cnpj?: string | null,
    public rg?: string | null,
    public ie?: string | null,
    public estadoCivil?: EstadoCivil | null,
    public observacoes?: string | null,
    public naturalidade?: string | null,
    public raca?: string | null
  ) {
    this.possuiNomeSocial = this.possuiNomeSocial ?? false;
  }
}

export function getPessoaIdentifier(pessoa: IPessoa): number | undefined {
  return pessoa.id;
}
