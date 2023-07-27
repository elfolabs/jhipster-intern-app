import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPessoaContato, PessoaContato } from '../pessoa-contato.model';
import { PessoaContatoService } from '../service/pessoa-contato.service';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';

@Component({
  selector: 'jhi-pessoa-contato-update',
  templateUrl: './pessoa-contato-update.component.html',
})
export class PessoaContatoUpdateComponent implements OnInit {
  isSaving = false;

  pessoasSharedCollection: IPessoa[] = [];

  editForm = this.fb.group({
    id: [],
    dataRegistro: [null, [Validators.required]],
    dataImportacao: [],
    dataExclusao: [],
    descricao: [],
    contatoDigitalIdent: [],
    telefoneNumeroCompleto: [],
    telefoneDdd: [],
    telefoneNumero: [],
    preferido: [null, [Validators.required]],
    receberPropagandas: [null, [Validators.required]],
    receberConfirmacoes: [null, [Validators.required]],
    possuiWhatsapp: [null, [Validators.required]],
    contato: [],
  });

  constructor(
    protected pessoaContatoService: PessoaContatoService,
    protected pessoaService: PessoaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pessoaContato }) => {
      if (pessoaContato.id === undefined) {
        const today = dayjs().startOf('day');
        pessoaContato.dataRegistro = today;
        pessoaContato.dataImportacao = today;
        pessoaContato.dataExclusao = today;
      }

      this.updateForm(pessoaContato);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pessoaContato = this.createFromForm();
    if (pessoaContato.id !== undefined) {
      this.subscribeToSaveResponse(this.pessoaContatoService.update(pessoaContato));
    } else {
      this.subscribeToSaveResponse(this.pessoaContatoService.create(pessoaContato));
    }
  }

  trackPessoaById(index: number, item: IPessoa): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPessoaContato>>): void {
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

  protected updateForm(pessoaContato: IPessoaContato): void {
    this.editForm.patchValue({
      id: pessoaContato.id,
      dataRegistro: pessoaContato.dataRegistro ? pessoaContato.dataRegistro.format(DATE_TIME_FORMAT) : null,
      dataImportacao: pessoaContato.dataImportacao ? pessoaContato.dataImportacao.format(DATE_TIME_FORMAT) : null,
      dataExclusao: pessoaContato.dataExclusao ? pessoaContato.dataExclusao.format(DATE_TIME_FORMAT) : null,
      descricao: pessoaContato.descricao,
      contatoDigitalIdent: pessoaContato.contatoDigitalIdent,
      telefoneNumeroCompleto: pessoaContato.telefoneNumeroCompleto,
      telefoneDdd: pessoaContato.telefoneDdd,
      telefoneNumero: pessoaContato.telefoneNumero,
      preferido: pessoaContato.preferido,
      receberPropagandas: pessoaContato.receberPropagandas,
      receberConfirmacoes: pessoaContato.receberConfirmacoes,
      possuiWhatsapp: pessoaContato.possuiWhatsapp,
      contato: pessoaContato.contato,
    });

    this.pessoasSharedCollection = this.pessoaService.addPessoaToCollectionIfMissing(this.pessoasSharedCollection, pessoaContato.contato);
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaService
      .query()
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(map((pessoas: IPessoa[]) => this.pessoaService.addPessoaToCollectionIfMissing(pessoas, this.editForm.get('contato')!.value)))
      .subscribe((pessoas: IPessoa[]) => (this.pessoasSharedCollection = pessoas));
  }

  protected createFromForm(): IPessoaContato {
    return {
      ...new PessoaContato(),
      id: this.editForm.get(['id'])!.value,
      dataRegistro: this.editForm.get(['dataRegistro'])!.value
        ? dayjs(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dataImportacao: this.editForm.get(['dataImportacao'])!.value
        ? dayjs(this.editForm.get(['dataImportacao'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dataExclusao: this.editForm.get(['dataExclusao'])!.value
        ? dayjs(this.editForm.get(['dataExclusao'])!.value, DATE_TIME_FORMAT)
        : undefined,
      descricao: this.editForm.get(['descricao'])!.value,
      contatoDigitalIdent: this.editForm.get(['contatoDigitalIdent'])!.value,
      telefoneNumeroCompleto: this.editForm.get(['telefoneNumeroCompleto'])!.value,
      telefoneDdd: this.editForm.get(['telefoneDdd'])!.value,
      telefoneNumero: this.editForm.get(['telefoneNumero'])!.value,
      preferido: this.editForm.get(['preferido'])!.value,
      receberPropagandas: this.editForm.get(['receberPropagandas'])!.value,
      receberConfirmacoes: this.editForm.get(['receberConfirmacoes'])!.value,
      possuiWhatsapp: this.editForm.get(['possuiWhatsapp'])!.value,
      contato: this.editForm.get(['contato'])!.value,
    };
  }
}
