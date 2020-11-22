import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptProposalTagMapUpdateComponent } from 'app/entities/concepts/concept-proposal-tag-map/concept-proposal-tag-map-update.component';
import { ConceptProposalTagMapService } from 'app/entities/concepts/concept-proposal-tag-map/concept-proposal-tag-map.service';
import { ConceptProposalTagMap } from 'app/shared/model/concepts/concept-proposal-tag-map.model';

describe('Component Tests', () => {
  describe('ConceptProposalTagMap Management Update Component', () => {
    let comp: ConceptProposalTagMapUpdateComponent;
    let fixture: ComponentFixture<ConceptProposalTagMapUpdateComponent>;
    let service: ConceptProposalTagMapService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptProposalTagMapUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConceptProposalTagMapUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptProposalTagMapUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptProposalTagMapService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConceptProposalTagMap(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConceptProposalTagMap();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
