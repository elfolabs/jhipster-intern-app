import dayjs from 'dayjs/esm';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';

export interface IPessoaRedeSocial {
  id?: number;
  nomeRede?: string;
  endereco?: string | null;
  dataRegistro?: dayjs.Dayjs;
  dataExclusao?: dayjs.Dayjs | null;
  divulgarNoAplicativo?: boolean | null;
  redeSocial?: IPessoa | null;
}

export class PessoaRedeSocial implements IPessoaRedeSocial {
  constructor(
    public id?: number,
    public nomeRede?: string,
    public endereco?: string | null,
    public dataRegistro?: dayjs.Dayjs,
    public dataExclusao?: dayjs.Dayjs | null,
    public divulgarNoAplicativo?: boolean | null,
    public redeSocial?: IPessoa | null
  ) {
    this.divulgarNoAplicativo = this.divulgarNoAplicativo ?? false;
  }
}

export function getPessoaRedeSocialIdentifier(pessoaRedeSocial: IPessoaRedeSocial): number | undefined {
  return pessoaRedeSocial.id;
}
