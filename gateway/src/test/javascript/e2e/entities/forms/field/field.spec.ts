import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { FieldComponentsPage, FieldDeleteDialog, FieldUpdatePage } from './field.page-object';

const expect = chai.expect;

describe('Field e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fieldComponentsPage: FieldComponentsPage;
  let fieldUpdatePage: FieldUpdatePage;
  let fieldDeleteDialog: FieldDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Fields', async () => {
    await navBarPage.goToEntity('field');
    fieldComponentsPage = new FieldComponentsPage();
    await browser.wait(ec.visibilityOf(fieldComponentsPage.title), 5000);
    expect(await fieldComponentsPage.getTitle()).to.eq('gatewayApp.formsField.home.title');
    await browser.wait(ec.or(ec.visibilityOf(fieldComponentsPage.entities), ec.visibilityOf(fieldComponentsPage.noResult)), 1000);
  });

  it('should load create Field page', async () => {
    await fieldComponentsPage.clickOnCreateButton();
    fieldUpdatePage = new FieldUpdatePage();
    expect(await fieldUpdatePage.getPageTitle()).to.eq('gatewayApp.formsField.home.createOrEditLabel');
    await fieldUpdatePage.cancel();
  });

  it('should create and save Fields', async () => {
    const nbButtonsBeforeCreate = await fieldComponentsPage.countDeleteButtons();

    await fieldComponentsPage.clickOnCreateButton();

    await promise.all([
      fieldUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      fieldUpdatePage.setNameInput('name'),
      fieldUpdatePage.setDescriptionInput('description'),
      fieldUpdatePage.setConceptUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      fieldUpdatePage.setTableNameInput('tableName'),
      fieldUpdatePage.setAttributesNameInput('attributesName'),
      fieldUpdatePage.setDefaultValueInput('defaultValue'),
    ]);

    expect(await fieldUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await fieldUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await fieldUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await fieldUpdatePage.getConceptUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected ConceptUuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await fieldUpdatePage.getTableNameInput()).to.eq('tableName', 'Expected TableName value to be equals to tableName');
    expect(await fieldUpdatePage.getAttributesNameInput()).to.eq(
      'attributesName',
      'Expected AttributesName value to be equals to attributesName'
    );
    expect(await fieldUpdatePage.getDefaultValueInput()).to.eq('defaultValue', 'Expected DefaultValue value to be equals to defaultValue');
    const selectedSelectMultiple = fieldUpdatePage.getSelectMultipleInput();
    if (await selectedSelectMultiple.isSelected()) {
      await fieldUpdatePage.getSelectMultipleInput().click();
      expect(await fieldUpdatePage.getSelectMultipleInput().isSelected(), 'Expected selectMultiple not to be selected').to.be.false;
    } else {
      await fieldUpdatePage.getSelectMultipleInput().click();
      expect(await fieldUpdatePage.getSelectMultipleInput().isSelected(), 'Expected selectMultiple to be selected').to.be.true;
    }

    await fieldUpdatePage.save();
    expect(await fieldUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await fieldComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Field', async () => {
    const nbButtonsBeforeDelete = await fieldComponentsPage.countDeleteButtons();
    await fieldComponentsPage.clickOnLastDeleteButton();

    fieldDeleteDialog = new FieldDeleteDialog();
    expect(await fieldDeleteDialog.getDialogTitle()).to.eq('gatewayApp.formsField.delete.question');
    await fieldDeleteDialog.clickOnConfirmButton();

    expect(await fieldComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
