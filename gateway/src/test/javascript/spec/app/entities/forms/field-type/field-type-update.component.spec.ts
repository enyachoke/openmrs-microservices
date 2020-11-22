import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { FieldTypeUpdateComponent } from 'app/entities/forms/field-type/field-type-update.component';
import { FieldTypeService } from 'app/entities/forms/field-type/field-type.service';
import { FieldType } from 'app/shared/model/forms/field-type.model';

describe('Component Tests', () => {
  describe('FieldType Management Update Component', () => {
    let comp: FieldTypeUpdateComponent;
    let fixture: ComponentFixture<FieldTypeUpdateComponent>;
    let service: FieldTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [FieldTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FieldTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FieldTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FieldTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FieldType(123);
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
        const entity = new FieldType();
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
