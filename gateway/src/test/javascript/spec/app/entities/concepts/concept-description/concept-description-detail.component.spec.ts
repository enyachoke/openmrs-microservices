import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptDescriptionDetailComponent } from 'app/entities/concepts/concept-description/concept-description-detail.component';
import { ConceptDescription } from 'app/shared/model/concepts/concept-description.model';

describe('Component Tests', () => {
  describe('ConceptDescription Management Detail Component', () => {
    let comp: ConceptDescriptionDetailComponent;
    let fixture: ComponentFixture<ConceptDescriptionDetailComponent>;
    const route = ({ data: of({ conceptDescription: new ConceptDescription(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptDescriptionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConceptDescriptionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptDescriptionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conceptDescription on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conceptDescription).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
