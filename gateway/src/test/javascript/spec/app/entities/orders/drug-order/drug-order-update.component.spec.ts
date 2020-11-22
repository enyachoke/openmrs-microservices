import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { DrugOrderUpdateComponent } from 'app/entities/orders/drug-order/drug-order-update.component';
import { DrugOrderService } from 'app/entities/orders/drug-order/drug-order.service';
import { DrugOrder } from 'app/shared/model/orders/drug-order.model';

describe('Component Tests', () => {
  describe('DrugOrder Management Update Component', () => {
    let comp: DrugOrderUpdateComponent;
    let fixture: ComponentFixture<DrugOrderUpdateComponent>;
    let service: DrugOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [DrugOrderUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DrugOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DrugOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DrugOrderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DrugOrder(123);
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
        const entity = new DrugOrder();
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
