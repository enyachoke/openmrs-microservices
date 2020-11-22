import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptNameTagDetailComponent } from 'app/entities/concepts/concept-name-tag/concept-name-tag-detail.component';
import { ConceptNameTag } from 'app/shared/model/concepts/concept-name-tag.model';

describe('Component Tests', () => {
  describe('ConceptNameTag Management Detail Component', () => {
    let comp: ConceptNameTagDetailComponent;
    let fixture: ComponentFixture<ConceptNameTagDetailComponent>;
    const route = ({ data: of({ conceptNameTag: new ConceptNameTag(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptNameTagDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConceptNameTagDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptNameTagDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conceptNameTag on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conceptNameTag).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
