import { element, by, ElementFinder } from 'protractor';

export class FormComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-form div table .btn-danger'));
  title = element.all(by.css('jhi-form div h2#page-heading span')).first();
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

export class FormUpdatePage {
  pageTitle = element(by.id('jhi-form-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  uuidInput = element(by.id('field_uuid'));
  nameInput = element(by.id('field_name'));
  versionInput = element(by.id('field_version'));
  buildInput = element(by.id('field_build'));
  publishedInput = element(by.id('field_published'));
  descriptionInput = element(by.id('field_description'));
  encounterTypeInput = element(by.id('field_encounterType'));
  templateInput = element(by.id('field_template'));
  xsltInput = element(by.id('field_xslt'));

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

  async setVersionInput(version: string): Promise<void> {
    await this.versionInput.sendKeys(version);
  }

  async getVersionInput(): Promise<string> {
    return await this.versionInput.getAttribute('value');
  }

  async setBuildInput(build: string): Promise<void> {
    await this.buildInput.sendKeys(build);
  }

  async getBuildInput(): Promise<string> {
    return await this.buildInput.getAttribute('value');
  }

  getPublishedInput(): ElementFinder {
    return this.publishedInput;
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setEncounterTypeInput(encounterType: string): Promise<void> {
    await this.encounterTypeInput.sendKeys(encounterType);
  }

  async getEncounterTypeInput(): Promise<string> {
    return await this.encounterTypeInput.getAttribute('value');
  }

  async setTemplateInput(template: string): Promise<void> {
    await this.templateInput.sendKeys(template);
  }

  async getTemplateInput(): Promise<string> {
    return await this.templateInput.getAttribute('value');
  }

  async setXsltInput(xslt: string): Promise<void> {
    await this.xsltInput.sendKeys(xslt);
  }

  async getXsltInput(): Promise<string> {
    return await this.xsltInput.getAttribute('value');
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

export class FormDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-form-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-form'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
