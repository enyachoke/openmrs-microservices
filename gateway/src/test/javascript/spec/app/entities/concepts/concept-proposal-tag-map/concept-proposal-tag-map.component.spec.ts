import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptProposalTagMapComponent } from 'app/entities/concepts/concept-proposal-tag-map/concept-proposal-tag-map.component';
import { ConceptProposalTagMapService } from 'app/entities/concepts/concept-proposal-tag-map/concept-proposal-tag-map.service';
import { ConceptProposalTagMap } from 'app/shared/model/concepts/concept-proposal-tag-map.model';

describe('Component Tests', () => {
  describe('ConceptProposalTagMap Management Component', () => {
    let comp: ConceptProposalTagMapComponent;
    let fixture: ComponentFixture<ConceptProposalTagMapComponent>;
    let service: ConceptProposalTagMapService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptProposalTagMapComponent],
      })
        .overrideTemplate(ConceptProposalTagMapComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptProposalTagMapComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptProposalTagMapService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConceptProposalTagMap(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conceptProposalTagMaps && comp.conceptProposalTagMaps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
