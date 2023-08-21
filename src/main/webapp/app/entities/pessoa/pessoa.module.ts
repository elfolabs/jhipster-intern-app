import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PessoaComponent } from './list/pessoa.component';
import { PessoaDetailComponent } from './detail/pessoa-detail.component';
import { PessoaUpdateComponent } from './update/pessoa-update.component';
import { PessoaDeleteDialogComponent } from './delete/pessoa-delete-dialog.component';
import { PessoaRoutingModule } from './route/pessoa-routing.module';
import { NgxMaskModule, IConfig } from 'ngx-mask';

export const options: Partial<null | IConfig> | (() => Partial<IConfig>) = null;

@NgModule({
  imports: [SharedModule, PessoaRoutingModule, NgxMaskModule.forRoot()],
  declarations: [PessoaComponent, PessoaDetailComponent, PessoaUpdateComponent, PessoaDeleteDialogComponent],
  entryComponents: [PessoaDeleteDialogComponent],
})
export class PessoaModule {}
