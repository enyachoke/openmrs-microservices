import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptProposalDetailComponent } from 'app/entities/concepts/concept-proposal/concept-proposal-detail.component';
import { ConceptProposal } from 'app/shared/model/concepts/concept-proposal.model';

describe('Component Tests', () => {
  describe('ConceptProposal Management Detail Component', () => {
    let comp: ConceptProposalDetailComponent;
    let fixture: ComponentFixture<ConceptProposalDetailComponent>;
    const route = ({ data: of({ conceptProposal: new ConceptProposal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptProposalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConceptProposalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptProposalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conceptProposal on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conceptProposal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
