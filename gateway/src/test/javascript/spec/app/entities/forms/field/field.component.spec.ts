import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { FieldComponent } from 'app/entities/forms/field/field.component';
import { FieldService } from 'app/entities/forms/field/field.service';
import { Field } from 'app/shared/model/forms/field.model';

describe('Component Tests', () => {
  describe('Field Management Component', () => {
    let comp: FieldComponent;
    let fixture: ComponentFixture<FieldComponent>;
    let service: FieldService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [FieldComponent],
      })
        .overrideTemplate(FieldComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FieldComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FieldService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Field(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fields && comp.fields[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
