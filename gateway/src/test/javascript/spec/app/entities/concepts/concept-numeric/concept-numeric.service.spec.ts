import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ConceptNumericService } from 'app/entities/concepts/concept-numeric/concept-numeric.service';
import { IConceptNumeric, ConceptNumeric } from 'app/shared/model/concepts/concept-numeric.model';

describe('Service Tests', () => {
  describe('ConceptNumeric Service', () => {
    let injector: TestBed;
    let service: ConceptNumericService;
    let httpMock: HttpTestingController;
    let elemDefault: IConceptNumeric;
    let expectedResult: IConceptNumeric | IConceptNumeric[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ConceptNumericService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ConceptNumeric(0, 'AAAAAAA', 0, 0, 0, 0, 0, 0, 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ConceptNumeric', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ConceptNumeric()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ConceptNumeric', () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            hiAbsolute: 1,
            hiNormal: 1,
            hiCritical: 1,
            lowAbsolute: 1,
            lowNormal: 1,
            lowCritical: 1,
            units: 'BBBBBB',
            precise: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ConceptNumeric', () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            hiAbsolute: 1,
            hiNormal: 1,
            hiCritical: 1,
            lowAbsolute: 1,
            lowNormal: 1,
            lowCritical: 1,
            units: 'BBBBBB',
            precise: true,
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

      it('should delete a ConceptNumeric', () => {
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
