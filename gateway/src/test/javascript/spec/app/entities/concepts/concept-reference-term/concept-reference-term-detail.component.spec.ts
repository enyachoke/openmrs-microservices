import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptReferenceTermDetailComponent } from 'app/entities/concepts/concept-reference-term/concept-reference-term-detail.component';
import { ConceptReferenceTerm } from 'app/shared/model/concepts/concept-reference-term.model';

describe('Component Tests', () => {
  describe('ConceptReferenceTerm Management Detail Component', () => {
    let comp: ConceptReferenceTermDetailComponent;
    let fixture: ComponentFixture<ConceptReferenceTermDetailComponent>;
    const route = ({ data: of({ conceptReferenceTerm: new ConceptReferenceTerm(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptReferenceTermDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConceptReferenceTermDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptReferenceTermDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conceptReferenceTerm on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conceptReferenceTerm).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
