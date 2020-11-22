import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptDetailComponent } from 'app/entities/concepts/concept/concept-detail.component';
import { Concept } from 'app/shared/model/concepts/concept.model';

describe('Component Tests', () => {
  describe('Concept Management Detail Component', () => {
    let comp: ConceptDetailComponent;
    let fixture: ComponentFixture<ConceptDetailComponent>;
    const route = ({ data: of({ concept: new Concept(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConceptDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load concept on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.concept).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
