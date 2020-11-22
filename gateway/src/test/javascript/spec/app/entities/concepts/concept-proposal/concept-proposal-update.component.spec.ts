import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConceptProposalUpdateComponent } from 'app/entities/concepts/concept-proposal/concept-proposal-update.component';
import { ConceptProposalService } from 'app/entities/concepts/concept-proposal/concept-proposal.service';
import { ConceptProposal } from 'app/shared/model/concepts/concept-proposal.model';

describe('Component Tests', () => {
  describe('ConceptProposal Management Update Component', () => {
    let comp: ConceptProposalUpdateComponent;
    let fixture: ComponentFixture<ConceptProposalUpdateComponent>;
    let service: ConceptProposalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptProposalUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConceptProposalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptProposalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptProposalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConceptProposal(123);
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
        const entity = new ConceptProposal();
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
