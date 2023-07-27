import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MediaFileComponent } from './list/media-file.component';
import { MediaFileDetailComponent } from './detail/media-file-detail.component';
import { MediaFileUpdateComponent } from './update/media-file-update.component';
import { MediaFileDeleteDialogComponent } from './delete/media-file-delete-dialog.component';
import { MediaFileRoutingModule } from './route/media-file-routing.module';

@NgModule({
  imports: [SharedModule, MediaFileRoutingModule],
  declarations: [MediaFileComponent, MediaFileDetailComponent, MediaFileUpdateComponent, MediaFileDeleteDialogComponent],
  entryComponents: [MediaFileDeleteDialogComponent],
})
export class MediaFileModule {}
