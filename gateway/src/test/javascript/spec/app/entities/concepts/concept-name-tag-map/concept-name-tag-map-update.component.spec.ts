import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptNameTagMapUpdateComponent } from 'app/entities/concepts/concept-name-tag-map/concept-name-tag-map-update.component';
import { ConceptNameTagMapService } from 'app/entities/concepts/concept-name-tag-map/concept-name-tag-map.service';
import { ConceptNameTagMap } from 'app/shared/model/concepts/concept-name-tag-map.model';

describe('Component Tests', () => {
  describe('ConceptNameTagMap Management Update Component', () => {
    let comp: ConceptNameTagMapUpdateComponent;
    let fixture: ComponentFixture<ConceptNameTagMapUpdateComponent>;
    let service: ConceptNameTagMapService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptNameTagMapUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConceptNameTagMapUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptNameTagMapUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptNameTagMapService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConceptNameTagMap(123);
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
        const entity = new ConceptNameTagMap();
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
