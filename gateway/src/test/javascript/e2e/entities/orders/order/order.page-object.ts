import { element, by, ElementFinder } from 'protractor';

export class OrderComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-order div table .btn-danger'));
  title = element.all(by.css('jhi-order div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class OrderUpdatePage {
  pageTitle = element(by.id('jhi-order-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  uuidInput = element(by.id('field_uuid'));
  conceptUuidInput = element(by.id('field_conceptUuid'));
  ordererUuidInput = element(by.id('field_ordererUuid'));
  encounterUuidInput = element(by.id('field_encounterUuid'));
  instructionsInput = element(by.id('field_instructions'));
  startDateInput = element(by.id('field_startDate'));
  autoExpireDateInput = element(by.id('field_autoExpireDate'));
  discontinuedInput = element(by.id('field_discontinued'));
  discontinuedDateInput = element(by.id('field_discontinuedDate'));
  accessionNumberInput = element(by.id('field_accessionNumber'));
  discontinuedReasonNonCodedInput = element(by.id('field_discontinuedReasonNonCoded'));
  patientUuidInput = element(by.id('field_patientUuid'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUuidInput(uuid: string): Promise<void> {
    await this.uuidInput.sendKeys(uuid);
  }

  async getUuidInput(): Promise<string> {
    return await this.uuidInput.getAttribute('value');
  }

  async setConceptUuidInput(conceptUuid: string): Promise<void> {
    await this.conceptUuidInput.sendKeys(conceptUuid);
  }

  async getConceptUuidInput(): Promise<string> {
    return await this.conceptUuidInput.getAttribute('value');
  }

  async setOrdererUuidInput(ordererUuid: string): Promise<void> {
    await this.ordererUuidInput.sendKeys(ordererUuid);
  }

  async getOrdererUuidInput(): Promise<string> {
    return await this.ordererUuidInput.getAttribute('value');
  }

  async setEncounterUuidInput(encounterUuid: string): Promise<void> {
    await this.encounterUuidInput.sendKeys(encounterUuid);
  }

  async getEncounterUuidInput(): Promise<string> {
    return await this.encounterUuidInput.getAttribute('value');
  }

  async setInstructionsInput(instructions: string): Promise<void> {
    await this.instructionsInput.sendKeys(instructions);
  }

  async getInstructionsInput(): Promise<string> {
    return await this.instructionsInput.getAttribute('value');
  }

  async setStartDateInput(startDate: string): Promise<void> {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput(): Promise<string> {
    return await this.startDateInput.getAttribute('value');
  }

  async setAutoExpireDateInput(autoExpireDate: string): Promise<void> {
    await this.autoExpireDateInput.sendKeys(autoExpireDate);
  }

  async getAutoExpireDateInput(): Promise<string> {
    return await this.autoExpireDateInput.getAttribute('value');
  }

  getDiscontinuedInput(): ElementFinder {
    return this.discontinuedInput;
  }

  async setDiscontinuedDateInput(discontinuedDate: string): Promise<void> {
    await this.discontinuedDateInput.sendKeys(discontinuedDate);
  }

  async getDiscontinuedDateInput(): Promise<string> {
    return await this.discontinuedDateInput.getAttribute('value');
  }

  async setAccessionNumberInput(accessionNumber: string): Promise<void> {
    await this.accessionNumberInput.sendKeys(accessionNumber);
  }

  async getAccessionNumberInput(): Promise<string> {
    return await this.accessionNumberInput.getAttribute('value');
  }

  async setDiscontinuedReasonNonCodedInput(discontinuedReasonNonCoded: string): Promise<void> {
    await this.discontinuedReasonNonCodedInput.sendKeys(discontinuedReasonNonCoded);
  }

  async getDiscontinuedReasonNonCodedInput(): Promise<string> {
    return await this.discontinuedReasonNonCodedInput.getAttribute('value');
  }

  async setPatientUuidInput(patientUuid: string): Promise<void> {
    await this.patientUuidInput.sendKeys(patientUuid);
  }

  async getPatientUuidInput(): Promise<string> {
    return await this.patientUuidInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class OrderDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-order-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-order'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
