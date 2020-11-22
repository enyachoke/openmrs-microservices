import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptAnswerDetailComponent } from 'app/entities/concepts/concept-answer/concept-answer-detail.component';
import { ConceptAnswer } from 'app/shared/model/concepts/concept-answer.model';

describe('Component Tests', () => {
  describe('ConceptAnswer Management Detail Component', () => {
    let comp: ConceptAnswerDetailComponent;
    let fixture: ComponentFixture<ConceptAnswerDetailComponent>;
    const route = ({ data: of({ conceptAnswer: new ConceptAnswer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptAnswerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConceptAnswerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptAnswerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conceptAnswer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conceptAnswer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
