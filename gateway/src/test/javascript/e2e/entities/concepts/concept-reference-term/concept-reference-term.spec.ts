import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  ConceptReferenceTermComponentsPage,
  ConceptReferenceTermDeleteDialog,
  ConceptReferenceTermUpdatePage,
} from './concept-reference-term.page-object';

const expect = chai.expect;

describe('ConceptReferenceTerm e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let conceptReferenceTermComponentsPage: ConceptReferenceTermComponentsPage;
  let conceptReferenceTermUpdatePage: ConceptReferenceTermUpdatePage;
  let conceptReferenceTermDeleteDialog: ConceptReferenceTermDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ConceptReferenceTerms', async () => {
    await navBarPage.goToEntity('concept-reference-term');
    conceptReferenceTermComponentsPage = new ConceptReferenceTermComponentsPage();
    await browser.wait(ec.visibilityOf(conceptReferenceTermComponentsPage.title), 5000);
    expect(await conceptReferenceTermComponentsPage.getTitle()).to.eq('gatewayApp.conceptsConceptReferenceTerm.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(conceptReferenceTermComponentsPage.entities), ec.visibilityOf(conceptReferenceTermComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ConceptReferenceTerm page', async () => {
    await conceptReferenceTermComponentsPage.clickOnCreateButton();
    conceptReferenceTermUpdatePage = new ConceptReferenceTermUpdatePage();
    expect(await conceptReferenceTermUpdatePage.getPageTitle()).to.eq('gatewayApp.conceptsConceptReferenceTerm.home.createOrEditLabel');
    await conceptReferenceTermUpdatePage.cancel();
  });

  it('should create and save ConceptReferenceTerms', async () => {
    const nbButtonsBeforeCreate = await conceptReferenceTermComponentsPage.countDeleteButtons();

    await conceptReferenceTermComponentsPage.clickOnCreateButton();

    await promise.all([
      conceptReferenceTermUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      conceptReferenceTermUpdatePage.setNameInput('name'),
      conceptReferenceTermUpdatePage.setCodeInput('code'),
      conceptReferenceTermUpdatePage.setVersionInput('version'),
      conceptReferenceTermUpdatePage.setDescriptionInput('description'),
    ]);

    expect(await conceptReferenceTermUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await conceptReferenceTermUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await conceptReferenceTermUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await conceptReferenceTermUpdatePage.getVersionInput()).to.eq('version', 'Expected Version value to be equals to version');
    expect(await conceptReferenceTermUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await conceptReferenceTermUpdatePage.save();
    expect(await conceptReferenceTermUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await conceptReferenceTermComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ConceptReferenceTerm', async () => {
    const nbButtonsBeforeDelete = await conceptReferenceTermComponentsPage.countDeleteButtons();
    await conceptReferenceTermComponentsPage.clickOnLastDeleteButton();

    conceptReferenceTermDeleteDialog = new ConceptReferenceTermDeleteDialog();
    expect(await conceptReferenceTermDeleteDialog.getDialogTitle()).to.eq('gatewayApp.conceptsConceptReferenceTerm.delete.question');
    await conceptReferenceTermDeleteDialog.clickOnConfirmButton();

    expect(await conceptReferenceTermComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
