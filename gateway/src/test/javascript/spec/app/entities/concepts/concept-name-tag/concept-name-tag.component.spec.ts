import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptNameTagComponent } from 'app/entities/concepts/concept-name-tag/concept-name-tag.component';
import { ConceptNameTagService } from 'app/entities/concepts/concept-name-tag/concept-name-tag.service';
import { ConceptNameTag } from 'app/shared/model/concepts/concept-name-tag.model';

describe('Component Tests', () => {
  describe('ConceptNameTag Management Component', () => {
    let comp: ConceptNameTagComponent;
    let fixture: ComponentFixture<ConceptNameTagComponent>;
    let service: ConceptNameTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptNameTagComponent],
      })
        .overrideTemplate(ConceptNameTagComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptNameTagComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptNameTagService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConceptNameTag(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conceptNameTags && comp.conceptNameTags[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
