import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PessoaContatoComponent } from './list/pessoa-contato.component';
import { PessoaContatoDetailComponent } from './detail/pessoa-contato-detail.component';
import { PessoaContatoUpdateComponent } from './update/pessoa-contato-update.component';
import { PessoaContatoDeleteDialogComponent } from './delete/pessoa-contato-delete-dialog.component';
import { PessoaContatoRoutingModule } from './route/pessoa-contato-routing.module';

@NgModule({
  imports: [SharedModule, PessoaContatoRoutingModule],
  declarations: [PessoaContatoComponent, PessoaContatoDetailComponent, PessoaContatoUpdateComponent, PessoaContatoDeleteDialogComponent],
  entryComponents: [PessoaContatoDeleteDialogComponent],
})
export class PessoaContatoModule {}
