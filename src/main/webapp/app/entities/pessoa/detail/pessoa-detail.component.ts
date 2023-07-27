import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPessoa } from '../pessoa.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-pessoa-detail',
  templateUrl: './pessoa-detail.component.html',
})
export class PessoaDetailComponent implements OnInit {
  pessoa: IPessoa | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pessoa }) => {
      this.pessoa = pessoa;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
