import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ConceptComplexComponentsPage, ConceptComplexDeleteDialog, ConceptComplexUpdatePage } from './concept-complex.page-object';

const expect = chai.expect;

describe('ConceptComplex e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let conceptComplexComponentsPage: ConceptComplexComponentsPage;
  let conceptComplexUpdatePage: ConceptComplexUpdatePage;
  let conceptComplexDeleteDialog: ConceptComplexDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ConceptComplexes', async () => {
    await navBarPage.goToEntity('concept-complex');
    conceptComplexComponentsPage = new ConceptComplexComponentsPage();
    await browser.wait(ec.visibilityOf(conceptComplexComponentsPage.title), 5000);
    expect(await conceptComplexComponentsPage.getTitle()).to.eq('gatewayApp.conceptsConceptComplex.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(conceptComplexComponentsPage.entities), ec.visibilityOf(conceptComplexComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ConceptComplex page', async () => {
    await conceptComplexComponentsPage.clickOnCreateButton();
    conceptComplexUpdatePage = new ConceptComplexUpdatePage();
    expect(await conceptComplexUpdatePage.getPageTitle()).to.eq('gatewayApp.conceptsConceptComplex.home.createOrEditLabel');
    await conceptComplexUpdatePage.cancel();
  });

  it('should create and save ConceptComplexes', async () => {
    const nbButtonsBeforeCreate = await conceptComplexComponentsPage.countDeleteButtons();

    await conceptComplexComponentsPage.clickOnCreateButton();

    await promise.all([
      conceptComplexUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      conceptComplexUpdatePage.setHandlerInput('handler'),
    ]);

    expect(await conceptComplexUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await conceptComplexUpdatePage.getHandlerInput()).to.eq('handler', 'Expected Handler value to be equals to handler');

    await conceptComplexUpdatePage.save();
    expect(await conceptComplexUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await conceptComplexComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ConceptComplex', async () => {
    const nbButtonsBeforeDelete = await conceptComplexComponentsPage.countDeleteButtons();
    await conceptComplexComponentsPage.clickOnLastDeleteButton();

    conceptComplexDeleteDialog = new ConceptComplexDeleteDialog();
    expect(await conceptComplexDeleteDialog.getDialogTitle()).to.eq('gatewayApp.conceptsConceptComplex.delete.question');
    await conceptComplexDeleteDialog.clickOnConfirmButton();

    expect(await conceptComplexComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
