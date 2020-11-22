import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FieldService } from 'app/entities/forms/field/field.service';
import { IField, Field } from 'app/shared/model/forms/field.model';

describe('Service Tests', () => {
  describe('Field Service', () => {
    let injector: TestBed;
    let service: FieldService;
    let httpMock: HttpTestingController;
    let elemDefault: IField;
    let expectedResult: IField | IField[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FieldService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Field(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Field', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Field()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Field', () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            name: 'BBBBBB',
            description: 'BBBBBB',
            conceptUuid: 'BBBBBB',
            tableName: 'BBBBBB',
            attributesName: 'BBBBBB',
            defaultValue: 'BBBBBB',
            selectMultiple: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Field', () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            name: 'BBBBBB',
            description: 'BBBBBB',
            conceptUuid: 'BBBBBB',
            tableName: 'BBBBBB',
            attributesName: 'BBBBBB',
            defaultValue: 'BBBBBB',
            selectMultiple: true,
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

      it('should delete a Field', () => {
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
