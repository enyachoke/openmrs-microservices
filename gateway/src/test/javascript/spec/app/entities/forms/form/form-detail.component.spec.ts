import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { FormDetailComponent } from 'app/entities/forms/form/form-detail.component';
import { Form } from 'app/shared/model/forms/form.model';

describe('Component Tests', () => {
  describe('Form Management Detail Component', () => {
    let comp: FormDetailComponent;
    let fixture: ComponentFixture<FormDetailComponent>;
    const route = ({ data: of({ form: new Form(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [FormDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FormDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FormDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load form on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.form).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
