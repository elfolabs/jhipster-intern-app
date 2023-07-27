import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPessoaContato, PessoaContato } from '../pessoa-contato.model';
import { PessoaContatoService } from '../service/pessoa-contato.service';

import { PessoaContatoRoutingResolveService } from './pessoa-contato-routing-resolve.service';

describe('PessoaContato routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PessoaContatoRoutingResolveService;
  let service: PessoaContatoService;
  let resultPessoaContato: IPessoaContato | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(PessoaContatoRoutingResolveService);
    service = TestBed.inject(PessoaContatoService);
    resultPessoaContato = undefined;
  });

  describe('resolve', () => {
    it('should return IPessoaContato returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPessoaContato = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPessoaContato).toEqual({ id: 123 });
    });

    it('should return new IPessoaContato if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPessoaContato = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPessoaContato).toEqual(new PessoaContato());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PessoaContato })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPessoaContato = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPessoaContato).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
