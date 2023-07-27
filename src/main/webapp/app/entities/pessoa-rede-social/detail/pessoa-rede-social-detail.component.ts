import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPessoaRedeSocial } from '../pessoa-rede-social.model';

@Component({
  selector: 'jhi-pessoa-rede-social-detail',
  templateUrl: './pessoa-rede-social-detail.component.html',
})
export class PessoaRedeSocialDetailComponent implements OnInit {
  pessoaRedeSocial: IPessoaRedeSocial | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pessoaRedeSocial }) => {
      this.pessoaRedeSocial = pessoaRedeSocial;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
