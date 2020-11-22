import { element, by, ElementFinder } from 'protractor';

export class ConceptNameComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-concept-name div table .btn-danger'));
  title = element.all(by.css('jhi-concept-name div h2#page-heading span')).first();
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

export class ConceptNameUpdatePage {
  pageTitle = element(by.id('jhi-concept-name-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  uuidInput = element(by.id('field_uuid'));
  nameInput = element(by.id('field_name'));
  localeInput = element(by.id('field_locale'));
  conceptNameTypeInput = element(by.id('field_conceptNameType'));

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

  async setLocaleInput(locale: string): Promise<void> {
    await this.localeInput.sendKeys(locale);
  }

  async getLocaleInput(): Promise<string> {
    return await this.localeInput.getAttribute('value');
  }

  async setConceptNameTypeInput(conceptNameType: string): Promise<void> {
    await this.conceptNameTypeInput.sendKeys(conceptNameType);
  }

  async getConceptNameTypeInput(): Promise<string> {
    return await this.conceptNameTypeInput.getAttribute('value');
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

export class ConceptNameDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-conceptName-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-conceptName'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
