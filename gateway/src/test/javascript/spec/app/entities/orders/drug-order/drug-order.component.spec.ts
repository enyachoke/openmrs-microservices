import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { DrugOrderComponent } from 'app/entities/orders/drug-order/drug-order.component';
import { DrugOrderService } from 'app/entities/orders/drug-order/drug-order.service';
import { DrugOrder } from 'app/shared/model/orders/drug-order.model';

describe('Component Tests', () => {
  describe('DrugOrder Management Component', () => {
    let comp: DrugOrderComponent;
    let fixture: ComponentFixture<DrugOrderComponent>;
    let service: DrugOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [DrugOrderComponent],
      })
        .overrideTemplate(DrugOrderComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DrugOrderComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DrugOrderService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DrugOrder(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.drugOrders && comp.drugOrders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
