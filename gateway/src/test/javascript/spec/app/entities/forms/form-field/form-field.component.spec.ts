import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { FormFieldComponent } from 'app/entities/forms/form-field/form-field.component';
import { FormFieldService } from 'app/entities/forms/form-field/form-field.service';
import { FormField } from 'app/shared/model/forms/form-field.model';

describe('Component Tests', () => {
  describe('FormField Management Component', () => {
    let comp: FormFieldComponent;
    let fixture: ComponentFixture<FormFieldComponent>;
    let service: FormFieldService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [FormFieldComponent],
      })
        .overrideTemplate(FormFieldComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormFieldComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormFieldService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FormField(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.formFields && comp.formFields[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
