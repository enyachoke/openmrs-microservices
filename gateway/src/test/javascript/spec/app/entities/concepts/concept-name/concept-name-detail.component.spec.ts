import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptNameDetailComponent } from 'app/entities/concepts/concept-name/concept-name-detail.component';
import { ConceptName } from 'app/shared/model/concepts/concept-name.model';

describe('Component Tests', () => {
  describe('ConceptName Management Detail Component', () => {
    let comp: ConceptNameDetailComponent;
    let fixture: ComponentFixture<ConceptNameDetailComponent>;
    const route = ({ data: of({ conceptName: new ConceptName(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptNameDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConceptNameDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptNameDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conceptName on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conceptName).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
