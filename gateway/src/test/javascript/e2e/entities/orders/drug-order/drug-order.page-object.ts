import { element, by, ElementFinder } from 'protractor';

export class DrugOrderComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-drug-order div table .btn-danger'));
  title = element.all(by.css('jhi-drug-order div h2#page-heading span')).first();
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

export class DrugOrderUpdatePage {
  pageTitle = element(by.id('jhi-drug-order-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  uuidInput = element(by.id('field_uuid'));
  drugInventoryUuidInput = element(by.id('field_drugInventoryUuid'));
  doseInput = element(by.id('field_dose'));
  equivalentDailyDoseInput = element(by.id('field_equivalentDailyDose'));
  unitsInput = element(by.id('field_units'));
  frequencyInput = element(by.id('field_frequency'));
  prnInput = element(by.id('field_prn'));
  complexInput = element(by.id('field_complex'));
  quantityInput = element(by.id('field_quantity'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUuidInput(uuid: string): Promise<void> {
    await this.uuidInput.sendKeys(uuid);
  }

  async getUuidInput(): Promise<string> {
    return await this.uuidInput.getAttribute('value');
  }

  async setDrugInventoryUuidInput(drugInventoryUuid: string): Promise<void> {
    await this.drugInventoryUuidInput.sendKeys(drugInventoryUuid);
  }

  async getDrugInventoryUuidInput(): Promise<string> {
    return await this.drugInventoryUuidInput.getAttribute('value');
  }

  async setDoseInput(dose: string): Promise<void> {
    await this.doseInput.sendKeys(dose);
  }

  async getDoseInput(): Promise<string> {
    return await this.doseInput.getAttribute('value');
  }

  async setEquivalentDailyDoseInput(equivalentDailyDose: string): Promise<void> {
    await this.equivalentDailyDoseInput.sendKeys(equivalentDailyDose);
  }

  async getEquivalentDailyDoseInput(): Promise<string> {
    return await this.equivalentDailyDoseInput.getAttribute('value');
  }

  async setUnitsInput(units: string): Promise<void> {
    await this.unitsInput.sendKeys(units);
  }

  async getUnitsInput(): Promise<string> {
    return await this.unitsInput.getAttribute('value');
  }

  async setFrequencyInput(frequency: string): Promise<void> {
    await this.frequencyInput.sendKeys(frequency);
  }

  async getFrequencyInput(): Promise<string> {
    return await this.frequencyInput.getAttribute('value');
  }

  getPrnInput(): ElementFinder {
    return this.prnInput;
  }

  getComplexInput(): ElementFinder {
    return this.complexInput;
  }

  async setQuantityInput(quantity: string): Promise<void> {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput(): Promise<string> {
    return await this.quantityInput.getAttribute('value');
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

export class DrugOrderDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-drugOrder-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-drugOrder'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
