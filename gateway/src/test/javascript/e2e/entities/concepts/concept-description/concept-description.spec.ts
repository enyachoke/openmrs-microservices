import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  ConceptDescriptionComponentsPage,
  ConceptDescriptionDeleteDialog,
  ConceptDescriptionUpdatePage,
} from './concept-description.page-object';

const expect = chai.expect;

describe('ConceptDescription e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let conceptDescriptionComponentsPage: ConceptDescriptionComponentsPage;
  let conceptDescriptionUpdatePage: ConceptDescriptionUpdatePage;
  let conceptDescriptionDeleteDialog: ConceptDescriptionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ConceptDescriptions', async () => {
    await navBarPage.goToEntity('concept-description');
    conceptDescriptionComponentsPage = new ConceptDescriptionComponentsPage();
    await browser.wait(ec.visibilityOf(conceptDescriptionComponentsPage.title), 5000);
    expect(await conceptDescriptionComponentsPage.getTitle()).to.eq('gatewayApp.conceptsConceptDescription.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(conceptDescriptionComponentsPage.entities), ec.visibilityOf(conceptDescriptionComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ConceptDescription page', async () => {
    await conceptDescriptionComponentsPage.clickOnCreateButton();
    conceptDescriptionUpdatePage = new ConceptDescriptionUpdatePage();
    expect(await conceptDescriptionUpdatePage.getPageTitle()).to.eq('gatewayApp.conceptsConceptDescription.home.createOrEditLabel');
    await conceptDescriptionUpdatePage.cancel();
  });

  it('should create and save ConceptDescriptions', async () => {
    const nbButtonsBeforeCreate = await conceptDescriptionComponentsPage.countDeleteButtons();

    await conceptDescriptionComponentsPage.clickOnCreateButton();

    await promise.all([
      conceptDescriptionUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      conceptDescriptionUpdatePage.setDescriptionInput('description'),
      conceptDescriptionUpdatePage.setLocaleInput('locale'),
    ]);

    expect(await conceptDescriptionUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await conceptDescriptionUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await conceptDescriptionUpdatePage.getLocaleInput()).to.eq('locale', 'Expected Locale value to be equals to locale');

    await conceptDescriptionUpdatePage.save();
    expect(await conceptDescriptionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await conceptDescriptionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ConceptDescription', async () => {
    const nbButtonsBeforeDelete = await conceptDescriptionComponentsPage.countDeleteButtons();
    await conceptDescriptionComponentsPage.clickOnLastDeleteButton();

    conceptDescriptionDeleteDialog = new ConceptDescriptionDeleteDialog();
    expect(await conceptDescriptionDeleteDialog.getDialogTitle()).to.eq('gatewayApp.conceptsConceptDescription.delete.question');
    await conceptDescriptionDeleteDialog.clickOnConfirmButton();

    expect(await conceptDescriptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
