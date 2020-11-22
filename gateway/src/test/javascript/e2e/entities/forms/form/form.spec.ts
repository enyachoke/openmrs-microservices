import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { FormComponentsPage, FormDeleteDialog, FormUpdatePage } from './form.page-object';

const expect = chai.expect;

describe('Form e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let formComponentsPage: FormComponentsPage;
  let formUpdatePage: FormUpdatePage;
  let formDeleteDialog: FormDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Forms', async () => {
    await navBarPage.goToEntity('form');
    formComponentsPage = new FormComponentsPage();
    await browser.wait(ec.visibilityOf(formComponentsPage.title), 5000);
    expect(await formComponentsPage.getTitle()).to.eq('gatewayApp.formsForm.home.title');
    await browser.wait(ec.or(ec.visibilityOf(formComponentsPage.entities), ec.visibilityOf(formComponentsPage.noResult)), 1000);
  });

  it('should load create Form page', async () => {
    await formComponentsPage.clickOnCreateButton();
    formUpdatePage = new FormUpdatePage();
    expect(await formUpdatePage.getPageTitle()).to.eq('gatewayApp.formsForm.home.createOrEditLabel');
    await formUpdatePage.cancel();
  });

  it('should create and save Forms', async () => {
    const nbButtonsBeforeCreate = await formComponentsPage.countDeleteButtons();

    await formComponentsPage.clickOnCreateButton();

    await promise.all([
      formUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      formUpdatePage.setNameInput('name'),
      formUpdatePage.setVersionInput('version'),
      formUpdatePage.setBuildInput('5'),
      formUpdatePage.setDescriptionInput('description'),
      formUpdatePage.setEncounterTypeInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      formUpdatePage.setTemplateInput('template'),
      formUpdatePage.setXsltInput('xslt'),
    ]);

    expect(await formUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await formUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await formUpdatePage.getVersionInput()).to.eq('version', 'Expected Version value to be equals to version');
    expect(await formUpdatePage.getBuildInput()).to.eq('5', 'Expected build value to be equals to 5');
    const selectedPublished = formUpdatePage.getPublishedInput();
    if (await selectedPublished.isSelected()) {
      await formUpdatePage.getPublishedInput().click();
      expect(await formUpdatePage.getPublishedInput().isSelected(), 'Expected published not to be selected').to.be.false;
    } else {
      await formUpdatePage.getPublishedInput().click();
      expect(await formUpdatePage.getPublishedInput().isSelected(), 'Expected published to be selected').to.be.true;
    }
    expect(await formUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await formUpdatePage.getEncounterTypeInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected EncounterType value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await formUpdatePage.getTemplateInput()).to.eq('template', 'Expected Template value to be equals to template');
    expect(await formUpdatePage.getXsltInput()).to.eq('xslt', 'Expected Xslt value to be equals to xslt');

    await formUpdatePage.save();
    expect(await formUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await formComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Form', async () => {
    const nbButtonsBeforeDelete = await formComponentsPage.countDeleteButtons();
    await formComponentsPage.clickOnLastDeleteButton();

    formDeleteDialog = new FormDeleteDialog();
    expect(await formDeleteDialog.getDialogTitle()).to.eq('gatewayApp.formsForm.delete.question');
    await formDeleteDialog.clickOnConfirmButton();

    expect(await formComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
