import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPessoaRedeSocial, PessoaRedeSocial } from '../pessoa-rede-social.model';

import { PessoaRedeSocialService } from './pessoa-rede-social.service';

describe('PessoaRedeSocial Service', () => {
  let service: PessoaRedeSocialService;
  let httpMock: HttpTestingController;
  let elemDefault: IPessoaRedeSocial;
  let expectedResult: IPessoaRedeSocial | IPessoaRedeSocial[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PessoaRedeSocialService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      nomeRede: 'AAAAAAA',
      endereco: 'AAAAAAA',
      dataRegistro: currentDate,
      dataExclusao: currentDate,
      divulgarNoAplicativo: false,
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

    it('should create a PessoaRedeSocial', () => {
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

      service.create(new PessoaRedeSocial()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PessoaRedeSocial', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomeRede: 'BBBBBB',
          endereco: 'BBBBBB',
          dataRegistro: currentDate.format(DATE_TIME_FORMAT),
          dataExclusao: currentDate.format(DATE_TIME_FORMAT),
          divulgarNoAplicativo: true,
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

    it('should partial update a PessoaRedeSocial', () => {
      const patchObject = Object.assign(
        {
          nomeRede: 'BBBBBB',
          dataRegistro: currentDate.format(DATE_TIME_FORMAT),
        },
        new PessoaRedeSocial()
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

    it('should return a list of PessoaRedeSocial', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomeRede: 'BBBBBB',
          endereco: 'BBBBBB',
          dataRegistro: currentDate.format(DATE_TIME_FORMAT),
          dataExclusao: currentDate.format(DATE_TIME_FORMAT),
          divulgarNoAplicativo: true,
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

    it('should delete a PessoaRedeSocial', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPessoaRedeSocialToCollectionIfMissing', () => {
      it('should add a PessoaRedeSocial to an empty array', () => {
        const pessoaRedeSocial: IPessoaRedeSocial = { id: 123 };
        expectedResult = service.addPessoaRedeSocialToCollectionIfMissing([], pessoaRedeSocial);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pessoaRedeSocial);
      });

      it('should not add a PessoaRedeSocial to an array that contains it', () => {
        const pessoaRedeSocial: IPessoaRedeSocial = { id: 123 };
        const pessoaRedeSocialCollection: IPessoaRedeSocial[] = [
          {
            ...pessoaRedeSocial,
          },
          { id: 456 },
        ];
        expectedResult = service.addPessoaRedeSocialToCollectionIfMissing(pessoaRedeSocialCollection, pessoaRedeSocial);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PessoaRedeSocial to an array that doesn't contain it", () => {
        const pessoaRedeSocial: IPessoaRedeSocial = { id: 123 };
        const pessoaRedeSocialCollection: IPessoaRedeSocial[] = [{ id: 456 }];
        expectedResult = service.addPessoaRedeSocialToCollectionIfMissing(pessoaRedeSocialCollection, pessoaRedeSocial);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pessoaRedeSocial);
      });

      it('should add only unique PessoaRedeSocial to an array', () => {
        const pessoaRedeSocialArray: IPessoaRedeSocial[] = [{ id: 123 }, { id: 456 }, { id: 8432 }];
        const pessoaRedeSocialCollection: IPessoaRedeSocial[] = [{ id: 123 }];
        expectedResult = service.addPessoaRedeSocialToCollectionIfMissing(pessoaRedeSocialCollection, ...pessoaRedeSocialArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pessoaRedeSocial: IPessoaRedeSocial = { id: 123 };
        const pessoaRedeSocial2: IPessoaRedeSocial = { id: 456 };
        expectedResult = service.addPessoaRedeSocialToCollectionIfMissing([], pessoaRedeSocial, pessoaRedeSocial2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pessoaRedeSocial);
        expect(expectedResult).toContain(pessoaRedeSocial2);
      });

      it('should accept null and undefined values', () => {
        const pessoaRedeSocial: IPessoaRedeSocial = { id: 123 };
        expectedResult = service.addPessoaRedeSocialToCollectionIfMissing([], null, pessoaRedeSocial, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pessoaRedeSocial);
      });

      it('should return initial array if no PessoaRedeSocial is added', () => {
        const pessoaRedeSocialCollection: IPessoaRedeSocial[] = [{ id: 123 }];
        expectedResult = service.addPessoaRedeSocialToCollectionIfMissing(pessoaRedeSocialCollection, undefined, null);
        expect(expectedResult).toEqual(pessoaRedeSocialCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
