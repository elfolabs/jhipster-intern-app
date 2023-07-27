import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMediaFile, MediaFile } from '../media-file.model';

import { MediaFileService } from './media-file.service';

describe('MediaFile Service', () => {
  let service: MediaFileService;
  let httpMock: HttpTestingController;
  let elemDefault: IMediaFile;
  let expectedResult: IMediaFile | IMediaFile[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MediaFileService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      mediaType: 'AAAAAAA',
      fileName: 'AAAAAAA',
      fileExtension: 'AAAAAAA',
      mediaTitle: 'AAAAAAA',
      mediaDescription: 'AAAAAAA',
      publico: false,
      repoName: 'AAAAAAA',
      repoUuid: 'AAAAAAA',
      repoFolder: 'AAAAAAA',
      repoPath: 'AAAAAAA',
      thumbnail: false,
      width: 0,
      height: 0,
      dataRegistro: currentDate,
      dataExclusao: currentDate,
      tamanhoBytes: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dataRegistro: currentDate.format(DATE_TIME_FORMAT),
          dataExclusao: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a MediaFile', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dataRegistro: currentDate.format(DATE_TIME_FORMAT),
          dataExclusao: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataRegistro: currentDate,
          dataExclusao: currentDate,
        },
        returnedFromService
      );

      service.create(new MediaFile()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MediaFile', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          mediaType: 'BBBBBB',
          fileName: 'BBBBBB',
          fileExtension: 'BBBBBB',
          mediaTitle: 'BBBBBB',
          mediaDescription: 'BBBBBB',
          publico: true,
          repoName: 'BBBBBB',
          repoUuid: 'BBBBBB',
          repoFolder: 'BBBBBB',
          repoPath: 'BBBBBB',
          thumbnail: true,
          width: 1,
          height: 1,
          dataRegistro: currentDate.format(DATE_TIME_FORMAT),
          dataExclusao: currentDate.format(DATE_TIME_FORMAT),
          tamanhoBytes: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataRegistro: currentDate,
          dataExclusao: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MediaFile', () => {
      const patchObject = Object.assign(
        {
          mediaTitle: 'BBBBBB',
          mediaDescription: 'BBBBBB',
          repoName: 'BBBBBB',
          repoUuid: 'BBBBBB',
          repoPath: 'BBBBBB',
          width: 1,
          height: 1,
          dataExclusao: currentDate.format(DATE_TIME_FORMAT),
        },
        new MediaFile()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dataRegistro: currentDate,
          dataExclusao: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MediaFile', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          mediaType: 'BBBBBB',
          fileName: 'BBBBBB',
          fileExtension: 'BBBBBB',
          mediaTitle: 'BBBBBB',
          mediaDescription: 'BBBBBB',
          publico: true,
          repoName: 'BBBBBB',
          repoUuid: 'BBBBBB',
          repoFolder: 'BBBBBB',
          repoPath: 'BBBBBB',
          thumbnail: true,
          width: 1,
          height: 1,
          dataRegistro: currentDate.format(DATE_TIME_FORMAT),
          dataExclusao: currentDate.format(DATE_TIME_FORMAT),
          tamanhoBytes: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataRegistro: currentDate,
          dataExclusao: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a MediaFile', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMediaFileToCollectionIfMissing', () => {
      it('should add a MediaFile to an empty array', () => {
        const mediaFile: IMediaFile = { id: 123 };
        expectedResult = service.addMediaFileToCollectionIfMissing([], mediaFile);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mediaFile);
      });

      it('should not add a MediaFile to an array that contains it', () => {
        const mediaFile: IMediaFile = { id: 123 };
        const mediaFileCollection: IMediaFile[] = [
          {
            ...mediaFile,
          },
          { id: 456 },
        ];
        expectedResult = service.addMediaFileToCollectionIfMissing(mediaFileCollection, mediaFile);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MediaFile to an array that doesn't contain it", () => {
        const mediaFile: IMediaFile = { id: 123 };
        const mediaFileCollection: IMediaFile[] = [{ id: 456 }];
        expectedResult = service.addMediaFileToCollectionIfMissing(mediaFileCollection, mediaFile);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mediaFile);
      });

      it('should add only unique MediaFile to an array', () => {
        const mediaFileArray: IMediaFile[] = [{ id: 123 }, { id: 456 }, { id: 1756 }];
        const mediaFileCollection: IMediaFile[] = [{ id: 123 }];
        expectedResult = service.addMediaFileToCollectionIfMissing(mediaFileCollection, ...mediaFileArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mediaFile: IMediaFile = { id: 123 };
        const mediaFile2: IMediaFile = { id: 456 };
        expectedResult = service.addMediaFileToCollectionIfMissing([], mediaFile, mediaFile2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mediaFile);
        expect(expectedResult).toContain(mediaFile2);
      });

      it('should accept null and undefined values', () => {
        const mediaFile: IMediaFile = { id: 123 };
        expectedResult = service.addMediaFileToCollectionIfMissing([], null, mediaFile, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mediaFile);
      });

      it('should return initial array if no MediaFile is added', () => {
        const mediaFileCollection: IMediaFile[] = [{ id: 123 }];
        expectedResult = service.addMediaFileToCollectionIfMissing(mediaFileCollection, undefined, null);
        expect(expectedResult).toEqual(mediaFileCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
