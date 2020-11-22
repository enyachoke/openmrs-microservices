import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ConceptWordComponentsPage, ConceptWordDeleteDialog, ConceptWordUpdatePage } from './concept-word.page-object';

const expect = chai.expect;

describe('ConceptWord e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let conceptWordComponentsPage: ConceptWordComponentsPage;
  let conceptWordUpdatePage: ConceptWordUpdatePage;
  let conceptWordDeleteDialog: ConceptWordDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ConceptWords', async () => {
    await navBarPage.goToEntity('concept-word');
    conceptWordComponentsPage = new ConceptWordComponentsPage();
    await browser.wait(ec.visibilityOf(conceptWordComponentsPage.title), 5000);
    expect(await conceptWordComponentsPage.getTitle()).to.eq('gatewayApp.conceptsConceptWord.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(conceptWordComponentsPage.entities), ec.visibilityOf(conceptWordComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ConceptWord page', async () => {
    await conceptWordComponentsPage.clickOnCreateButton();
    conceptWordUpdatePage = new ConceptWordUpdatePage();
    expect(await conceptWordUpdatePage.getPageTitle()).to.eq('gatewayApp.conceptsConceptWord.home.createOrEditLabel');
    await conceptWordUpdatePage.cancel();
  });

  it('should create and save ConceptWords', async () => {
    const nbButtonsBeforeCreate = await conceptWordComponentsPage.countDeleteButtons();

    await conceptWordComponentsPage.clickOnCreateButton();

    await promise.all([
      conceptWordUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      conceptWordUpdatePage.setWordInput('word'),
      conceptWordUpdatePage.setLocaleInput('locale'),
      conceptWordUpdatePage.setWeightInput('5'),
    ]);

    expect(await conceptWordUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await conceptWordUpdatePage.getWordInput()).to.eq('word', 'Expected Word value to be equals to word');
    expect(await conceptWordUpdatePage.getLocaleInput()).to.eq('locale', 'Expected Locale value to be equals to locale');
    expect(await conceptWordUpdatePage.getWeightInput()).to.eq('5', 'Expected weight value to be equals to 5');

    await conceptWordUpdatePage.save();
    expect(await conceptWordUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await conceptWordComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ConceptWord', async () => {
    const nbButtonsBeforeDelete = await conceptWordComponentsPage.countDeleteButtons();
    await conceptWordComponentsPage.clickOnLastDeleteButton();

    conceptWordDeleteDialog = new ConceptWordDeleteDialog();
    expect(await conceptWordDeleteDialog.getDialogTitle()).to.eq('gatewayApp.conceptsConceptWord.delete.question');
    await conceptWordDeleteDialog.clickOnConfirmButton();

    expect(await conceptWordComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
