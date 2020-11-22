import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptNameTagMapComponent } from 'app/entities/concepts/concept-name-tag-map/concept-name-tag-map.component';
import { ConceptNameTagMapService } from 'app/entities/concepts/concept-name-tag-map/concept-name-tag-map.service';
import { ConceptNameTagMap } from 'app/shared/model/concepts/concept-name-tag-map.model';

describe('Component Tests', () => {
  describe('ConceptNameTagMap Management Component', () => {
    let comp: ConceptNameTagMapComponent;
    let fixture: ComponentFixture<ConceptNameTagMapComponent>;
    let service: ConceptNameTagMapService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptNameTagMapComponent],
      })
        .overrideTemplate(ConceptNameTagMapComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptNameTagMapComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptNameTagMapService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConceptNameTagMap(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conceptNameTagMaps && comp.conceptNameTagMaps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
