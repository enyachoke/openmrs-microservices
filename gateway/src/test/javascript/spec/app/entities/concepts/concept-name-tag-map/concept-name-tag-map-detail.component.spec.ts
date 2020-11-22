import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptNameTagMapDetailComponent } from 'app/entities/concepts/concept-name-tag-map/concept-name-tag-map-detail.component';
import { ConceptNameTagMap } from 'app/shared/model/concepts/concept-name-tag-map.model';

describe('Component Tests', () => {
  describe('ConceptNameTagMap Management Detail Component', () => {
    let comp: ConceptNameTagMapDetailComponent;
    let fixture: ComponentFixture<ConceptNameTagMapDetailComponent>;
    const route = ({ data: of({ conceptNameTagMap: new ConceptNameTagMap(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptNameTagMapDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConceptNameTagMapDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptNameTagMapDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conceptNameTagMap on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conceptNameTagMap).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
