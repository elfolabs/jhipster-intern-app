import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPessoaContato, PessoaContato } from '../pessoa-contato.model';

import { PessoaContatoService } from './pessoa-contato.service';

describe('PessoaContato Service', () => {
  let service: PessoaContatoService;
  let httpMock: HttpTestingController;
  let elemDefault: IPessoaContato;
  let expectedResult: IPessoaContato | IPessoaContato[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PessoaContatoService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      dataRegistro: currentDate,
      dataImportacao: currentDate,
      dataExclusao: currentDate,
      descricao: 'AAAAAAA',
      contatoDigitalIdent: 'AAAAAAA',
      telefoneNumeroCompleto: 'AAAAAAA',
      telefoneDdd: 0,
      telefoneNumero: 0,
      preferido: false,
      receberPropagandas: false,
      receberConfirmacoes: false,
      possuiWhatsapp: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dataRegistro: currentDate.format(DATE_TIME_FORMAT),
          dataImportacao: currentDate.format(DATE_TIME_FORMAT),
          dataExclusao: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PessoaContato', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dataRegistro: currentDate.format(DATE_TIME_FORMAT),
          dataImportacao: currentDate.format(DATE_TIME_FORMAT),
          dataExclusao: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataRegistro: currentDate,
          dataImportacao: currentDate,
          dataExclusao: currentDate,
        },
        returnedFromService
      );

      service.create(new PessoaContato()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PessoaContato', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dataRegistro: currentDate.format(DATE_TIME_FORMAT),
          dataImportacao: currentDate.format(DATE_TIME_FORMAT),
          dataExclusao: currentDate.format(DATE_TIME_FORMAT),
          descricao: 'BBBBBB',
          contatoDigitalIdent: 'BBBBBB',
          telefoneNumeroCompleto: 'BBBBBB',
          telefoneDdd: 1,
          telefoneNumero: 1,
          preferido: true,
          receberPropagandas: true,
          receberConfirmacoes: true,
          possuiWhatsapp: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataRegistro: currentDate,
          dataImportacao: currentDate,
          dataExclusao: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PessoaContato', () => {
      const patchObject = Object.assign(
        {
          dataImportacao: currentDate.format(DATE_TIME_FORMAT),
          contatoDigitalIdent: 'BBBBBB',
          receberPropagandas: true,
        },
        new PessoaContato()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dataRegistro: currentDate,
          dataImportacao: currentDate,
          dataExclusao: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PessoaContato', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dataRegistro: currentDate.format(DATE_TIME_FORMAT),
          dataImportacao: currentDate.format(DATE_TIME_FORMAT),
          dataExclusao: currentDate.format(DATE_TIME_FORMAT),
          descricao: 'BBBBBB',
          contatoDigitalIdent: 'BBBBBB',
          telefoneNumeroCompleto: 'BBBBBB',
          telefoneDdd: 1,
          telefoneNumero: 1,
          preferido: true,
          receberPropagandas: true,
          receberConfirmacoes: true,
          possuiWhatsapp: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataRegistro: currentDate,
          dataImportacao: currentDate,
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

    it('should delete a PessoaContato', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPessoaContatoToCollectionIfMissing', () => {
      it('should add a PessoaContato to an empty array', () => {
        const pessoaContato: IPessoaContato = { id: 123 };
        expectedResult = service.addPessoaContatoToCollectionIfMissing([], pessoaContato);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pessoaContato);
      });

      it('should not add a PessoaContato to an array that contains it', () => {
        const pessoaContato: IPessoaContato = { id: 123 };
        const pessoaContatoCollection: IPessoaContato[] = [
          {
            ...pessoaContato,
          },
          { id: 456 },
        ];
        expectedResult = service.addPessoaContatoToCollectionIfMissing(pessoaContatoCollection, pessoaContato);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PessoaContato to an array that doesn't contain it", () => {
        const pessoaContato: IPessoaContato = { id: 123 };
        const pessoaContatoCollection: IPessoaContato[] = [{ id: 456 }];
        expectedResult = service.addPessoaContatoToCollectionIfMissing(pessoaContatoCollection, pessoaContato);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pessoaContato);
      });

      it('should add only unique PessoaContato to an array', () => {
        const pessoaContatoArray: IPessoaContato[] = [{ id: 123 }, { id: 456 }, { id: 88677 }];
        const pessoaContatoCollection: IPessoaContato[] = [{ id: 123 }];
        expectedResult = service.addPessoaContatoToCollectionIfMissing(pessoaContatoCollection, ...pessoaContatoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pessoaContato: IPessoaContato = { id: 123 };
        const pessoaContato2: IPessoaContato = { id: 456 };
        expectedResult = service.addPessoaContatoToCollectionIfMissing([], pessoaContato, pessoaContato2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pessoaContato);
        expect(expectedResult).toContain(pessoaContato2);
      });

      it('should accept null and undefined values', () => {
        const pessoaContato: IPessoaContato = { id: 123 };
        expectedResult = service.addPessoaContatoToCollectionIfMissing([], null, pessoaContato, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pessoaContato);
      });

      it('should return initial array if no PessoaContato is added', () => {
        const pessoaContatoCollection: IPessoaContato[] = [{ id: 123 }];
        expectedResult = service.addPessoaContatoToCollectionIfMissing(pessoaContatoCollection, undefined, null);
        expect(expectedResult).toEqual(pessoaContatoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
