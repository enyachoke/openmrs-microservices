import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptReferenceTermUpdateComponent } from 'app/entities/concepts/concept-reference-term/concept-reference-term-update.component';
import { ConceptReferenceTermService } from 'app/entities/concepts/concept-reference-term/concept-reference-term.service';
import { ConceptReferenceTerm } from 'app/shared/model/concepts/concept-reference-term.model';

describe('Component Tests', () => {
  describe('ConceptReferenceTerm Management Update Component', () => {
    let comp: ConceptReferenceTermUpdateComponent;
    let fixture: ComponentFixture<ConceptReferenceTermUpdateComponent>;
    let service: ConceptReferenceTermService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptReferenceTermUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConceptReferenceTermUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptReferenceTermUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptReferenceTermService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConceptReferenceTerm(123);
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
        const entity = new ConceptReferenceTerm();
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
