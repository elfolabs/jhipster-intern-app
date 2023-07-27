import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMediaFile } from '../media-file.model';
import { MediaFileService } from '../service/media-file.service';

@Component({
  templateUrl: './media-file-delete-dialog.component.html',
})
export class MediaFileDeleteDialogComponent {
  mediaFile?: IMediaFile;

  constructor(protected mediaFileService: MediaFileService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mediaFileService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
