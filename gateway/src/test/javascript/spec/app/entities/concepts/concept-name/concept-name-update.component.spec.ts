import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptNameUpdateComponent } from 'app/entities/concepts/concept-name/concept-name-update.component';
import { ConceptNameService } from 'app/entities/concepts/concept-name/concept-name.service';
import { ConceptName } from 'app/shared/model/concepts/concept-name.model';

describe('Component Tests', () => {
  describe('ConceptName Management Update Component', () => {
    let comp: ConceptNameUpdateComponent;
    let fixture: ComponentFixture<ConceptNameUpdateComponent>;
    let service: ConceptNameService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptNameUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConceptNameUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptNameUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptNameService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConceptName(123);
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
        const entity = new ConceptName();
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
