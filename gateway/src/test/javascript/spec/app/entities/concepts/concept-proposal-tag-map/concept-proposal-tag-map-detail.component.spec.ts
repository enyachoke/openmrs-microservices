import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptProposalTagMapDetailComponent } from 'app/entities/concepts/concept-proposal-tag-map/concept-proposal-tag-map-detail.component';
import { ConceptProposalTagMap } from 'app/shared/model/concepts/concept-proposal-tag-map.model';

describe('Component Tests', () => {
  describe('ConceptProposalTagMap Management Detail Component', () => {
    let comp: ConceptProposalTagMapDetailComponent;
    let fixture: ComponentFixture<ConceptProposalTagMapDetailComponent>;
    const route = ({ data: of({ conceptProposalTagMap: new ConceptProposalTagMap(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptProposalTagMapDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConceptProposalTagMapDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptProposalTagMapDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conceptProposalTagMap on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conceptProposalTagMap).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
