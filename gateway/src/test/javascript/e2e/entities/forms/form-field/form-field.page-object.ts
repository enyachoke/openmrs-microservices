import { element, by, ElementFinder } from 'protractor';

export class FormFieldComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-form-field div table .btn-danger'));
  title = element.all(by.css('jhi-form-field div h2#page-heading span')).first();
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

export class FormFieldUpdatePage {
  pageTitle = element(by.id('jhi-form-field-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  uuidInput = element(by.id('field_uuid'));
  nameInput = element(by.id('field_name'));
  fieldNumberInput = element(by.id('field_fieldNumber'));
  fieldPartInput = element(by.id('field_fieldPart'));
  pageNumberInput = element(by.id('field_pageNumber'));
  minOccursInput = element(by.id('field_minOccurs'));
  maxOccursInput = element(by.id('field_maxOccurs'));
  isRequiredInput = element(by.id('field_isRequired'));
  sortWeightInput = element(by.id('field_sortWeight'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUuidInput(uuid: string): Promise<void> {
    await this.uuidInput.sendKeys(uuid);
  }

  async getUuidInput(): Promise<string> {
    return await this.uuidInput.getAttribute('value');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setFieldNumberInput(fieldNumber: string): Promise<void> {
    await this.fieldNumberInput.sendKeys(fieldNumber);
  }

  async getFieldNumberInput(): Promise<string> {
    return await this.fieldNumberInput.getAttribute('value');
  }

  async setFieldPartInput(fieldPart: string): Promise<void> {
    await this.fieldPartInput.sendKeys(fieldPart);
  }

  async getFieldPartInput(): Promise<string> {
    return await this.fieldPartInput.getAttribute('value');
  }

  async setPageNumberInput(pageNumber: string): Promise<void> {
    await this.pageNumberInput.sendKeys(pageNumber);
  }

  async getPageNumberInput(): Promise<string> {
    return await this.pageNumberInput.getAttribute('value');
  }

  async setMinOccursInput(minOccurs: string): Promise<void> {
    await this.minOccursInput.sendKeys(minOccurs);
  }

  async getMinOccursInput(): Promise<string> {
    return await this.minOccursInput.getAttribute('value');
  }

  async setMaxOccursInput(maxOccurs: string): Promise<void> {
    await this.maxOccursInput.sendKeys(maxOccurs);
  }

  async getMaxOccursInput(): Promise<string> {
    return await this.maxOccursInput.getAttribute('value');
  }

  getIsRequiredInput(): ElementFinder {
    return this.isRequiredInput;
  }

  async setSortWeightInput(sortWeight: string): Promise<void> {
    await this.sortWeightInput.sendKeys(sortWeight);
  }

  async getSortWeightInput(): Promise<string> {
    return await this.sortWeightInput.getAttribute('value');
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

export class FormFieldDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-formField-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-formField'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
