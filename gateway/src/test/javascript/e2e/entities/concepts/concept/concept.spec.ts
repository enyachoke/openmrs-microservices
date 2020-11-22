import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ConceptComponentsPage, ConceptDeleteDialog, ConceptUpdatePage } from './concept.page-object';

const expect = chai.expect;

describe('Concept e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let conceptComponentsPage: ConceptComponentsPage;
  let conceptUpdatePage: ConceptUpdatePage;
  let conceptDeleteDialog: ConceptDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Concepts', async () => {
    await navBarPage.goToEntity('concept');
    conceptComponentsPage = new ConceptComponentsPage();
    await browser.wait(ec.visibilityOf(conceptComponentsPage.title), 5000);
    expect(await conceptComponentsPage.getTitle()).to.eq('gatewayApp.conceptsConcept.home.title');
    await browser.wait(ec.or(ec.visibilityOf(conceptComponentsPage.entities), ec.visibilityOf(conceptComponentsPage.noResult)), 1000);
  });

  it('should load create Concept page', async () => {
    await conceptComponentsPage.clickOnCreateButton();
    conceptUpdatePage = new ConceptUpdatePage();
    expect(await conceptUpdatePage.getPageTitle()).to.eq('gatewayApp.conceptsConcept.home.createOrEditLabel');
    await conceptUpdatePage.cancel();
  });

  it('should create and save Concepts', async () => {
    const nbButtonsBeforeCreate = await conceptComponentsPage.countDeleteButtons();

    await conceptComponentsPage.clickOnCreateButton();

    await promise.all([
      conceptUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      conceptUpdatePage.setShortNameInput('shortName'),
      conceptUpdatePage.setDescriptionInput('description'),
      conceptUpdatePage.setFormTextInput('formText'),
      conceptUpdatePage.setVersionInput('version'),
    ]);

    expect(await conceptUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await conceptUpdatePage.getShortNameInput()).to.eq('shortName', 'Expected ShortName value to be equals to shortName');
    expect(await conceptUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await conceptUpdatePage.getFormTextInput()).to.eq('formText', 'Expected FormText value to be equals to formText');
    expect(await conceptUpdatePage.getVersionInput()).to.eq('version', 'Expected Version value to be equals to version');
    const selectedIsSet = conceptUpdatePage.getIsSetInput();
    if (await selectedIsSet.isSelected()) {
      await conceptUpdatePage.getIsSetInput().click();
      expect(await conceptUpdatePage.getIsSetInput().isSelected(), 'Expected isSet not to be selected').to.be.false;
    } else {
      await conceptUpdatePage.getIsSetInput().click();
      expect(await conceptUpdatePage.getIsSetInput().isSelected(), 'Expected isSet to be selected').to.be.true;
    }

    await conceptUpdatePage.save();
    expect(await conceptUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await conceptComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Concept', async () => {
    const nbButtonsBeforeDelete = await conceptComponentsPage.countDeleteButtons();
    await conceptComponentsPage.clickOnLastDeleteButton();

    conceptDeleteDialog = new ConceptDeleteDialog();
    expect(await conceptDeleteDialog.getDialogTitle()).to.eq('gatewayApp.conceptsConcept.delete.question');
    await conceptDeleteDialog.clickOnConfirmButton();

    expect(await conceptComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
