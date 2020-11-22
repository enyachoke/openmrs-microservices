import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptAnswerComponent } from 'app/entities/concepts/concept-answer/concept-answer.component';
import { ConceptAnswerService } from 'app/entities/concepts/concept-answer/concept-answer.service';
import { ConceptAnswer } from 'app/shared/model/concepts/concept-answer.model';

describe('Component Tests', () => {
  describe('ConceptAnswer Management Component', () => {
    let comp: ConceptAnswerComponent;
    let fixture: ComponentFixture<ConceptAnswerComponent>;
    let service: ConceptAnswerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptAnswerComponent],
      })
        .overrideTemplate(ConceptAnswerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptAnswerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptAnswerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConceptAnswer(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conceptAnswers && comp.conceptAnswers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
