import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MediaFileService } from '../service/media-file.service';
import { IMediaFile, MediaFile } from '../media-file.model';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';

import { MediaFileUpdateComponent } from './media-file-update.component';

describe('MediaFile Management Update Component', () => {
  let comp: MediaFileUpdateComponent;
  let fixture: ComponentFixture<MediaFileUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mediaFileService: MediaFileService;
  let pessoaService: PessoaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MediaFileUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MediaFileUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MediaFileUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mediaFileService = TestBed.inject(MediaFileService);
    pessoaService = TestBed.inject(PessoaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pessoa query and add missing value', () => {
      const mediaFile: IMediaFile = { id: 456 };
      const fotoRosto: IPessoa = { id: 28534 };
      mediaFile.fotoRosto = fotoRosto;

      const pessoaCollection: IPessoa[] = [{ id: 23945 }];
      jest.spyOn(pessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaCollection })));
      const additionalPessoas = [fotoRosto];
      const expectedCollection: IPessoa[] = [...additionalPessoas, ...pessoaCollection];
      jest.spyOn(pessoaService, 'addPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mediaFile });
      comp.ngOnInit();

      expect(pessoaService.query).toHaveBeenCalled();
      expect(pessoaService.addPessoaToCollectionIfMissing).toHaveBeenCalledWith(pessoaCollection, ...additionalPessoas);
      expect(comp.pessoasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mediaFile: IMediaFile = { id: 456 };
      const fotoRosto: IPessoa = { id: 44028 };
      mediaFile.fotoRosto = fotoRosto;

      activatedRoute.data = of({ mediaFile });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(mediaFile));
      expect(comp.pessoasSharedCollection).toContain(fotoRosto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MediaFile>>();
      const mediaFile = { id: 123 };
      jest.spyOn(mediaFileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mediaFile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mediaFile }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(mediaFileService.update).toHaveBeenCalledWith(mediaFile);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MediaFile>>();
      const mediaFile = new MediaFile();
      jest.spyOn(mediaFileService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mediaFile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mediaFile }));
      saveSubject.complete();

      // THEN
      expect(mediaFileService.create).toHaveBeenCalledWith(mediaFile);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MediaFile>>();
      const mediaFile = { id: 123 };
      jest.spyOn(mediaFileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mediaFile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mediaFileService.update).toHaveBeenCalledWith(mediaFile);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPessoaById', () => {
      it('Should return tracked Pessoa primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPessoaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
