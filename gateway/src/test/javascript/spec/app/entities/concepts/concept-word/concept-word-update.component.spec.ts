import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptWordUpdateComponent } from 'app/entities/concepts/concept-word/concept-word-update.component';
import { ConceptWordService } from 'app/entities/concepts/concept-word/concept-word.service';
import { ConceptWord } from 'app/shared/model/concepts/concept-word.model';

describe('Component Tests', () => {
  describe('ConceptWord Management Update Component', () => {
    let comp: ConceptWordUpdateComponent;
    let fixture: ComponentFixture<ConceptWordUpdateComponent>;
    let service: ConceptWordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptWordUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConceptWordUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptWordUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptWordService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConceptWord(123);
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
        const entity = new ConceptWord();
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
