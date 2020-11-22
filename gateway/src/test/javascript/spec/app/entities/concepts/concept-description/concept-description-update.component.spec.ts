import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptDescriptionUpdateComponent } from 'app/entities/concepts/concept-description/concept-description-update.component';
import { ConceptDescriptionService } from 'app/entities/concepts/concept-description/concept-description.service';
import { ConceptDescription } from 'app/shared/model/concepts/concept-description.model';

describe('Component Tests', () => {
  describe('ConceptDescription Management Update Component', () => {
    let comp: ConceptDescriptionUpdateComponent;
    let fixture: ComponentFixture<ConceptDescriptionUpdateComponent>;
    let service: ConceptDescriptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptDescriptionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConceptDescriptionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptDescriptionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptDescriptionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConceptDescription(123);
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
        const entity = new ConceptDescription();
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
