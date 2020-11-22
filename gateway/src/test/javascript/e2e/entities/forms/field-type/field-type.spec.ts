import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { FieldTypeComponentsPage, FieldTypeDeleteDialog, FieldTypeUpdatePage } from './field-type.page-object';

const expect = chai.expect;

describe('FieldType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fieldTypeComponentsPage: FieldTypeComponentsPage;
  let fieldTypeUpdatePage: FieldTypeUpdatePage;
  let fieldTypeDeleteDialog: FieldTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FieldTypes', async () => {
    await navBarPage.goToEntity('field-type');
    fieldTypeComponentsPage = new FieldTypeComponentsPage();
    await browser.wait(ec.visibilityOf(fieldTypeComponentsPage.title), 5000);
    expect(await fieldTypeComponentsPage.getTitle()).to.eq('gatewayApp.formsFieldType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(fieldTypeComponentsPage.entities), ec.visibilityOf(fieldTypeComponentsPage.noResult)), 1000);
  });

  it('should load create FieldType page', async () => {
    await fieldTypeComponentsPage.clickOnCreateButton();
    fieldTypeUpdatePage = new FieldTypeUpdatePage();
    expect(await fieldTypeUpdatePage.getPageTitle()).to.eq('gatewayApp.formsFieldType.home.createOrEditLabel');
    await fieldTypeUpdatePage.cancel();
  });

  it('should create and save FieldTypes', async () => {
    const nbButtonsBeforeCreate = await fieldTypeComponentsPage.countDeleteButtons();

    await fieldTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      fieldTypeUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      fieldTypeUpdatePage.setNameInput('name'),
      fieldTypeUpdatePage.setDescriptionInput('description'),
    ]);

    expect(await fieldTypeUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await fieldTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await fieldTypeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    const selectedIsSet = fieldTypeUpdatePage.getIsSetInput();
    if (await selectedIsSet.isSelected()) {
      await fieldTypeUpdatePage.getIsSetInput().click();
      expect(await fieldTypeUpdatePage.getIsSetInput().isSelected(), 'Expected isSet not to be selected').to.be.false;
    } else {
      await fieldTypeUpdatePage.getIsSetInput().click();
      expect(await fieldTypeUpdatePage.getIsSetInput().isSelected(), 'Expected isSet to be selected').to.be.true;
    }

    await fieldTypeUpdatePage.save();
    expect(await fieldTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await fieldTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last FieldType', async () => {
    const nbButtonsBeforeDelete = await fieldTypeComponentsPage.countDeleteButtons();
    await fieldTypeComponentsPage.clickOnLastDeleteButton();

    fieldTypeDeleteDialog = new FieldTypeDeleteDialog();
    expect(await fieldTypeDeleteDialog.getDialogTitle()).to.eq('gatewayApp.formsFieldType.delete.question');
    await fieldTypeDeleteDialog.clickOnConfirmButton();

    expect(await fieldTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
