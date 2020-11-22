import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptSetComponent } from 'app/entities/concepts/concept-set/concept-set.component';
import { ConceptSetService } from 'app/entities/concepts/concept-set/concept-set.service';
import { ConceptSet } from 'app/shared/model/concepts/concept-set.model';

describe('Component Tests', () => {
  describe('ConceptSet Management Component', () => {
    let comp: ConceptSetComponent;
    let fixture: ComponentFixture<ConceptSetComponent>;
    let service: ConceptSetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptSetComponent],
      })
        .overrideTemplate(ConceptSetComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptSetComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptSetService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConceptSet(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conceptSets && comp.conceptSets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
