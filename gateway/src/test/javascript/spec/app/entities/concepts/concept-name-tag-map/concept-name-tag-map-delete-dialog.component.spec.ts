import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { MockEventManager } from '../../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../../helpers/mock-active-modal.service';
import { ConceptNameTagMapDeleteDialogComponent } from 'app/entities/concepts/concept-name-tag-map/concept-name-tag-map-delete-dialog.component';
import { ConceptNameTagMapService } from 'app/entities/concepts/concept-name-tag-map/concept-name-tag-map.service';

describe('Component Tests', () => {
  describe('ConceptNameTagMap Management Delete Component', () => {
    let comp: ConceptNameTagMapDeleteDialogComponent;
    let fixture: ComponentFixture<ConceptNameTagMapDeleteDialogComponent>;
    let service: ConceptNameTagMapService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConceptNameTagMapDeleteDialogComponent],
      })
        .overrideTemplate(ConceptNameTagMapDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptNameTagMapDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptNameTagMapService);
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
