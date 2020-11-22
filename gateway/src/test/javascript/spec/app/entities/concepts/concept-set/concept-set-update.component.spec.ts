import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptSetUpdateComponent } from 'app/entities/concepts/concept-set/concept-set-update.component';
import { ConceptSetService } from 'app/entities/concepts/concept-set/concept-set.service';
import { ConceptSet } from 'app/shared/model/concepts/concept-set.model';

describe('Component Tests', () => {
  describe('ConceptSet Management Update Component', () => {
    let comp: ConceptSetUpdateComponent;
    let fixture: ComponentFixture<ConceptSetUpdateComponent>;
    let service: ConceptSetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptSetUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConceptSetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptSetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptSetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConceptSet(123);
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
        const entity = new ConceptSet();
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
