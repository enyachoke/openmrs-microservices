import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { FieldAnswerComponentsPage, FieldAnswerDeleteDialog, FieldAnswerUpdatePage } from './field-answer.page-object';

const expect = chai.expect;

describe('FieldAnswer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fieldAnswerComponentsPage: FieldAnswerComponentsPage;
  let fieldAnswerUpdatePage: FieldAnswerUpdatePage;
  let fieldAnswerDeleteDialog: FieldAnswerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FieldAnswers', async () => {
    await navBarPage.goToEntity('field-answer');
    fieldAnswerComponentsPage = new FieldAnswerComponentsPage();
    await browser.wait(ec.visibilityOf(fieldAnswerComponentsPage.title), 5000);
    expect(await fieldAnswerComponentsPage.getTitle()).to.eq('gatewayApp.formsFieldAnswer.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(fieldAnswerComponentsPage.entities), ec.visibilityOf(fieldAnswerComponentsPage.noResult)),
      1000
    );
  });

  it('should load create FieldAnswer page', async () => {
    await fieldAnswerComponentsPage.clickOnCreateButton();
    fieldAnswerUpdatePage = new FieldAnswerUpdatePage();
    expect(await fieldAnswerUpdatePage.getPageTitle()).to.eq('gatewayApp.formsFieldAnswer.home.createOrEditLabel');
    await fieldAnswerUpdatePage.cancel();
  });

  it('should create and save FieldAnswers', async () => {
    const nbButtonsBeforeCreate = await fieldAnswerComponentsPage.countDeleteButtons();

    await fieldAnswerComponentsPage.clickOnCreateButton();

    await promise.all([fieldAnswerUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974')]);

    expect(await fieldAnswerUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );

    await fieldAnswerUpdatePage.save();
    expect(await fieldAnswerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await fieldAnswerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last FieldAnswer', async () => {
    const nbButtonsBeforeDelete = await fieldAnswerComponentsPage.countDeleteButtons();
    await fieldAnswerComponentsPage.clickOnLastDeleteButton();

    fieldAnswerDeleteDialog = new FieldAnswerDeleteDialog();
    expect(await fieldAnswerDeleteDialog.getDialogTitle()).to.eq('gatewayApp.formsFieldAnswer.delete.question');
    await fieldAnswerDeleteDialog.clickOnConfirmButton();

    expect(await fieldAnswerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
