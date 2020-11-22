import { element, by, ElementFinder } from 'protractor';

export class FieldComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-field div table .btn-danger'));
  title = element.all(by.css('jhi-field div h2#page-heading span')).first();
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

export class FieldUpdatePage {
  pageTitle = element(by.id('jhi-field-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  uuidInput = element(by.id('field_uuid'));
  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  conceptUuidInput = element(by.id('field_conceptUuid'));
  tableNameInput = element(by.id('field_tableName'));
  attributesNameInput = element(by.id('field_attributesName'));
  defaultValueInput = element(by.id('field_defaultValue'));
  selectMultipleInput = element(by.id('field_selectMultiple'));

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

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setConceptUuidInput(conceptUuid: string): Promise<void> {
    await this.conceptUuidInput.sendKeys(conceptUuid);
  }

  async getConceptUuidInput(): Promise<string> {
    return await this.conceptUuidInput.getAttribute('value');
  }

  async setTableNameInput(tableName: string): Promise<void> {
    await this.tableNameInput.sendKeys(tableName);
  }

  async getTableNameInput(): Promise<string> {
    return await this.tableNameInput.getAttribute('value');
  }

  async setAttributesNameInput(attributesName: string): Promise<void> {
    await this.attributesNameInput.sendKeys(attributesName);
  }

  async getAttributesNameInput(): Promise<string> {
    return await this.attributesNameInput.getAttribute('value');
  }

  async setDefaultValueInput(defaultValue: string): Promise<void> {
    await this.defaultValueInput.sendKeys(defaultValue);
  }

  async getDefaultValueInput(): Promise<string> {
    return await this.defaultValueInput.getAttribute('value');
  }

  getSelectMultipleInput(): ElementFinder {
    return this.selectMultipleInput;
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

export class FieldDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-field-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-field'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
