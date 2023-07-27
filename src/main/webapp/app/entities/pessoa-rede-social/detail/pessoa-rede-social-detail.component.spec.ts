import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PessoaRedeSocialDetailComponent } from './pessoa-rede-social-detail.component';

describe('PessoaRedeSocial Management Detail Component', () => {
  let comp: PessoaRedeSocialDetailComponent;
  let fixture: ComponentFixture<PessoaRedeSocialDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PessoaRedeSocialDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pessoaRedeSocial: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PessoaRedeSocialDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PessoaRedeSocialDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pessoaRedeSocial on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pessoaRedeSocial).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
