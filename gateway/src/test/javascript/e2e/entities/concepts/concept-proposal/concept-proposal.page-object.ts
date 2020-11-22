import { element, by, ElementFinder } from 'protractor';

export class ConceptProposalComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-concept-proposal div table .btn-danger'));
  title = element.all(by.css('jhi-concept-proposal div h2#page-heading span')).first();
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

export class ConceptProposalUpdatePage {
  pageTitle = element(by.id('jhi-concept-proposal-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  uuidInput = element(by.id('field_uuid'));
  encounterInput = element(by.id('field_encounter'));
  originalTextInput = element(by.id('field_originalText'));
  finalTextInput = element(by.id('field_finalText'));
  obsUuidInput = element(by.id('field_obsUuid'));
  obsConceptUuidInput = element(by.id('field_obsConceptUuid'));
  stateInput = element(by.id('field_state'));
  commentsInput = element(by.id('field_comments'));
  localeInput = element(by.id('field_locale'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUuidInput(uuid: string): Promise<void> {
    await this.uuidInput.sendKeys(uuid);
  }

  async getUuidInput(): Promise<string> {
    return await this.uuidInput.getAttribute('value');
  }

  async setEncounterInput(encounter: string): Promise<void> {
    await this.encounterInput.sendKeys(encounter);
  }

  async getEncounterInput(): Promise<string> {
    return await this.encounterInput.getAttribute('value');
  }

  async setOriginalTextInput(originalText: string): Promise<void> {
    await this.originalTextInput.sendKeys(originalText);
  }

  async getOriginalTextInput(): Promise<string> {
    return await this.originalTextInput.getAttribute('value');
  }

  async setFinalTextInput(finalText: string): Promise<void> {
    await this.finalTextInput.sendKeys(finalText);
  }

  async getFinalTextInput(): Promise<string> {
    return await this.finalTextInput.getAttribute('value');
  }

  async setObsUuidInput(obsUuid: string): Promise<void> {
    await this.obsUuidInput.sendKeys(obsUuid);
  }

  async getObsUuidInput(): Promise<string> {
    return await this.obsUuidInput.getAttribute('value');
  }

  async setObsConceptUuidInput(obsConceptUuid: string): Promise<void> {
    await this.obsConceptUuidInput.sendKeys(obsConceptUuid);
  }

  async getObsConceptUuidInput(): Promise<string> {
    return await this.obsConceptUuidInput.getAttribute('value');
  }

  async setStateInput(state: string): Promise<void> {
    await this.stateInput.sendKeys(state);
  }

  async getStateInput(): Promise<string> {
    return await this.stateInput.getAttribute('value');
  }

  async setCommentsInput(comments: string): Promise<void> {
    await this.commentsInput.sendKeys(comments);
  }

  async getCommentsInput(): Promise<string> {
    return await this.commentsInput.getAttribute('value');
  }

  async setLocaleInput(locale: string): Promise<void> {
    await this.localeInput.sendKeys(locale);
  }

  async getLocaleInput(): Promise<string> {
    return await this.localeInput.getAttribute('value');
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

export class ConceptProposalDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-conceptProposal-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-conceptProposal'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
