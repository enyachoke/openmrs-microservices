import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { DrugOrderComponentsPage, DrugOrderDeleteDialog, DrugOrderUpdatePage } from './drug-order.page-object';

const expect = chai.expect;

describe('DrugOrder e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let drugOrderComponentsPage: DrugOrderComponentsPage;
  let drugOrderUpdatePage: DrugOrderUpdatePage;
  let drugOrderDeleteDialog: DrugOrderDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DrugOrders', async () => {
    await navBarPage.goToEntity('drug-order');
    drugOrderComponentsPage = new DrugOrderComponentsPage();
    await browser.wait(ec.visibilityOf(drugOrderComponentsPage.title), 5000);
    expect(await drugOrderComponentsPage.getTitle()).to.eq('gatewayApp.ordersDrugOrder.home.title');
    await browser.wait(ec.or(ec.visibilityOf(drugOrderComponentsPage.entities), ec.visibilityOf(drugOrderComponentsPage.noResult)), 1000);
  });

  it('should load create DrugOrder page', async () => {
    await drugOrderComponentsPage.clickOnCreateButton();
    drugOrderUpdatePage = new DrugOrderUpdatePage();
    expect(await drugOrderUpdatePage.getPageTitle()).to.eq('gatewayApp.ordersDrugOrder.home.createOrEditLabel');
    await drugOrderUpdatePage.cancel();
  });

  it('should create and save DrugOrders', async () => {
    const nbButtonsBeforeCreate = await drugOrderComponentsPage.countDeleteButtons();

    await drugOrderComponentsPage.clickOnCreateButton();

    await promise.all([
      drugOrderUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      drugOrderUpdatePage.setDrugInventoryUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      drugOrderUpdatePage.setDoseInput('5'),
      drugOrderUpdatePage.setEquivalentDailyDoseInput('5'),
      drugOrderUpdatePage.setUnitsInput('units'),
      drugOrderUpdatePage.setFrequencyInput('frequency'),
      drugOrderUpdatePage.setQuantityInput('5'),
    ]);

    expect(await drugOrderUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await drugOrderUpdatePage.getDrugInventoryUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected DrugInventoryUuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await drugOrderUpdatePage.getDoseInput()).to.eq('5', 'Expected dose value to be equals to 5');
    expect(await drugOrderUpdatePage.getEquivalentDailyDoseInput()).to.eq('5', 'Expected equivalentDailyDose value to be equals to 5');
    expect(await drugOrderUpdatePage.getUnitsInput()).to.eq('units', 'Expected Units value to be equals to units');
    expect(await drugOrderUpdatePage.getFrequencyInput()).to.eq('frequency', 'Expected Frequency value to be equals to frequency');
    const selectedPrn = drugOrderUpdatePage.getPrnInput();
    if (await selectedPrn.isSelected()) {
      await drugOrderUpdatePage.getPrnInput().click();
      expect(await drugOrderUpdatePage.getPrnInput().isSelected(), 'Expected prn not to be selected').to.be.false;
    } else {
      await drugOrderUpdatePage.getPrnInput().click();
      expect(await drugOrderUpdatePage.getPrnInput().isSelected(), 'Expected prn to be selected').to.be.true;
    }
    const selectedComplex = drugOrderUpdatePage.getComplexInput();
    if (await selectedComplex.isSelected()) {
      await drugOrderUpdatePage.getComplexInput().click();
      expect(await drugOrderUpdatePage.getComplexInput().isSelected(), 'Expected complex not to be selected').to.be.false;
    } else {
      await drugOrderUpdatePage.getComplexInput().click();
      expect(await drugOrderUpdatePage.getComplexInput().isSelected(), 'Expected complex to be selected').to.be.true;
    }
    expect(await drugOrderUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');

    await drugOrderUpdatePage.save();
    expect(await drugOrderUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await drugOrderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last DrugOrder', async () => {
    const nbButtonsBeforeDelete = await drugOrderComponentsPage.countDeleteButtons();
    await drugOrderComponentsPage.clickOnLastDeleteButton();

    drugOrderDeleteDialog = new DrugOrderDeleteDialog();
    expect(await drugOrderDeleteDialog.getDialogTitle()).to.eq('gatewayApp.ordersDrugOrder.delete.question');
    await drugOrderDeleteDialog.clickOnConfirmButton();

    expect(await drugOrderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
