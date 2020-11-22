import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ConceptSetComponentsPage, ConceptSetDeleteDialog, ConceptSetUpdatePage } from './concept-set.page-object';

const expect = chai.expect;

describe('ConceptSet e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let conceptSetComponentsPage: ConceptSetComponentsPage;
  let conceptSetUpdatePage: ConceptSetUpdatePage;
  let conceptSetDeleteDialog: ConceptSetDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ConceptSets', async () => {
    await navBarPage.goToEntity('concept-set');
    conceptSetComponentsPage = new ConceptSetComponentsPage();
    await browser.wait(ec.visibilityOf(conceptSetComponentsPage.title), 5000);
    expect(await conceptSetComponentsPage.getTitle()).to.eq('gatewayApp.conceptsConceptSet.home.title');
    await browser.wait(ec.or(ec.visibilityOf(conceptSetComponentsPage.entities), ec.visibilityOf(conceptSetComponentsPage.noResult)), 1000);
  });

  it('should load create ConceptSet page', async () => {
    await conceptSetComponentsPage.clickOnCreateButton();
    conceptSetUpdatePage = new ConceptSetUpdatePage();
    expect(await conceptSetUpdatePage.getPageTitle()).to.eq('gatewayApp.conceptsConceptSet.home.createOrEditLabel');
    await conceptSetUpdatePage.cancel();
  });

  it('should create and save ConceptSets', async () => {
    const nbButtonsBeforeCreate = await conceptSetComponentsPage.countDeleteButtons();

    await conceptSetComponentsPage.clickOnCreateButton();

    await promise.all([
      conceptSetUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      conceptSetUpdatePage.setSortWeightInput('5'),
    ]);

    expect(await conceptSetUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await conceptSetUpdatePage.getSortWeightInput()).to.eq('5', 'Expected sortWeight value to be equals to 5');

    await conceptSetUpdatePage.save();
    expect(await conceptSetUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await conceptSetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ConceptSet', async () => {
    const nbButtonsBeforeDelete = await conceptSetComponentsPage.countDeleteButtons();
    await conceptSetComponentsPage.clickOnLastDeleteButton();

    conceptSetDeleteDialog = new ConceptSetDeleteDialog();
    expect(await conceptSetDeleteDialog.getDialogTitle()).to.eq('gatewayApp.conceptsConceptSet.delete.question');
    await conceptSetDeleteDialog.clickOnConfirmButton();

    expect(await conceptSetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
