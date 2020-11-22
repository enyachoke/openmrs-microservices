import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { FieldAnswerComponent } from 'app/entities/forms/field-answer/field-answer.component';
import { FieldAnswerService } from 'app/entities/forms/field-answer/field-answer.service';
import { FieldAnswer } from 'app/shared/model/forms/field-answer.model';

describe('Component Tests', () => {
  describe('FieldAnswer Management Component', () => {
    let comp: FieldAnswerComponent;
    let fixture: ComponentFixture<FieldAnswerComponent>;
    let service: FieldAnswerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [FieldAnswerComponent],
      })
        .overrideTemplate(FieldAnswerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FieldAnswerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FieldAnswerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FieldAnswer(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fieldAnswers && comp.fieldAnswers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
