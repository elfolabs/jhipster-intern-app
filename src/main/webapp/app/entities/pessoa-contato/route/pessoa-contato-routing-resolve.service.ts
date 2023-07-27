import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPessoaContato, PessoaContato } from '../pessoa-contato.model';
import { PessoaContatoService } from '../service/pessoa-contato.service';

@Injectable({ providedIn: 'root' })
export class PessoaContatoRoutingResolveService implements Resolve<IPessoaContato> {
  constructor(protected service: PessoaContatoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPessoaContato> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pessoaContato: HttpResponse<PessoaContato>) => {
          if (pessoaContato.body) {
            return of(pessoaContato.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PessoaContato());
  }
}
