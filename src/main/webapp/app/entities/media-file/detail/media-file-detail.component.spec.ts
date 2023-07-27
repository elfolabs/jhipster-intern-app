import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MediaFileDetailComponent } from './media-file-detail.component';

describe('MediaFile Management Detail Component', () => {
  let comp: MediaFileDetailComponent;
  let fixture: ComponentFixture<MediaFileDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MediaFileDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ mediaFile: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MediaFileDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MediaFileDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mediaFile on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.mediaFile).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
