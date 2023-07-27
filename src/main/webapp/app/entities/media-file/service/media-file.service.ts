import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMediaFile, getMediaFileIdentifier } from '../media-file.model';

export type EntityResponseType = HttpResponse<IMediaFile>;
export type EntityArrayResponseType = HttpResponse<IMediaFile[]>;

@Injectable({ providedIn: 'root' })
export class MediaFileService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/media-files');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mediaFile: IMediaFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mediaFile);
    return this.http
      .post<IMediaFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mediaFile: IMediaFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mediaFile);
    return this.http
      .put<IMediaFile>(`${this.resourceUrl}/${getMediaFileIdentifier(mediaFile) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(mediaFile: IMediaFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mediaFile);
    return this.http
      .patch<IMediaFile>(`${this.resourceUrl}/${getMediaFileIdentifier(mediaFile) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMediaFile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMediaFile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMediaFileToCollectionIfMissing(
    mediaFileCollection: IMediaFile[],
    ...mediaFilesToCheck: (IMediaFile | null | undefined)[]
  ): IMediaFile[] {
    const mediaFiles: IMediaFile[] = mediaFilesToCheck.filter(isPresent);
    if (mediaFiles.length > 0) {
      const mediaFileCollectionIdentifiers = mediaFileCollection.map(mediaFileItem => getMediaFileIdentifier(mediaFileItem)!);
      const mediaFilesToAdd = mediaFiles.filter(mediaFileItem => {
        const mediaFileIdentifier = getMediaFileIdentifier(mediaFileItem);
        if (mediaFileIdentifier == null || mediaFileCollectionIdentifiers.includes(mediaFileIdentifier)) {
          return false;
        }
        mediaFileCollectionIdentifiers.push(mediaFileIdentifier);
        return true;
      });
      return [...mediaFilesToAdd, ...mediaFileCollection];
    }
    return mediaFileCollection;
  }

  protected convertDateFromClient(mediaFile: IMediaFile): IMediaFile {
    return Object.assign({}, mediaFile, {
      dataRegistro: mediaFile.dataRegistro?.isValid() ? mediaFile.dataRegistro.toJSON() : undefined,
      dataExclusao: mediaFile.dataExclusao?.isValid() ? mediaFile.dataExclusao.toJSON() : undefined,
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
      res.body.forEach((mediaFile: IMediaFile) => {
        mediaFile.dataRegistro = mediaFile.dataRegistro ? dayjs(mediaFile.dataRegistro) : undefined;
        mediaFile.dataExclusao = mediaFile.dataExclusao ? dayjs(mediaFile.dataExclusao) : undefined;
      });
    }
    return res;
  }
}
