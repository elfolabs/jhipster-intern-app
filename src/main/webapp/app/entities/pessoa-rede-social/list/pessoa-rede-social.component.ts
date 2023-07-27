import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPessoaRedeSocial } from '../pessoa-rede-social.model';

import { ASC, DESC, ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { PessoaRedeSocialService } from '../service/pessoa-rede-social.service';
import { PessoaRedeSocialDeleteDialogComponent } from '../delete/pessoa-rede-social-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-pessoa-rede-social',
  templateUrl: './pessoa-rede-social.component.html',
})
export class PessoaRedeSocialComponent implements OnInit {
  pessoaRedeSocials: IPessoaRedeSocial[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected pessoaRedeSocialService: PessoaRedeSocialService,
    protected modalService: NgbModal,
    protected parseLinks: ParseLinks
  ) {
    this.pessoaRedeSocials = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.isLoading = true;

    this.pessoaRedeSocialService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<IPessoaRedeSocial[]>) => {
          this.isLoading = false;
          this.paginatePessoaRedeSocials(res.body, res.headers);
        },
        error: () => {
          this.isLoading = false;
        },
      });
  }

  reset(): void {
    this.page = 0;
    this.pessoaRedeSocials = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPessoaRedeSocial): number {
    return item.id!;
  }

  delete(pessoaRedeSocial: IPessoaRedeSocial): void {
    const modalRef = this.modalService.open(PessoaRedeSocialDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pessoaRedeSocial = pessoaRedeSocial;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.reset();
      }
    });
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePessoaRedeSocials(data: IPessoaRedeSocial[] | null, headers: HttpHeaders): void {
    const linkHeader = headers.get('link');
    if (linkHeader) {
      this.links = this.parseLinks.parse(linkHeader);
    } else {
      this.links = {
        last: 0,
      };
    }
    if (data) {
      for (const d of data) {
        this.pessoaRedeSocials.push(d);
      }
    }
  }
}
