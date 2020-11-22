import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { FieldTypeComponent } from 'app/entities/forms/field-type/field-type.component';
import { FieldTypeService } from 'app/entities/forms/field-type/field-type.service';
import { FieldType } from 'app/shared/model/forms/field-type.model';

describe('Component Tests', () => {
  describe('FieldType Management Component', () => {
    let comp: FieldTypeComponent;
    let fixture: ComponentFixture<FieldTypeComponent>;
    let service: FieldTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [FieldTypeComponent],
      })
        .overrideTemplate(FieldTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FieldTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FieldTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FieldType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fieldTypes && comp.fieldTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
