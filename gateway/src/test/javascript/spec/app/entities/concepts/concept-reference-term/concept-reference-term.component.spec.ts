import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptReferenceTermComponent } from 'app/entities/concepts/concept-reference-term/concept-reference-term.component';
import { ConceptReferenceTermService } from 'app/entities/concepts/concept-reference-term/concept-reference-term.service';
import { ConceptReferenceTerm } from 'app/shared/model/concepts/concept-reference-term.model';

describe('Component Tests', () => {
  describe('ConceptReferenceTerm Management Component', () => {
    let comp: ConceptReferenceTermComponent;
    let fixture: ComponentFixture<ConceptReferenceTermComponent>;
    let service: ConceptReferenceTermService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptReferenceTermComponent],
      })
        .overrideTemplate(ConceptReferenceTermComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptReferenceTermComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptReferenceTermService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConceptReferenceTerm(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conceptReferenceTerms && comp.conceptReferenceTerms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
