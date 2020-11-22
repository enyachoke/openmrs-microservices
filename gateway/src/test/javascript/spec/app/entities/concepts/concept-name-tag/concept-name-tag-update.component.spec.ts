import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptNameTagUpdateComponent } from 'app/entities/concepts/concept-name-tag/concept-name-tag-update.component';
import { ConceptNameTagService } from 'app/entities/concepts/concept-name-tag/concept-name-tag.service';
import { ConceptNameTag } from 'app/shared/model/concepts/concept-name-tag.model';

describe('Component Tests', () => {
  describe('ConceptNameTag Management Update Component', () => {
    let comp: ConceptNameTagUpdateComponent;
    let fixture: ComponentFixture<ConceptNameTagUpdateComponent>;
    let service: ConceptNameTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptNameTagUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConceptNameTagUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptNameTagUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptNameTagService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConceptNameTag(123);
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
        const entity = new ConceptNameTag();
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
