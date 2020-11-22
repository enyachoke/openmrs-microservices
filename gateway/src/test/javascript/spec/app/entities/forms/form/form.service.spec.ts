import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FormService } from 'app/entities/forms/form/form.service';
import { IForm, Form } from 'app/shared/model/forms/form.model';

describe('Service Tests', () => {
  describe('Form Service', () => {
    let injector: TestBed;
    let service: FormService;
    let httpMock: HttpTestingController;
    let elemDefault: IForm;
    let expectedResult: IForm | IForm[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FormService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Form(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, false, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Form', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Form()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Form', () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            name: 'BBBBBB',
            version: 'BBBBBB',
            build: 1,
            published: true,
            description: 'BBBBBB',
            encounterType: 'BBBBBB',
            template: 'BBBBBB',
            xslt: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Form', () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            name: 'BBBBBB',
            version: 'BBBBBB',
            build: 1,
            published: true,
            description: 'BBBBBB',
            encounterType: 'BBBBBB',
            template: 'BBBBBB',
            xslt: 'BBBBBB',
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

      it('should delete a Form', () => {
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
