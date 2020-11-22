import { element, by, ElementFinder } from 'protractor';

export class ConceptNumericComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-concept-numeric div table .btn-danger'));
  title = element.all(by.css('jhi-concept-numeric div h2#page-heading span')).first();
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

export class ConceptNumericUpdatePage {
  pageTitle = element(by.id('jhi-concept-numeric-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  uuidInput = element(by.id('field_uuid'));
  hiAbsoluteInput = element(by.id('field_hiAbsolute'));
  hiNormalInput = element(by.id('field_hiNormal'));
  hiCriticalInput = element(by.id('field_hiCritical'));
  lowAbsoluteInput = element(by.id('field_lowAbsolute'));
  lowNormalInput = element(by.id('field_lowNormal'));
  lowCriticalInput = element(by.id('field_lowCritical'));
  unitsInput = element(by.id('field_units'));
  preciseInput = element(by.id('field_precise'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUuidInput(uuid: string): Promise<void> {
    await this.uuidInput.sendKeys(uuid);
  }

  async getUuidInput(): Promise<string> {
    return await this.uuidInput.getAttribute('value');
  }

  async setHiAbsoluteInput(hiAbsolute: string): Promise<void> {
    await this.hiAbsoluteInput.sendKeys(hiAbsolute);
  }

  async getHiAbsoluteInput(): Promise<string> {
    return await this.hiAbsoluteInput.getAttribute('value');
  }

  async setHiNormalInput(hiNormal: string): Promise<void> {
    await this.hiNormalInput.sendKeys(hiNormal);
  }

  async getHiNormalInput(): Promise<string> {
    return await this.hiNormalInput.getAttribute('value');
  }

  async setHiCriticalInput(hiCritical: string): Promise<void> {
    await this.hiCriticalInput.sendKeys(hiCritical);
  }

  async getHiCriticalInput(): Promise<string> {
    return await this.hiCriticalInput.getAttribute('value');
  }

  async setLowAbsoluteInput(lowAbsolute: string): Promise<void> {
    await this.lowAbsoluteInput.sendKeys(lowAbsolute);
  }

  async getLowAbsoluteInput(): Promise<string> {
    return await this.lowAbsoluteInput.getAttribute('value');
  }

  async setLowNormalInput(lowNormal: string): Promise<void> {
    await this.lowNormalInput.sendKeys(lowNormal);
  }

  async getLowNormalInput(): Promise<string> {
    return await this.lowNormalInput.getAttribute('value');
  }

  async setLowCriticalInput(lowCritical: string): Promise<void> {
    await this.lowCriticalInput.sendKeys(lowCritical);
  }

  async getLowCriticalInput(): Promise<string> {
    return await this.lowCriticalInput.getAttribute('value');
  }

  async setUnitsInput(units: string): Promise<void> {
    await this.unitsInput.sendKeys(units);
  }

  async getUnitsInput(): Promise<string> {
    return await this.unitsInput.getAttribute('value');
  }

  getPreciseInput(): ElementFinder {
    return this.preciseInput;
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

export class ConceptNumericDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-conceptNumeric-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-conceptNumeric'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
