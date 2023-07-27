import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPessoaContato } from '../pessoa-contato.model';

import { ASC, DESC, ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { PessoaContatoService } from '../service/pessoa-contato.service';
import { PessoaContatoDeleteDialogComponent } from '../delete/pessoa-contato-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-pessoa-contato',
  templateUrl: './pessoa-contato.component.html',
})
export class PessoaContatoComponent implements OnInit {
  pessoaContatoes: IPessoaContato[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected pessoaContatoService: PessoaContatoService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.pessoaContatoes = [];
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

    this.pessoaContatoService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<IPessoaContato[]>) => {
          this.isLoading = false;
          this.paginatePessoaContatoes(res.body, res.headers);
        },
        error: () => {
          this.isLoading = false;
        },
      });
  }

  reset(): void {
    this.page = 0;
    this.pessoaContatoes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPessoaContato): number {
    return item.id!;
  }

  delete(pessoaContato: IPessoaContato): void {
    const modalRef = this.modalService.open(PessoaContatoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pessoaContato = pessoaContato;
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

  protected paginatePessoaContatoes(data: IPessoaContato[] | null, headers: HttpHeaders): void {
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
        this.pessoaContatoes.push(d);
      }
    }
  }
}
