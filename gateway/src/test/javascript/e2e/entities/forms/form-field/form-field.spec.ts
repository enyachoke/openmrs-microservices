import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { FormFieldComponentsPage, FormFieldDeleteDialog, FormFieldUpdatePage } from './form-field.page-object';

const expect = chai.expect;

describe('FormField e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let formFieldComponentsPage: FormFieldComponentsPage;
  let formFieldUpdatePage: FormFieldUpdatePage;
  let formFieldDeleteDialog: FormFieldDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FormFields', async () => {
    await navBarPage.goToEntity('form-field');
    formFieldComponentsPage = new FormFieldComponentsPage();
    await browser.wait(ec.visibilityOf(formFieldComponentsPage.title), 5000);
    expect(await formFieldComponentsPage.getTitle()).to.eq('gatewayApp.formsFormField.home.title');
    await browser.wait(ec.or(ec.visibilityOf(formFieldComponentsPage.entities), ec.visibilityOf(formFieldComponentsPage.noResult)), 1000);
  });

  it('should load create FormField page', async () => {
    await formFieldComponentsPage.clickOnCreateButton();
    formFieldUpdatePage = new FormFieldUpdatePage();
    expect(await formFieldUpdatePage.getPageTitle()).to.eq('gatewayApp.formsFormField.home.createOrEditLabel');
    await formFieldUpdatePage.cancel();
  });

  it('should create and save FormFields', async () => {
    const nbButtonsBeforeCreate = await formFieldComponentsPage.countDeleteButtons();

    await formFieldComponentsPage.clickOnCreateButton();

    await promise.all([
      formFieldUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      formFieldUpdatePage.setNameInput('name'),
      formFieldUpdatePage.setFieldNumberInput('5'),
      formFieldUpdatePage.setFieldPartInput('fieldPart'),
      formFieldUpdatePage.setPageNumberInput('5'),
      formFieldUpdatePage.setMinOccursInput('5'),
      formFieldUpdatePage.setMaxOccursInput('5'),
      formFieldUpdatePage.setSortWeightInput('5'),
    ]);

    expect(await formFieldUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await formFieldUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await formFieldUpdatePage.getFieldNumberInput()).to.eq('5', 'Expected fieldNumber value to be equals to 5');
    expect(await formFieldUpdatePage.getFieldPartInput()).to.eq('fieldPart', 'Expected FieldPart value to be equals to fieldPart');
    expect(await formFieldUpdatePage.getPageNumberInput()).to.eq('5', 'Expected pageNumber value to be equals to 5');
    expect(await formFieldUpdatePage.getMinOccursInput()).to.eq('5', 'Expected minOccurs value to be equals to 5');
    expect(await formFieldUpdatePage.getMaxOccursInput()).to.eq('5', 'Expected maxOccurs value to be equals to 5');
    const selectedIsRequired = formFieldUpdatePage.getIsRequiredInput();
    if (await selectedIsRequired.isSelected()) {
      await formFieldUpdatePage.getIsRequiredInput().click();
      expect(await formFieldUpdatePage.getIsRequiredInput().isSelected(), 'Expected isRequired not to be selected').to.be.false;
    } else {
      await formFieldUpdatePage.getIsRequiredInput().click();
      expect(await formFieldUpdatePage.getIsRequiredInput().isSelected(), 'Expected isRequired to be selected').to.be.true;
    }
    expect(await formFieldUpdatePage.getSortWeightInput()).to.eq('5', 'Expected sortWeight value to be equals to 5');

    await formFieldUpdatePage.save();
    expect(await formFieldUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await formFieldComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last FormField', async () => {
    const nbButtonsBeforeDelete = await formFieldComponentsPage.countDeleteButtons();
    await formFieldComponentsPage.clickOnLastDeleteButton();

    formFieldDeleteDialog = new FormFieldDeleteDialog();
    expect(await formFieldDeleteDialog.getDialogTitle()).to.eq('gatewayApp.formsFormField.delete.question');
    await formFieldDeleteDialog.clickOnConfirmButton();

    expect(await formFieldComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
