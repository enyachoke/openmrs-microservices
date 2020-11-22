import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptComplexUpdateComponent } from 'app/entities/concepts/concept-complex/concept-complex-update.component';
import { ConceptComplexService } from 'app/entities/concepts/concept-complex/concept-complex.service';
import { ConceptComplex } from 'app/shared/model/concepts/concept-complex.model';

describe('Component Tests', () => {
  describe('ConceptComplex Management Update Component', () => {
    let comp: ConceptComplexUpdateComponent;
    let fixture: ComponentFixture<ConceptComplexUpdateComponent>;
    let service: ConceptComplexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptComplexUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConceptComplexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptComplexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptComplexService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConceptComplex(123);
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
        const entity = new ConceptComplex();
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
