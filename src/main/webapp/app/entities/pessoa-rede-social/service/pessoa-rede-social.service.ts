import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPessoaRedeSocial, getPessoaRedeSocialIdentifier } from '../pessoa-rede-social.model';

export type EntityResponseType = HttpResponse<IPessoaRedeSocial>;
export type EntityArrayResponseType = HttpResponse<IPessoaRedeSocial[]>;

@Injectable({ providedIn: 'root' })
export class PessoaRedeSocialService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pessoa-rede-socials');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pessoaRedeSocial: IPessoaRedeSocial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pessoaRedeSocial);
    return this.http
      .post<IPessoaRedeSocial>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pessoaRedeSocial: IPessoaRedeSocial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pessoaRedeSocial);
    return this.http
      .put<IPessoaRedeSocial>(`${this.resourceUrl}/${getPessoaRedeSocialIdentifier(pessoaRedeSocial) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(pessoaRedeSocial: IPessoaRedeSocial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pessoaRedeSocial);
    return this.http
      .patch<IPessoaRedeSocial>(`${this.resourceUrl}/${getPessoaRedeSocialIdentifier(pessoaRedeSocial) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPessoaRedeSocial>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPessoaRedeSocial[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPessoaRedeSocialToCollectionIfMissing(
    pessoaRedeSocialCollection: IPessoaRedeSocial[],
    ...pessoaRedeSocialsToCheck: (IPessoaRedeSocial | null | undefined)[]
  ): IPessoaRedeSocial[] {
    const pessoaRedeSocials: IPessoaRedeSocial[] = pessoaRedeSocialsToCheck.filter(isPresent);
    if (pessoaRedeSocials.length > 0) {
      const pessoaRedeSocialCollectionIdentifiers = pessoaRedeSocialCollection.map(
        pessoaRedeSocialItem => getPessoaRedeSocialIdentifier(pessoaRedeSocialItem)!
      );
      const pessoaRedeSocialsToAdd = pessoaRedeSocials.filter(pessoaRedeSocialItem => {
        const pessoaRedeSocialIdentifier = getPessoaRedeSocialIdentifier(pessoaRedeSocialItem);
        if (pessoaRedeSocialIdentifier == null || pessoaRedeSocialCollectionIdentifiers.includes(pessoaRedeSocialIdentifier)) {
          return false;
        }
        pessoaRedeSocialCollectionIdentifiers.push(pessoaRedeSocialIdentifier);
        return true;
      });
      return [...pessoaRedeSocialsToAdd, ...pessoaRedeSocialCollection];
    }
    return pessoaRedeSocialCollection;
  }

  protected convertDateFromClient(pessoaRedeSocial: IPessoaRedeSocial): IPessoaRedeSocial {
    return Object.assign({}, pessoaRedeSocial, {
      dataRegistro: pessoaRedeSocial.dataRegistro?.isValid() ? pessoaRedeSocial.dataRegistro.toJSON() : undefined,
      dataExclusao: pessoaRedeSocial.dataExclusao?.isValid() ? pessoaRedeSocial.dataExclusao.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataRegistro = res.body.dataRegistro ? dayjs(res.body.dataRegistro) : undefined;
      res.body.dataExclusao = res.body.dataExclusao ? dayjs(res.body.dataExclusao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pessoaRedeSocial: IPessoaRedeSocial) => {
        pessoaRedeSocial.dataRegistro = pessoaRedeSocial.dataRegistro ? dayjs(pessoaRedeSocial.dataRegistro) : undefined;
        pessoaRedeSocial.dataExclusao = pessoaRedeSocial.dataExclusao ? dayjs(pessoaRedeSocial.dataExclusao) : undefined;
      });
    }
    return res;
  }
}
