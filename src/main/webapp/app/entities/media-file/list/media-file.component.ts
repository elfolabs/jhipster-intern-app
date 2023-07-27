import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMediaFile } from '../media-file.model';

import { ASC, DESC, ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { MediaFileService } from '../service/media-file.service';
import { MediaFileDeleteDialogComponent } from '../delete/media-file-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-media-file',
  templateUrl: './media-file.component.html',
})
export class MediaFileComponent implements OnInit {
  mediaFiles: IMediaFile[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected mediaFileService: MediaFileService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.mediaFiles = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.isLoading = true;

    this.mediaFileService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<IMediaFile[]>) => {
          this.isLoading = false;
          this.paginateMediaFiles(res.body, res.headers);
        },
        error: () => {
          this.isLoading = false;
        },
      });
  }

  reset(): void {
    this.page = 0;
    this.mediaFiles = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IMediaFile): number {
    return item.id!;
  }

  delete(mediaFile: IMediaFile): void {
    const modalRef = this.modalService.open(MediaFileDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mediaFile = mediaFile;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.reset();
      }
    });
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMediaFiles(data: IMediaFile[] | null, headers: HttpHeaders): void {
    const linkHeader = headers.get('link');
    if (linkHeader) {
      this.links = this.parseLinks.parse(linkHeader);
    } else {
      this.links = {
        last: 0,
      };
    }
    if (data) {
      for (const d of data) {
        this.mediaFiles.push(d);
      }
    }
  }
}
