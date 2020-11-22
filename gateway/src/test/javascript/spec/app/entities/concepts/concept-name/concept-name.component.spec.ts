import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptNameComponent } from 'app/entities/concepts/concept-name/concept-name.component';
import { ConceptNameService } from 'app/entities/concepts/concept-name/concept-name.service';
import { ConceptName } from 'app/shared/model/concepts/concept-name.model';

describe('Component Tests', () => {
  describe('ConceptName Management Component', () => {
    let comp: ConceptNameComponent;
    let fixture: ComponentFixture<ConceptNameComponent>;
    let service: ConceptNameService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptNameComponent],
      })
        .overrideTemplate(ConceptNameComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptNameComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptNameService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConceptName(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conceptNames && comp.conceptNames[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
