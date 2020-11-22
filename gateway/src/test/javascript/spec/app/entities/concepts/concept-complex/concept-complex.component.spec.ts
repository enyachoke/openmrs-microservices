import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptComplexComponent } from 'app/entities/concepts/concept-complex/concept-complex.component';
import { ConceptComplexService } from 'app/entities/concepts/concept-complex/concept-complex.service';
import { ConceptComplex } from 'app/shared/model/concepts/concept-complex.model';

describe('Component Tests', () => {
  describe('ConceptComplex Management Component', () => {
    let comp: ConceptComplexComponent;
    let fixture: ComponentFixture<ConceptComplexComponent>;
    let service: ConceptComplexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptComplexComponent],
      })
        .overrideTemplate(ConceptComplexComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptComplexComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptComplexService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConceptComplex(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conceptComplexes && comp.conceptComplexes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
