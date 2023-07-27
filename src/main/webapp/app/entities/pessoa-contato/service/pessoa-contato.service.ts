import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPessoaContato, getPessoaContatoIdentifier } from '../pessoa-contato.model';

export type EntityResponseType = HttpResponse<IPessoaContato>;
export type EntityArrayResponseType = HttpResponse<IPessoaContato[]>;

@Injectable({ providedIn: 'root' })
export class PessoaContatoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pessoa-contatoes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pessoaContato: IPessoaContato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pessoaContato);
    return this.http
      .post<IPessoaContato>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pessoaContato: IPessoaContato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pessoaContato);
    return this.http
      .put<IPessoaContato>(`${this.resourceUrl}/${getPessoaContatoIdentifier(pessoaContato) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(pessoaContato: IPessoaContato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pessoaContato);
    return this.http
      .patch<IPessoaContato>(`${this.resourceUrl}/${getPessoaContatoIdentifier(pessoaContato) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPessoaContato>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPessoaContato[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPessoaContatoToCollectionIfMissing(
    pessoaContatoCollection: IPessoaContato[],
    ...pessoaContatoesToCheck: (IPessoaContato | null | undefined)[]
  ): IPessoaContato[] {
    const pessoaContatoes: IPessoaContato[] = pessoaContatoesToCheck.filter(isPresent);
    if (pessoaContatoes.length > 0) {
      const pessoaContatoCollectionIdentifiers = pessoaContatoCollection.map(
        pessoaContatoItem => getPessoaContatoIdentifier(pessoaContatoItem)!
      );
      const pessoaContatoesToAdd = pessoaContatoes.filter(pessoaContatoItem => {
        const pessoaContatoIdentifier = getPessoaContatoIdentifier(pessoaContatoItem);
        if (pessoaContatoIdentifier == null || pessoaContatoCollectionIdentifiers.includes(pessoaContatoIdentifier)) {
          return false;
        }
        pessoaContatoCollectionIdentifiers.push(pessoaContatoIdentifier);
        return true;
      });
      return [...pessoaContatoesToAdd, ...pessoaContatoCollection];
    }
    return pessoaContatoCollection;
  }

  protected convertDateFromClient(pessoaContato: IPessoaContato): IPessoaContato {
    return Object.assign({}, pessoaContato, {
      dataRegistro: pessoaContato.dataRegistro?.isValid() ? pessoaContato.dataRegistro.toJSON() : undefined,
      dataImportacao: pessoaContato.dataImportacao?.isValid() ? pessoaContato.dataImportacao.toJSON() : undefined,
      dataExclusao: pessoaContato.dataExclusao?.isValid() ? pessoaContato.dataExclusao.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataRegistro = res.body.dataRegistro ? dayjs(res.body.dataRegistro) : undefined;
      res.body.dataImportacao = res.body.dataImportacao ? dayjs(res.body.dataImportacao) : undefined;
      res.body.dataExclusao = res.body.dataExclusao ? dayjs(res.body.dataExclusao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pessoaContato: IPessoaContato) => {
        pessoaContato.dataRegistro = pessoaContato.dataRegistro ? dayjs(pessoaContato.dataRegistro) : undefined;
        pessoaContato.dataImportacao = pessoaContato.dataImportacao ? dayjs(pessoaContato.dataImportacao) : undefined;
        pessoaContato.dataExclusao = pessoaContato.dataExclusao ? dayjs(pessoaContato.dataExclusao) : undefined;
      });
    }
    return res;
  }
}
