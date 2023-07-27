import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PessoaContatoDetailComponent } from './pessoa-contato-detail.component';

describe('PessoaContato Management Detail Component', () => {
  let comp: PessoaContatoDetailComponent;
  let fixture: ComponentFixture<PessoaContatoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PessoaContatoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pessoaContato: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PessoaContatoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PessoaContatoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pessoaContato on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pessoaContato).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
