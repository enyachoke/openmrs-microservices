import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { MockEventManager } from '../../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../../helpers/mock-active-modal.service';
import { ConceptReferenceTermDeleteDialogComponent } from 'app/entities/concepts/concept-reference-term/concept-reference-term-delete-dialog.component';
import { ConceptReferenceTermService } from 'app/entities/concepts/concept-reference-term/concept-reference-term.service';

describe('Component Tests', () => {
  describe('ConceptReferenceTerm Management Delete Component', () => {
    let comp: ConceptReferenceTermDeleteDialogComponent;
    let fixture: ComponentFixture<ConceptReferenceTermDeleteDialogComponent>;
    let service: ConceptReferenceTermService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptReferenceTermDeleteDialogComponent],
      })
        .overrideTemplate(ConceptReferenceTermDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptReferenceTermDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptReferenceTermService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
