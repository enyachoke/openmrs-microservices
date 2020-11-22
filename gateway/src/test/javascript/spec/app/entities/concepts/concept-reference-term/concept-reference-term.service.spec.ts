import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ConceptReferenceTermService } from 'app/entities/concepts/concept-reference-term/concept-reference-term.service';
import { IConceptReferenceTerm, ConceptReferenceTerm } from 'app/shared/model/concepts/concept-reference-term.model';

describe('Service Tests', () => {
  describe('ConceptReferenceTerm Service', () => {
    let injector: TestBed;
    let service: ConceptReferenceTermService;
    let httpMock: HttpTestingController;
    let elemDefault: IConceptReferenceTerm;
    let expectedResult: IConceptReferenceTerm | IConceptReferenceTerm[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ConceptReferenceTermService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ConceptReferenceTerm(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ConceptReferenceTerm', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ConceptReferenceTerm()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ConceptReferenceTerm', () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            name: 'BBBBBB',
            code: 'BBBBBB',
            version: 'BBBBBB',
            description: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ConceptReferenceTerm', () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            name: 'BBBBBB',
            code: 'BBBBBB',
            version: 'BBBBBB',
            description: 'BBBBBB',
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

      it('should delete a ConceptReferenceTerm', () => {
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
