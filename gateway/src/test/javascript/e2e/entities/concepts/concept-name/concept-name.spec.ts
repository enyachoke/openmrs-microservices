import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ConceptNameComponentsPage, ConceptNameDeleteDialog, ConceptNameUpdatePage } from './concept-name.page-object';

const expect = chai.expect;

describe('ConceptName e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let conceptNameComponentsPage: ConceptNameComponentsPage;
  let conceptNameUpdatePage: ConceptNameUpdatePage;
  let conceptNameDeleteDialog: ConceptNameDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ConceptNames', async () => {
    await navBarPage.goToEntity('concept-name');
    conceptNameComponentsPage = new ConceptNameComponentsPage();
    await browser.wait(ec.visibilityOf(conceptNameComponentsPage.title), 5000);
    expect(await conceptNameComponentsPage.getTitle()).to.eq('gatewayApp.conceptsConceptName.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(conceptNameComponentsPage.entities), ec.visibilityOf(conceptNameComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ConceptName page', async () => {
    await conceptNameComponentsPage.clickOnCreateButton();
    conceptNameUpdatePage = new ConceptNameUpdatePage();
    expect(await conceptNameUpdatePage.getPageTitle()).to.eq('gatewayApp.conceptsConceptName.home.createOrEditLabel');
    await conceptNameUpdatePage.cancel();
  });

  it('should create and save ConceptNames', async () => {
    const nbButtonsBeforeCreate = await conceptNameComponentsPage.countDeleteButtons();

    await conceptNameComponentsPage.clickOnCreateButton();

    await promise.all([
      conceptNameUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      conceptNameUpdatePage.setNameInput('name'),
      conceptNameUpdatePage.setLocaleInput('locale'),
      conceptNameUpdatePage.setConceptNameTypeInput('conceptNameType'),
    ]);

    expect(await conceptNameUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await conceptNameUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await conceptNameUpdatePage.getLocaleInput()).to.eq('locale', 'Expected Locale value to be equals to locale');
    expect(await conceptNameUpdatePage.getConceptNameTypeInput()).to.eq(
      'conceptNameType',
      'Expected ConceptNameType value to be equals to conceptNameType'
    );

    await conceptNameUpdatePage.save();
    expect(await conceptNameUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await conceptNameComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ConceptName', async () => {
    const nbButtonsBeforeDelete = await conceptNameComponentsPage.countDeleteButtons();
    await conceptNameComponentsPage.clickOnLastDeleteButton();

    conceptNameDeleteDialog = new ConceptNameDeleteDialog();
    expect(await conceptNameDeleteDialog.getDialogTitle()).to.eq('gatewayApp.conceptsConceptName.delete.question');
    await conceptNameDeleteDialog.clickOnConfirmButton();

    expect(await conceptNameComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
