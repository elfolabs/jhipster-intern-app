import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPessoaRedeSocial, PessoaRedeSocial } from '../pessoa-rede-social.model';
import { PessoaRedeSocialService } from '../service/pessoa-rede-social.service';

import { PessoaRedeSocialRoutingResolveService } from './pessoa-rede-social-routing-resolve.service';

describe('PessoaRedeSocial routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PessoaRedeSocialRoutingResolveService;
  let service: PessoaRedeSocialService;
  let resultPessoaRedeSocial: IPessoaRedeSocial | undefined;

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
    routingResolveService = TestBed.inject(PessoaRedeSocialRoutingResolveService);
    service = TestBed.inject(PessoaRedeSocialService);
    resultPessoaRedeSocial = undefined;
  });

  describe('resolve', () => {
    it('should return IPessoaRedeSocial returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPessoaRedeSocial = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPessoaRedeSocial).toEqual({ id: 123 });
    });

    it('should return new IPessoaRedeSocial if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPessoaRedeSocial = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPessoaRedeSocial).toEqual(new PessoaRedeSocial());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PessoaRedeSocial })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPessoaRedeSocial = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPessoaRedeSocial).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
