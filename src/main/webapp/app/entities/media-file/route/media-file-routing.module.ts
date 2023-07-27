import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MediaFileComponent } from '../list/media-file.component';
import { MediaFileDetailComponent } from '../detail/media-file-detail.component';
import { MediaFileUpdateComponent } from '../update/media-file-update.component';
import { MediaFileRoutingResolveService } from './media-file-routing-resolve.service';

const mediaFileRoute: Routes = [
  {
    path: '',
    component: MediaFileComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MediaFileDetailComponent,
    resolve: {
      mediaFile: MediaFileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MediaFileUpdateComponent,
    resolve: {
      mediaFile: MediaFileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MediaFileUpdateComponent,
    resolve: {
      mediaFile: MediaFileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mediaFileRoute)],
  exports: [RouterModule],
})
export class MediaFileRoutingModule {}
