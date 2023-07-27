import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPessoaContato } from '../pessoa-contato.model';
import { PessoaContatoService } from '../service/pessoa-contato.service';

@Component({
  templateUrl: './pessoa-contato-delete-dialog.component.html',
})
export class PessoaContatoDeleteDialogComponent {
  pessoaContato?: IPessoaContato;

  constructor(protected pessoaContatoService: PessoaContatoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pessoaContatoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
