import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { FieldTypeDetailComponent } from 'app/entities/forms/field-type/field-type-detail.component';
import { FieldType } from 'app/shared/model/forms/field-type.model';

describe('Component Tests', () => {
  describe('FieldType Management Detail Component', () => {
    let comp: FieldTypeDetailComponent;
    let fixture: ComponentFixture<FieldTypeDetailComponent>;
    const route = ({ data: of({ fieldType: new FieldType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [FieldTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FieldTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FieldTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fieldType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fieldType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
