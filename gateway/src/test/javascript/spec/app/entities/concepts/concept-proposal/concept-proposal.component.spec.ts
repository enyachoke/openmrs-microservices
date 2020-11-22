import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptProposalComponent } from 'app/entities/concepts/concept-proposal/concept-proposal.component';
import { ConceptProposalService } from 'app/entities/concepts/concept-proposal/concept-proposal.service';
import { ConceptProposal } from 'app/shared/model/concepts/concept-proposal.model';

describe('Component Tests', () => {
  describe('ConceptProposal Management Component', () => {
    let comp: ConceptProposalComponent;
    let fixture: ComponentFixture<ConceptProposalComponent>;
    let service: ConceptProposalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptProposalComponent],
      })
        .overrideTemplate(ConceptProposalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptProposalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptProposalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConceptProposal(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conceptProposals && comp.conceptProposals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
