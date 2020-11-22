import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ConceptNumericComponentsPage, ConceptNumericDeleteDialog, ConceptNumericUpdatePage } from './concept-numeric.page-object';

const expect = chai.expect;

describe('ConceptNumeric e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let conceptNumericComponentsPage: ConceptNumericComponentsPage;
  let conceptNumericUpdatePage: ConceptNumericUpdatePage;
  let conceptNumericDeleteDialog: ConceptNumericDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ConceptNumerics', async () => {
    await navBarPage.goToEntity('concept-numeric');
    conceptNumericComponentsPage = new ConceptNumericComponentsPage();
    await browser.wait(ec.visibilityOf(conceptNumericComponentsPage.title), 5000);
    expect(await conceptNumericComponentsPage.getTitle()).to.eq('gatewayApp.conceptsConceptNumeric.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(conceptNumericComponentsPage.entities), ec.visibilityOf(conceptNumericComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ConceptNumeric page', async () => {
    await conceptNumericComponentsPage.clickOnCreateButton();
    conceptNumericUpdatePage = new ConceptNumericUpdatePage();
    expect(await conceptNumericUpdatePage.getPageTitle()).to.eq('gatewayApp.conceptsConceptNumeric.home.createOrEditLabel');
    await conceptNumericUpdatePage.cancel();
  });

  it('should create and save ConceptNumerics', async () => {
    const nbButtonsBeforeCreate = await conceptNumericComponentsPage.countDeleteButtons();

    await conceptNumericComponentsPage.clickOnCreateButton();

    await promise.all([
      conceptNumericUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      conceptNumericUpdatePage.setHiAbsoluteInput('5'),
      conceptNumericUpdatePage.setHiNormalInput('5'),
      conceptNumericUpdatePage.setHiCriticalInput('5'),
      conceptNumericUpdatePage.setLowAbsoluteInput('5'),
      conceptNumericUpdatePage.setLowNormalInput('5'),
      conceptNumericUpdatePage.setLowCriticalInput('5'),
      conceptNumericUpdatePage.setUnitsInput('units'),
    ]);

    expect(await conceptNumericUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await conceptNumericUpdatePage.getHiAbsoluteInput()).to.eq('5', 'Expected hiAbsolute value to be equals to 5');
    expect(await conceptNumericUpdatePage.getHiNormalInput()).to.eq('5', 'Expected hiNormal value to be equals to 5');
    expect(await conceptNumericUpdatePage.getHiCriticalInput()).to.eq('5', 'Expected hiCritical value to be equals to 5');
    expect(await conceptNumericUpdatePage.getLowAbsoluteInput()).to.eq('5', 'Expected lowAbsolute value to be equals to 5');
    expect(await conceptNumericUpdatePage.getLowNormalInput()).to.eq('5', 'Expected lowNormal value to be equals to 5');
    expect(await conceptNumericUpdatePage.getLowCriticalInput()).to.eq('5', 'Expected lowCritical value to be equals to 5');
    expect(await conceptNumericUpdatePage.getUnitsInput()).to.eq('units', 'Expected Units value to be equals to units');
    const selectedPrecise = conceptNumericUpdatePage.getPreciseInput();
    if (await selectedPrecise.isSelected()) {
      await conceptNumericUpdatePage.getPreciseInput().click();
      expect(await conceptNumericUpdatePage.getPreciseInput().isSelected(), 'Expected precise not to be selected').to.be.false;
    } else {
      await conceptNumericUpdatePage.getPreciseInput().click();
      expect(await conceptNumericUpdatePage.getPreciseInput().isSelected(), 'Expected precise to be selected').to.be.true;
    }

    await conceptNumericUpdatePage.save();
    expect(await conceptNumericUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await conceptNumericComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ConceptNumeric', async () => {
    const nbButtonsBeforeDelete = await conceptNumericComponentsPage.countDeleteButtons();
    await conceptNumericComponentsPage.clickOnLastDeleteButton();

    conceptNumericDeleteDialog = new ConceptNumericDeleteDialog();
    expect(await conceptNumericDeleteDialog.getDialogTitle()).to.eq('gatewayApp.conceptsConceptNumeric.delete.question');
    await conceptNumericDeleteDialog.clickOnConfirmButton();

    expect(await conceptNumericComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
