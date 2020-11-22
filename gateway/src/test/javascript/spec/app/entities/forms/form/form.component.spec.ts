import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { FormComponent } from 'app/entities/forms/form/form.component';
import { FormService } from 'app/entities/forms/form/form.service';
import { Form } from 'app/shared/model/forms/form.model';

describe('Component Tests', () => {
  describe('Form Management Component', () => {
    let comp: FormComponent;
    let fixture: ComponentFixture<FormComponent>;
    let service: FormService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [FormComponent],
      })
        .overrideTemplate(FormComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Form(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.forms && comp.forms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
