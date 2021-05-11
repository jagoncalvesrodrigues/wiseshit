import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CamisetasDetailComponent } from './camisetas-detail.component';

describe('Component Tests', () => {
  describe('Camisetas Management Detail Component', () => {
    let comp: CamisetasDetailComponent;
    let fixture: ComponentFixture<CamisetasDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CamisetasDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ camisetas: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CamisetasDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CamisetasDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load camisetas on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.camisetas).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
