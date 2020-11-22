import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DrugOrderService } from 'app/entities/orders/drug-order/drug-order.service';
import { IDrugOrder, DrugOrder } from 'app/shared/model/orders/drug-order.model';

describe('Service Tests', () => {
  describe('DrugOrder Service', () => {
    let injector: TestBed;
    let service: DrugOrderService;
    let httpMock: HttpTestingController;
    let elemDefault: IDrugOrder;
    let expectedResult: IDrugOrder | IDrugOrder[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DrugOrderService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DrugOrder(0, 'AAAAAAA', 'AAAAAAA', 0, 0, 'AAAAAAA', 'AAAAAAA', false, false, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DrugOrder', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DrugOrder()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DrugOrder', () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            drugInventoryUuid: 'BBBBBB',
            dose: 1,
            equivalentDailyDose: 1,
            units: 'BBBBBB',
            frequency: 'BBBBBB',
            prn: true,
            complex: true,
            quantity: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DrugOrder', () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            drugInventoryUuid: 'BBBBBB',
            dose: 1,
            equivalentDailyDose: 1,
            units: 'BBBBBB',
            frequency: 'BBBBBB',
            prn: true,
            complex: true,
            quantity: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DrugOrder', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
