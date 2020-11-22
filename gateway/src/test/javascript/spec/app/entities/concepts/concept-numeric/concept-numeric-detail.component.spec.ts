import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptNumericDetailComponent } from 'app/entities/concepts/concept-numeric/concept-numeric-detail.component';
import { ConceptNumeric } from 'app/shared/model/concepts/concept-numeric.model';

describe('Component Tests', () => {
  describe('ConceptNumeric Management Detail Component', () => {
    let comp: ConceptNumericDetailComponent;
    let fixture: ComponentFixture<ConceptNumericDetailComponent>;
    const route = ({ data: of({ conceptNumeric: new ConceptNumeric(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptNumericDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConceptNumericDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptNumericDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conceptNumeric on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conceptNumeric).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
