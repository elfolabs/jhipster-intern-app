import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPessoa, Pessoa } from '../pessoa.model';
import { PessoaService } from '../service/pessoa.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { TipoSanguineo } from 'app/entities/enumerations/tipo-sanguineo.model';
import { Sexo } from 'app/entities/enumerations/sexo.model';
import { TipoPessoa } from 'app/entities/enumerations/tipo-pessoa.model';
import { EstadoCivil } from 'app/entities/enumerations/estado-civil.model';

@Component({
  selector: 'jhi-pessoa-update',
  templateUrl: './pessoa-update.component.html',
})
export class PessoaUpdateComponent implements OnInit {
  isSaving = false;
  tipoSanguineoValues = Object.keys(TipoSanguineo);
  sexoValues = Object.keys(Sexo);
  tipoPessoaValues = Object.keys(TipoPessoa);
  estadoCivilValues = Object.keys(EstadoCivil);

  editForm = this.fb.group({
    id: [],
    dataRegistro: [null, [Validators.required]],
    nome: [null, [Validators.required]],
    nomeSocial: [],
    possuiNomeSocial: [],
    apelidoNomeFantasia: [],
    dataNascimento: [],
    nomePai: [],
    nomeMae: [],
    tipoSanguineo: [],
    sexoBiologicoAoNascer: [],
    tipoPessoa: [null, [Validators.required]],
    cpf: [],
    cnpj: [],
    rg: [],
    ie: [],
    estadoCivil: [],
    observacoes: [],
    naturalidade: [],
    raca: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected pessoaService: PessoaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pessoa }) => {
      if (pessoa.id === undefined) {
        const today = dayjs().startOf('day');
        pessoa.dataRegistro = today;
      }

      this.updateForm(pessoa);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('internappApp.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pessoa = this.createFromForm();
    if (pessoa.id !== undefined) {
      this.subscribeToSaveResponse(this.pessoaService.update(pessoa));
    } else {
      this.subscribeToSaveResponse(this.pessoaService.create(pessoa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPessoa>>): void {
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

  protected updateForm(pessoa: IPessoa): void {
    this.editForm.patchValue({
      id: pessoa.id,
      dataRegistro: pessoa.dataRegistro ? pessoa.dataRegistro.format(DATE_TIME_FORMAT) : null,
      nome: pessoa.nome,
      nomeSocial: pessoa.nomeSocial,
      possuiNomeSocial: pessoa.possuiNomeSocial,
      apelidoNomeFantasia: pessoa.apelidoNomeFantasia,
      dataNascimento: pessoa.dataNascimento,
      nomePai: pessoa.nomePai,
      nomeMae: pessoa.nomeMae,
      tipoSanguineo: pessoa.tipoSanguineo,
      sexoBiologicoAoNascer: pessoa.sexoBiologicoAoNascer,
      tipoPessoa: pessoa.tipoPessoa,
      cpf: pessoa.cpf,
      cnpj: pessoa.cnpj,
      rg: pessoa.rg,
      ie: pessoa.ie,
      estadoCivil: pessoa.estadoCivil,
      observacoes: pessoa.observacoes,
      naturalidade: pessoa.naturalidade,
      raca: pessoa.raca,
    });
  }

  protected createFromForm(): IPessoa {
    return {
      ...new Pessoa(),
      id: this.editForm.get(['id'])!.value,
      dataRegistro: this.editForm.get(['dataRegistro'])!.value
        ? dayjs(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
        : undefined,
      nome: this.editForm.get(['nome'])!.value,
      nomeSocial: this.editForm.get(['nomeSocial'])!.value,
      possuiNomeSocial: this.editForm.get(['possuiNomeSocial'])!.value,
      apelidoNomeFantasia: this.editForm.get(['apelidoNomeFantasia'])!.value,
      dataNascimento: this.editForm.get(['dataNascimento'])!.value,
      nomePai: this.editForm.get(['nomePai'])!.value,
      nomeMae: this.editForm.get(['nomeMae'])!.value,
      tipoSanguineo: this.editForm.get(['tipoSanguineo'])!.value,
      sexoBiologicoAoNascer: this.editForm.get(['sexoBiologicoAoNascer'])!.value,
      tipoPessoa: this.editForm.get(['tipoPessoa'])!.value,
      cpf: this.editForm.get(['cpf'])!.value,
      cnpj: this.editForm.get(['cnpj'])!.value,
      rg: this.editForm.get(['rg'])!.value,
      ie: this.editForm.get(['ie'])!.value,
      estadoCivil: this.editForm.get(['estadoCivil'])!.value,
      observacoes: this.editForm.get(['observacoes'])!.value,
      naturalidade: this.editForm.get(['naturalidade'])!.value,
      raca: this.editForm.get(['raca'])!.value,
    };
  }
}
