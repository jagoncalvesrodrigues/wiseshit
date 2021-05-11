import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccesoriosDetailComponent } from './accesorios-detail.component';

describe('Component Tests', () => {
  describe('Accesorios Management Detail Component', () => {
    let comp: AccesoriosDetailComponent;
    let fixture: ComponentFixture<AccesoriosDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AccesoriosDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ accesorios: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AccesoriosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccesoriosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load accesorios on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accesorios).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
