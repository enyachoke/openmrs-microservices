import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { OrderComponentsPage, OrderDeleteDialog, OrderUpdatePage } from './order.page-object';

const expect = chai.expect;

describe('Order e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let orderComponentsPage: OrderComponentsPage;
  let orderUpdatePage: OrderUpdatePage;
  let orderDeleteDialog: OrderDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Orders', async () => {
    await navBarPage.goToEntity('order');
    orderComponentsPage = new OrderComponentsPage();
    await browser.wait(ec.visibilityOf(orderComponentsPage.title), 5000);
    expect(await orderComponentsPage.getTitle()).to.eq('gatewayApp.ordersOrder.home.title');
    await browser.wait(ec.or(ec.visibilityOf(orderComponentsPage.entities), ec.visibilityOf(orderComponentsPage.noResult)), 1000);
  });

  it('should load create Order page', async () => {
    await orderComponentsPage.clickOnCreateButton();
    orderUpdatePage = new OrderUpdatePage();
    expect(await orderUpdatePage.getPageTitle()).to.eq('gatewayApp.ordersOrder.home.createOrEditLabel');
    await orderUpdatePage.cancel();
  });

  it('should create and save Orders', async () => {
    const nbButtonsBeforeCreate = await orderComponentsPage.countDeleteButtons();

    await orderComponentsPage.clickOnCreateButton();

    await promise.all([
      orderUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      orderUpdatePage.setConceptUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      orderUpdatePage.setOrdererUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      orderUpdatePage.setEncounterUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      orderUpdatePage.setInstructionsInput('instructions'),
      orderUpdatePage.setStartDateInput('2000-12-31'),
      orderUpdatePage.setAutoExpireDateInput('2000-12-31'),
      orderUpdatePage.setDiscontinuedDateInput('2000-12-31'),
      orderUpdatePage.setAccessionNumberInput('accessionNumber'),
      orderUpdatePage.setDiscontinuedReasonNonCodedInput('discontinuedReasonNonCoded'),
      orderUpdatePage.setPatientUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
    ]);

    expect(await orderUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await orderUpdatePage.getConceptUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected ConceptUuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await orderUpdatePage.getOrdererUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected OrdererUuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await orderUpdatePage.getEncounterUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected EncounterUuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await orderUpdatePage.getInstructionsInput()).to.eq('instructions', 'Expected Instructions value to be equals to instructions');
    expect(await orderUpdatePage.getStartDateInput()).to.eq('2000-12-31', 'Expected startDate value to be equals to 2000-12-31');
    expect(await orderUpdatePage.getAutoExpireDateInput()).to.eq('2000-12-31', 'Expected autoExpireDate value to be equals to 2000-12-31');
    const selectedDiscontinued = orderUpdatePage.getDiscontinuedInput();
    if (await selectedDiscontinued.isSelected()) {
      await orderUpdatePage.getDiscontinuedInput().click();
      expect(await orderUpdatePage.getDiscontinuedInput().isSelected(), 'Expected discontinued not to be selected').to.be.false;
    } else {
      await orderUpdatePage.getDiscontinuedInput().click();
      expect(await orderUpdatePage.getDiscontinuedInput().isSelected(), 'Expected discontinued to be selected').to.be.true;
    }
    expect(await orderUpdatePage.getDiscontinuedDateInput()).to.eq(
      '2000-12-31',
      'Expected discontinuedDate value to be equals to 2000-12-31'
    );
    expect(await orderUpdatePage.getAccessionNumberInput()).to.eq(
      'accessionNumber',
      'Expected AccessionNumber value to be equals to accessionNumber'
    );
    expect(await orderUpdatePage.getDiscontinuedReasonNonCodedInput()).to.eq(
      'discontinuedReasonNonCoded',
      'Expected DiscontinuedReasonNonCoded value to be equals to discontinuedReasonNonCoded'
    );
    expect(await orderUpdatePage.getPatientUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected PatientUuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );

    await orderUpdatePage.save();
    expect(await orderUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await orderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Order', async () => {
    const nbButtonsBeforeDelete = await orderComponentsPage.countDeleteButtons();
    await orderComponentsPage.clickOnLastDeleteButton();

    orderDeleteDialog = new OrderDeleteDialog();
    expect(await orderDeleteDialog.getDialogTitle()).to.eq('gatewayApp.ordersOrder.delete.question');
    await orderDeleteDialog.clickOnConfirmButton();

    expect(await orderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
