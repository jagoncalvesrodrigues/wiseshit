import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SudaderasDetailComponent } from './sudaderas-detail.component';

describe('Component Tests', () => {
  describe('Sudaderas Management Detail Component', () => {
    let comp: SudaderasDetailComponent;
    let fixture: ComponentFixture<SudaderasDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [SudaderasDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ sudaderas: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(SudaderasDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SudaderasDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sudaderas on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sudaderas).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
