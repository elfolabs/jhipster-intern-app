import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPessoaRedeSocial, PessoaRedeSocial } from '../pessoa-rede-social.model';
import { PessoaRedeSocialService } from '../service/pessoa-rede-social.service';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';

@Component({
  selector: 'jhi-pessoa-rede-social-update',
  templateUrl: './pessoa-rede-social-update.component.html',
})
export class PessoaRedeSocialUpdateComponent implements OnInit {
  isSaving = false;

  pessoasSharedCollection: IPessoa[] = [];

  editForm = this.fb.group({
    id: [],
    nomeRede: [null, [Validators.required]],
    endereco: [null, [Validators.maxLength(2000)]],
    dataRegistro: [null, [Validators.required]],
    dataExclusao: [],
    divulgarNoAplicativo: [],
    redeSocial: [],
  });

  constructor(
    protected pessoaRedeSocialService: PessoaRedeSocialService,
    protected pessoaService: PessoaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pessoaRedeSocial }) => {
      if (pessoaRedeSocial.id === undefined) {
        const today = dayjs().startOf('day');
        pessoaRedeSocial.dataRegistro = today;
        pessoaRedeSocial.dataExclusao = today;
      }

      this.updateForm(pessoaRedeSocial);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pessoaRedeSocial = this.createFromForm();
    if (pessoaRedeSocial.id !== undefined) {
      this.subscribeToSaveResponse(this.pessoaRedeSocialService.update(pessoaRedeSocial));
    } else {
      this.subscribeToSaveResponse(this.pessoaRedeSocialService.create(pessoaRedeSocial));
    }
  }

  trackPessoaById(index: number, item: IPessoa): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPessoaRedeSocial>>): void {
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

  protected updateForm(pessoaRedeSocial: IPessoaRedeSocial): void {
    this.editForm.patchValue({
      id: pessoaRedeSocial.id,
      nomeRede: pessoaRedeSocial.nomeRede,
      endereco: pessoaRedeSocial.endereco,
      dataRegistro: pessoaRedeSocial.dataRegistro ? pessoaRedeSocial.dataRegistro.format(DATE_TIME_FORMAT) : null,
      dataExclusao: pessoaRedeSocial.dataExclusao ? pessoaRedeSocial.dataExclusao.format(DATE_TIME_FORMAT) : null,
      divulgarNoAplicativo: pessoaRedeSocial.divulgarNoAplicativo,
      redeSocial: pessoaRedeSocial.redeSocial,
    });

    this.pessoasSharedCollection = this.pessoaService.addPessoaToCollectionIfMissing(
      this.pessoasSharedCollection,
      pessoaRedeSocial.redeSocial
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaService
      .query()
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(map((pessoas: IPessoa[]) => this.pessoaService.addPessoaToCollectionIfMissing(pessoas, this.editForm.get('redeSocial')!.value)))
      .subscribe((pessoas: IPessoa[]) => (this.pessoasSharedCollection = pessoas));
  }

  protected createFromForm(): IPessoaRedeSocial {
    return {
      ...new PessoaRedeSocial(),
      id: this.editForm.get(['id'])!.value,
      nomeRede: this.editForm.get(['nomeRede'])!.value,
      endereco: this.editForm.get(['endereco'])!.value,
      dataRegistro: this.editForm.get(['dataRegistro'])!.value
        ? dayjs(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dataExclusao: this.editForm.get(['dataExclusao'])!.value
        ? dayjs(this.editForm.get(['dataExclusao'])!.value, DATE_TIME_FORMAT)
        : undefined,
      divulgarNoAplicativo: this.editForm.get(['divulgarNoAplicativo'])!.value,
      redeSocial: this.editForm.get(['redeSocial'])!.value,
    };
  }
}
