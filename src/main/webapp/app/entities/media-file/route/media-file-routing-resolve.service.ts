import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMediaFile, MediaFile } from '../media-file.model';
import { MediaFileService } from '../service/media-file.service';

@Injectable({ providedIn: 'root' })
export class MediaFileRoutingResolveService implements Resolve<IMediaFile> {
  constructor(protected service: MediaFileService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMediaFile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mediaFile: HttpResponse<MediaFile>) => {
          if (mediaFile.body) {
            return of(mediaFile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MediaFile());
  }
}
