import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { DrugOrderDetailComponent } from 'app/entities/orders/drug-order/drug-order-detail.component';
import { DrugOrder } from 'app/shared/model/orders/drug-order.model';

describe('Component Tests', () => {
  describe('DrugOrder Management Detail Component', () => {
    let comp: DrugOrderDetailComponent;
    let fixture: ComponentFixture<DrugOrderDetailComponent>;
    const route = ({ data: of({ drugOrder: new DrugOrder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [DrugOrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DrugOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DrugOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load drugOrder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.drugOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
