import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PessoaContatoComponent } from '../list/pessoa-contato.component';
import { PessoaContatoDetailComponent } from '../detail/pessoa-contato-detail.component';
import { PessoaContatoUpdateComponent } from '../update/pessoa-contato-update.component';
import { PessoaContatoRoutingResolveService } from './pessoa-contato-routing-resolve.service';

const pessoaContatoRoute: Routes = [
  {
    path: '',
    component: PessoaContatoComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PessoaContatoDetailComponent,
    resolve: {
      pessoaContato: PessoaContatoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PessoaContatoUpdateComponent,
    resolve: {
      pessoaContato: PessoaContatoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PessoaContatoUpdateComponent,
    resolve: {
      pessoaContato: PessoaContatoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pessoaContatoRoute)],
  exports: [RouterModule],
})
export class PessoaContatoRoutingModule {}
