import dayjs from 'dayjs/esm';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';

export interface IMediaFile {
  id?: number;
  mediaType?: string;
  fileName?: string | null;
  fileExtension?: string | null;
  mediaTitle?: string | null;
  mediaDescription?: string | null;
  publico?: boolean;
  repoName?: string;
  repoUuid?: string;
  repoFolder?: string;
  repoPath?: string | null;
  thumbnail?: boolean;
  width?: number | null;
  height?: number | null;
  dataRegistro?: dayjs.Dayjs;
  dataExclusao?: dayjs.Dayjs | null;
  tamanhoBytes?: number | null;
  fotoRosto?: IPessoa | null;
}

export class MediaFile implements IMediaFile {
  constructor(
    public id?: number,
    public mediaType?: string,
    public fileName?: string | null,
    public fileExtension?: string | null,
    public mediaTitle?: string | null,
    public mediaDescription?: string | null,
    public publico?: boolean,
    public repoName?: string,
    public repoUuid?: string,
    public repoFolder?: string,
    public repoPath?: string | null,
    public thumbnail?: boolean,
    public width?: number | null,
    public height?: number | null,
    public dataRegistro?: dayjs.Dayjs,
    public dataExclusao?: dayjs.Dayjs | null,
    public tamanhoBytes?: number | null,
    public fotoRosto?: IPessoa | null
  ) {
    this.publico = this.publico ?? false;
    this.thumbnail = this.thumbnail ?? false;
  }
}

export function getMediaFileIdentifier(mediaFile: IMediaFile): number | undefined {
  return mediaFile.id;
}
