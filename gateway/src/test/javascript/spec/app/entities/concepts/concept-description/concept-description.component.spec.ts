import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptDescriptionComponent } from 'app/entities/concepts/concept-description/concept-description.component';
import { ConceptDescriptionService } from 'app/entities/concepts/concept-description/concept-description.service';
import { ConceptDescription } from 'app/shared/model/concepts/concept-description.model';

describe('Component Tests', () => {
  describe('ConceptDescription Management Component', () => {
    let comp: ConceptDescriptionComponent;
    let fixture: ComponentFixture<ConceptDescriptionComponent>;
    let service: ConceptDescriptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptDescriptionComponent],
      })
        .overrideTemplate(ConceptDescriptionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptDescriptionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptDescriptionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConceptDescription(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conceptDescriptions && comp.conceptDescriptions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
