import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptWordComponent } from 'app/entities/concepts/concept-word/concept-word.component';
import { ConceptWordService } from 'app/entities/concepts/concept-word/concept-word.service';
import { ConceptWord } from 'app/shared/model/concepts/concept-word.model';

describe('Component Tests', () => {
  describe('ConceptWord Management Component', () => {
    let comp: ConceptWordComponent;
    let fixture: ComponentFixture<ConceptWordComponent>;
    let service: ConceptWordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptWordComponent],
      })
        .overrideTemplate(ConceptWordComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptWordComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptWordService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConceptWord(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conceptWords && comp.conceptWords[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
