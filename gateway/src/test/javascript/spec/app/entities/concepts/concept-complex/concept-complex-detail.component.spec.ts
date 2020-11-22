import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptComplexDetailComponent } from 'app/entities/concepts/concept-complex/concept-complex-detail.component';
import { ConceptComplex } from 'app/shared/model/concepts/concept-complex.model';

describe('Component Tests', () => {
  describe('ConceptComplex Management Detail Component', () => {
    let comp: ConceptComplexDetailComponent;
    let fixture: ComponentFixture<ConceptComplexDetailComponent>;
    const route = ({ data: of({ conceptComplex: new ConceptComplex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptComplexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConceptComplexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptComplexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conceptComplex on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conceptComplex).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
