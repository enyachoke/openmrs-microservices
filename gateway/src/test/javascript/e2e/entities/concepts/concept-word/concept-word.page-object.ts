import { element, by, ElementFinder } from 'protractor';

export class ConceptWordComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-concept-word div table .btn-danger'));
  title = element.all(by.css('jhi-concept-word div h2#page-heading span')).first();
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

export class ConceptWordUpdatePage {
  pageTitle = element(by.id('jhi-concept-word-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  uuidInput = element(by.id('field_uuid'));
  wordInput = element(by.id('field_word'));
  localeInput = element(by.id('field_locale'));
  weightInput = element(by.id('field_weight'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUuidInput(uuid: string): Promise<void> {
    await this.uuidInput.sendKeys(uuid);
  }

  async getUuidInput(): Promise<string> {
    return await this.uuidInput.getAttribute('value');
  }

  async setWordInput(word: string): Promise<void> {
    await this.wordInput.sendKeys(word);
  }

  async getWordInput(): Promise<string> {
    return await this.wordInput.getAttribute('value');
  }

  async setLocaleInput(locale: string): Promise<void> {
    await this.localeInput.sendKeys(locale);
  }

  async getLocaleInput(): Promise<string> {
    return await this.localeInput.getAttribute('value');
  }

  async setWeightInput(weight: string): Promise<void> {
    await this.weightInput.sendKeys(weight);
  }

  async getWeightInput(): Promise<string> {
    return await this.weightInput.getAttribute('value');
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

export class ConceptWordDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-conceptWord-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-conceptWord'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
