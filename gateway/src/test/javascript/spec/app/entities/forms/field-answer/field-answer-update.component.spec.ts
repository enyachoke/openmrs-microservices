import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { FieldAnswerUpdateComponent } from 'app/entities/forms/field-answer/field-answer-update.component';
import { FieldAnswerService } from 'app/entities/forms/field-answer/field-answer.service';
import { FieldAnswer } from 'app/shared/model/forms/field-answer.model';

describe('Component Tests', () => {
  describe('FieldAnswer Management Update Component', () => {
    let comp: FieldAnswerUpdateComponent;
    let fixture: ComponentFixture<FieldAnswerUpdateComponent>;
    let service: FieldAnswerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [FieldAnswerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FieldAnswerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FieldAnswerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FieldAnswerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FieldAnswer(123);
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
        const entity = new FieldAnswer();
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
