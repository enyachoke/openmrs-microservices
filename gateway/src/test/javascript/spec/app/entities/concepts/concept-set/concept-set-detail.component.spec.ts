import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptSetDetailComponent } from 'app/entities/concepts/concept-set/concept-set-detail.component';
import { ConceptSet } from 'app/shared/model/concepts/concept-set.model';

describe('Component Tests', () => {
  describe('ConceptSet Management Detail Component', () => {
    let comp: ConceptSetDetailComponent;
    let fixture: ComponentFixture<ConceptSetDetailComponent>;
    const route = ({ data: of({ conceptSet: new ConceptSet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptSetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConceptSetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptSetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conceptSet on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conceptSet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
