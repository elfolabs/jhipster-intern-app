import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMediaFile } from '../media-file.model';

@Component({
  selector: 'jhi-media-file-detail',
  templateUrl: './media-file-detail.component.html',
})
export class MediaFileDetailComponent implements OnInit {
  mediaFile: IMediaFile | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mediaFile }) => {
      this.mediaFile = mediaFile;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
