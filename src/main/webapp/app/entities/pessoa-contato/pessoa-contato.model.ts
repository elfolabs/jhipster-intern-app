import dayjs from 'dayjs/esm';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';

export interface IPessoaContato {
  id?: number;
  dataRegistro?: dayjs.Dayjs;
  dataImportacao?: dayjs.Dayjs | null;
  dataExclusao?: dayjs.Dayjs | null;
  descricao?: string | null;
  contatoDigitalIdent?: string | null;
  telefoneNumeroCompleto?: string | null;
  telefoneDdd?: number | null;
  telefoneNumero?: number | null;
  preferido?: boolean;
  receberPropagandas?: boolean;
  receberConfirmacoes?: boolean;
  possuiWhatsapp?: boolean;
  contato?: IPessoa | null;
}

export class PessoaContato implements IPessoaContato {
  constructor(
    public id?: number,
    public dataRegistro?: dayjs.Dayjs,
    public dataImportacao?: dayjs.Dayjs | null,
    public dataExclusao?: dayjs.Dayjs | null,
    public descricao?: string | null,
    public contatoDigitalIdent?: string | null,
    public telefoneNumeroCompleto?: string | null,
    public telefoneDdd?: number | null,
    public telefoneNumero?: number | null,
    public preferido?: boolean,
    public receberPropagandas?: boolean,
    public receberConfirmacoes?: boolean,
    public possuiWhatsapp?: boolean,
    public contato?: IPessoa | null
  ) {
    this.preferido = this.preferido ?? false;
    this.receberPropagandas = this.receberPropagandas ?? false;
    this.receberConfirmacoes = this.receberConfirmacoes ?? false;
    this.possuiWhatsapp = this.possuiWhatsapp ?? false;
  }
}

export function getPessoaContatoIdentifier(pessoaContato: IPessoaContato): number | undefined {
  return pessoaContato.id;
}
