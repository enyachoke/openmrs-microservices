import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptWordDetailComponent } from 'app/entities/concepts/concept-word/concept-word-detail.component';
import { ConceptWord } from 'app/shared/model/concepts/concept-word.model';

describe('Component Tests', () => {
  describe('ConceptWord Management Detail Component', () => {
    let comp: ConceptWordDetailComponent;
    let fixture: ComponentFixture<ConceptWordDetailComponent>;
    const route = ({ data: of({ conceptWord: new ConceptWord(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptWordDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConceptWordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptWordDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conceptWord on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conceptWord).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
