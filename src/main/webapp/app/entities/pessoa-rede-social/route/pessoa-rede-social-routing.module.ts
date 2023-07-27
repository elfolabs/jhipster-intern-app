import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PessoaRedeSocialComponent } from '../list/pessoa-rede-social.component';
import { PessoaRedeSocialDetailComponent } from '../detail/pessoa-rede-social-detail.component';
import { PessoaRedeSocialUpdateComponent } from '../update/pessoa-rede-social-update.component';
import { PessoaRedeSocialRoutingResolveService } from './pessoa-rede-social-routing-resolve.service';

const pessoaRedeSocialRoute: Routes = [
  {
    path: '',
    component: PessoaRedeSocialComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PessoaRedeSocialDetailComponent,
    resolve: {
      pessoaRedeSocial: PessoaRedeSocialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PessoaRedeSocialUpdateComponent,
    resolve: {
      pessoaRedeSocial: PessoaRedeSocialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PessoaRedeSocialUpdateComponent,
    resolve: {
      pessoaRedeSocial: PessoaRedeSocialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pessoaRedeSocialRoute)],
  exports: [RouterModule],
})
export class PessoaRedeSocialRoutingModule {}
