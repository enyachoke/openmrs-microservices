import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ConceptAnswerComponentsPage, ConceptAnswerDeleteDialog, ConceptAnswerUpdatePage } from './concept-answer.page-object';

const expect = chai.expect;

describe('ConceptAnswer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let conceptAnswerComponentsPage: ConceptAnswerComponentsPage;
  let conceptAnswerUpdatePage: ConceptAnswerUpdatePage;
  let conceptAnswerDeleteDialog: ConceptAnswerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ConceptAnswers', async () => {
    await navBarPage.goToEntity('concept-answer');
    conceptAnswerComponentsPage = new ConceptAnswerComponentsPage();
    await browser.wait(ec.visibilityOf(conceptAnswerComponentsPage.title), 5000);
    expect(await conceptAnswerComponentsPage.getTitle()).to.eq('gatewayApp.conceptsConceptAnswer.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(conceptAnswerComponentsPage.entities), ec.visibilityOf(conceptAnswerComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ConceptAnswer page', async () => {
    await conceptAnswerComponentsPage.clickOnCreateButton();
    conceptAnswerUpdatePage = new ConceptAnswerUpdatePage();
    expect(await conceptAnswerUpdatePage.getPageTitle()).to.eq('gatewayApp.conceptsConceptAnswer.home.createOrEditLabel');
    await conceptAnswerUpdatePage.cancel();
  });

  it('should create and save ConceptAnswers', async () => {
    const nbButtonsBeforeCreate = await conceptAnswerComponentsPage.countDeleteButtons();

    await conceptAnswerComponentsPage.clickOnCreateButton();

    await promise.all([
      conceptAnswerUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      conceptAnswerUpdatePage.setSortWeightInput('5'),
    ]);

    expect(await conceptAnswerUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await conceptAnswerUpdatePage.getSortWeightInput()).to.eq('5', 'Expected sortWeight value to be equals to 5');

    await conceptAnswerUpdatePage.save();
    expect(await conceptAnswerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await conceptAnswerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ConceptAnswer', async () => {
    const nbButtonsBeforeDelete = await conceptAnswerComponentsPage.countDeleteButtons();
    await conceptAnswerComponentsPage.clickOnLastDeleteButton();

    conceptAnswerDeleteDialog = new ConceptAnswerDeleteDialog();
    expect(await conceptAnswerDeleteDialog.getDialogTitle()).to.eq('gatewayApp.conceptsConceptAnswer.delete.question');
    await conceptAnswerDeleteDialog.clickOnConfirmButton();

    expect(await conceptAnswerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
