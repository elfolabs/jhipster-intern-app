import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPessoaContato } from '../pessoa-contato.model';

@Component({
  selector: 'jhi-pessoa-contato-detail',
  templateUrl: './pessoa-contato-detail.component.html',
})
export class PessoaContatoDetailComponent implements OnInit {
  pessoaContato: IPessoaContato | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pessoaContato }) => {
      this.pessoaContato = pessoaContato;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
