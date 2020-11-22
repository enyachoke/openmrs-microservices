import { element, by, ElementFinder } from 'protractor';

export class ConceptComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-concept div table .btn-danger'));
  title = element.all(by.css('jhi-concept div h2#page-heading span')).first();
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

export class ConceptUpdatePage {
  pageTitle = element(by.id('jhi-concept-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  uuidInput = element(by.id('field_uuid'));
  shortNameInput = element(by.id('field_shortName'));
  descriptionInput = element(by.id('field_description'));
  formTextInput = element(by.id('field_formText'));
  versionInput = element(by.id('field_version'));
  isSetInput = element(by.id('field_isSet'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUuidInput(uuid: string): Promise<void> {
    await this.uuidInput.sendKeys(uuid);
  }

  async getUuidInput(): Promise<string> {
    return await this.uuidInput.getAttribute('value');
  }

  async setShortNameInput(shortName: string): Promise<void> {
    await this.shortNameInput.sendKeys(shortName);
  }

  async getShortNameInput(): Promise<string> {
    return await this.shortNameInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setFormTextInput(formText: string): Promise<void> {
    await this.formTextInput.sendKeys(formText);
  }

  async getFormTextInput(): Promise<string> {
    return await this.formTextInput.getAttribute('value');
  }

  async setVersionInput(version: string): Promise<void> {
    await this.versionInput.sendKeys(version);
  }

  async getVersionInput(): Promise<string> {
    return await this.versionInput.getAttribute('value');
  }

  getIsSetInput(): ElementFinder {
    return this.isSetInput;
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

export class ConceptDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-concept-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-concept'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
