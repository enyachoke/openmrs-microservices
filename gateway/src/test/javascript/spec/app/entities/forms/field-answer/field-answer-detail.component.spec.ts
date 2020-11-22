import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { FieldAnswerDetailComponent } from 'app/entities/forms/field-answer/field-answer-detail.component';
import { FieldAnswer } from 'app/shared/model/forms/field-answer.model';

describe('Component Tests', () => {
  describe('FieldAnswer Management Detail Component', () => {
    let comp: FieldAnswerDetailComponent;
    let fixture: ComponentFixture<FieldAnswerDetailComponent>;
    const route = ({ data: of({ fieldAnswer: new FieldAnswer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [FieldAnswerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FieldAnswerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FieldAnswerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fieldAnswer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fieldAnswer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
