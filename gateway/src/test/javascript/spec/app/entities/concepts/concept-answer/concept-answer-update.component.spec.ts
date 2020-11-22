import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptAnswerUpdateComponent } from 'app/entities/concepts/concept-answer/concept-answer-update.component';
import { ConceptAnswerService } from 'app/entities/concepts/concept-answer/concept-answer.service';
import { ConceptAnswer } from 'app/shared/model/concepts/concept-answer.model';

describe('Component Tests', () => {
  describe('ConceptAnswer Management Update Component', () => {
    let comp: ConceptAnswerUpdateComponent;
    let fixture: ComponentFixture<ConceptAnswerUpdateComponent>;
    let service: ConceptAnswerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptAnswerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConceptAnswerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptAnswerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptAnswerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConceptAnswer(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConceptAnswer();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
