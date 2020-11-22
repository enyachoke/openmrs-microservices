import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptNumericUpdateComponent } from 'app/entities/concepts/concept-numeric/concept-numeric-update.component';
import { ConceptNumericService } from 'app/entities/concepts/concept-numeric/concept-numeric.service';
import { ConceptNumeric } from 'app/shared/model/concepts/concept-numeric.model';

describe('Component Tests', () => {
  describe('ConceptNumeric Management Update Component', () => {
    let comp: ConceptNumericUpdateComponent;
    let fixture: ComponentFixture<ConceptNumericUpdateComponent>;
    let service: ConceptNumericService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptNumericUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConceptNumericUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptNumericUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptNumericService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConceptNumeric(123);
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
        const entity = new ConceptNumeric();
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
