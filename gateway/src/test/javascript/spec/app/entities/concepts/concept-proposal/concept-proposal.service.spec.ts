import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ConceptProposalService } from 'app/entities/concepts/concept-proposal/concept-proposal.service';
import { IConceptProposal, ConceptProposal } from 'app/shared/model/concepts/concept-proposal.model';

describe('Service Tests', () => {
  describe('ConceptProposal Service', () => {
    let injector: TestBed;
    let service: ConceptProposalService;
    let httpMock: HttpTestingController;
    let elemDefault: IConceptProposal;
    let expectedResult: IConceptProposal | IConceptProposal[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ConceptProposalService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ConceptProposal(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ConceptProposal', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ConceptProposal()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ConceptProposal', () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            encounter: 'BBBBBB',
            originalText: 'BBBBBB',
            finalText: 'BBBBBB',
            obsUuid: 'BBBBBB',
            obsConceptUuid: 'BBBBBB',
            state: 'BBBBBB',
            comments: 'BBBBBB',
            locale: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ConceptProposal', () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            encounter: 'BBBBBB',
            originalText: 'BBBBBB',
            finalText: 'BBBBBB',
            obsUuid: 'BBBBBB',
            obsConceptUuid: 'BBBBBB',
            state: 'BBBBBB',
            comments: 'BBBBBB',
            locale: 'BBBBBB',
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

      it('should delete a ConceptProposal', () => {
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
