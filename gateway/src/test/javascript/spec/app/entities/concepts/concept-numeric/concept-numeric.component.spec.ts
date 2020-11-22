import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptNumericComponent } from 'app/entities/concepts/concept-numeric/concept-numeric.component';
import { ConceptNumericService } from 'app/entities/concepts/concept-numeric/concept-numeric.service';
import { ConceptNumeric } from 'app/shared/model/concepts/concept-numeric.model';

describe('Component Tests', () => {
  describe('ConceptNumeric Management Component', () => {
    let comp: ConceptNumericComponent;
    let fixture: ComponentFixture<ConceptNumericComponent>;
    let service: ConceptNumericService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptNumericComponent],
      })
        .overrideTemplate(ConceptNumericComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptNumericComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptNumericService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConceptNumeric(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conceptNumerics && comp.conceptNumerics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
