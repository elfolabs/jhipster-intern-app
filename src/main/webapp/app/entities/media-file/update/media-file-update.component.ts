import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IMediaFile, MediaFile } from '../media-file.model';
import { MediaFileService } from '../service/media-file.service';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';

@Component({
  selector: 'jhi-media-file-update',
  templateUrl: './media-file-update.component.html',
})
export class MediaFileUpdateComponent implements OnInit {
  isSaving = false;

  pessoasSharedCollection: IPessoa[] = [];

  editForm = this.fb.group({
    id: [],
    mediaType: [null, [Validators.required]],
    fileName: [],
    fileExtension: [],
    mediaTitle: [],
    mediaDescription: [],
    publico: [null, [Validators.required]],
    repoName: [null, [Validators.required]],
    repoUuid: [null, [Validators.required]],
    repoFolder: [null, [Validators.required]],
    repoPath: [],
    thumbnail: [null, [Validators.required]],
    width: [],
    height: [],
    dataRegistro: [null, [Validators.required]],
    dataExclusao: [],
    tamanhoBytes: [],
    fotoRosto: [],
  });

  constructor(
    protected mediaFileService: MediaFileService,
    protected pessoaService: PessoaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mediaFile }) => {
      if (mediaFile.id === undefined) {
        const today = dayjs().startOf('day');
        mediaFile.dataRegistro = today;
        mediaFile.dataExclusao = today;
      }

      this.updateForm(mediaFile);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mediaFile = this.createFromForm();
    if (mediaFile.id !== undefined) {
      this.subscribeToSaveResponse(this.mediaFileService.update(mediaFile));
    } else {
      this.subscribeToSaveResponse(this.mediaFileService.create(mediaFile));
    }
  }

  trackPessoaById(index: number, item: IPessoa): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMediaFile>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(mediaFile: IMediaFile): void {
    this.editForm.patchValue({
      id: mediaFile.id,
      mediaType: mediaFile.mediaType,
      fileName: mediaFile.fileName,
      fileExtension: mediaFile.fileExtension,
      mediaTitle: mediaFile.mediaTitle,
      mediaDescription: mediaFile.mediaDescription,
      publico: mediaFile.publico,
      repoName: mediaFile.repoName,
      repoUuid: mediaFile.repoUuid,
      repoFolder: mediaFile.repoFolder,
      repoPath: mediaFile.repoPath,
      thumbnail: mediaFile.thumbnail,
      width: mediaFile.width,
      height: mediaFile.height,
      dataRegistro: mediaFile.dataRegistro ? mediaFile.dataRegistro.format(DATE_TIME_FORMAT) : null,
      dataExclusao: mediaFile.dataExclusao ? mediaFile.dataExclusao.format(DATE_TIME_FORMAT) : null,
      tamanhoBytes: mediaFile.tamanhoBytes,
      fotoRosto: mediaFile.fotoRosto,
    });

    this.pessoasSharedCollection = this.pessoaService.addPessoaToCollectionIfMissing(this.pessoasSharedCollection, mediaFile.fotoRosto);
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaService
      .query()
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(map((pessoas: IPessoa[]) => this.pessoaService.addPessoaToCollectionIfMissing(pessoas, this.editForm.get('fotoRosto')!.value)))
      .subscribe((pessoas: IPessoa[]) => (this.pessoasSharedCollection = pessoas));
  }

  protected createFromForm(): IMediaFile {
    return {
      ...new MediaFile(),
      id: this.editForm.get(['id'])!.value,
      mediaType: this.editForm.get(['mediaType'])!.value,
      fileName: this.editForm.get(['fileName'])!.value,
      fileExtension: this.editForm.get(['fileExtension'])!.value,
      mediaTitle: this.editForm.get(['mediaTitle'])!.value,
      mediaDescription: this.editForm.get(['mediaDescription'])!.value,
      publico: this.editForm.get(['publico'])!.value,
      repoName: this.editForm.get(['repoName'])!.value,
      repoUuid: this.editForm.get(['repoUuid'])!.value,
      repoFolder: this.editForm.get(['repoFolder'])!.value,
      repoPath: this.editForm.get(['repoPath'])!.value,
      thumbnail: this.editForm.get(['thumbnail'])!.value,
      width: this.editForm.get(['width'])!.value,
      height: this.editForm.get(['height'])!.value,
      dataRegistro: this.editForm.get(['dataRegistro'])!.value
        ? dayjs(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dataExclusao: this.editForm.get(['dataExclusao'])!.value
        ? dayjs(this.editForm.get(['dataExclusao'])!.value, DATE_TIME_FORMAT)
        : undefined,
      tamanhoBytes: this.editForm.get(['tamanhoBytes'])!.value,
      fotoRosto: this.editForm.get(['fotoRosto'])!.value,
    };
  }
}
