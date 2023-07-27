import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPessoaRedeSocial } from '../pessoa-rede-social.model';
import { PessoaRedeSocialService } from '../service/pessoa-rede-social.service';

@Component({
  templateUrl: './pessoa-rede-social-delete-dialog.component.html',
})
export class PessoaRedeSocialDeleteDialogComponent {
  pessoaRedeSocial?: IPessoaRedeSocial;

  constructor(protected pessoaRedeSocialService: PessoaRedeSocialService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pessoaRedeSocialService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
