import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPessoaRedeSocial, PessoaRedeSocial } from '../pessoa-rede-social.model';
import { PessoaRedeSocialService } from '../service/pessoa-rede-social.service';

@Injectable({ providedIn: 'root' })
export class PessoaRedeSocialRoutingResolveService implements Resolve<IPessoaRedeSocial> {
  constructor(protected service: PessoaRedeSocialService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPessoaRedeSocial> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pessoaRedeSocial: HttpResponse<PessoaRedeSocial>) => {
          if (pessoaRedeSocial.body) {
            return of(pessoaRedeSocial.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PessoaRedeSocial());
  }
}
