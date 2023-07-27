import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PessoaRedeSocialComponent } from './list/pessoa-rede-social.component';
import { PessoaRedeSocialDetailComponent } from './detail/pessoa-rede-social-detail.component';
import { PessoaRedeSocialUpdateComponent } from './update/pessoa-rede-social-update.component';
import { PessoaRedeSocialDeleteDialogComponent } from './delete/pessoa-rede-social-delete-dialog.component';
import { PessoaRedeSocialRoutingModule } from './route/pessoa-rede-social-routing.module';

@NgModule({
  imports: [SharedModule, PessoaRedeSocialRoutingModule],
  declarations: [
    PessoaRedeSocialComponent,
    PessoaRedeSocialDetailComponent,
    PessoaRedeSocialUpdateComponent,
    PessoaRedeSocialDeleteDialogComponent,
  ],
  entryComponents: [PessoaRedeSocialDeleteDialogComponent],
})
export class PessoaRedeSocialModule {}
