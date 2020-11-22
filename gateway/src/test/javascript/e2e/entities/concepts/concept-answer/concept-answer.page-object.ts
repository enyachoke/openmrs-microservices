import { element, by, ElementFinder } from 'protractor';

export class ConceptAnswerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-concept-answer div table .btn-danger'));
  title = element.all(by.css('jhi-concept-answer div h2#page-heading span')).first();
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

export class ConceptAnswerUpdatePage {
  pageTitle = element(by.id('jhi-concept-answer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  uuidInput = element(by.id('field_uuid'));
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

export class ConceptAnswerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-conceptAnswer-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-conceptAnswer'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
