import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  ConceptNameTagMapComponentsPage,
  ConceptNameTagMapDeleteDialog,
  ConceptNameTagMapUpdatePage,
} from './concept-name-tag-map.page-object';

const expect = chai.expect;

describe('ConceptNameTagMap e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let conceptNameTagMapComponentsPage: ConceptNameTagMapComponentsPage;
  let conceptNameTagMapUpdatePage: ConceptNameTagMapUpdatePage;
  let conceptNameTagMapDeleteDialog: ConceptNameTagMapDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ConceptNameTagMaps', async () => {
    await navBarPage.goToEntity('concept-name-tag-map');
    conceptNameTagMapComponentsPage = new ConceptNameTagMapComponentsPage();
    await browser.wait(ec.visibilityOf(conceptNameTagMapComponentsPage.title), 5000);
    expect(await conceptNameTagMapComponentsPage.getTitle()).to.eq('gatewayApp.conceptsConceptNameTagMap.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(conceptNameTagMapComponentsPage.entities), ec.visibilityOf(conceptNameTagMapComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ConceptNameTagMap page', async () => {
    await conceptNameTagMapComponentsPage.clickOnCreateButton();
    conceptNameTagMapUpdatePage = new ConceptNameTagMapUpdatePage();
    expect(await conceptNameTagMapUpdatePage.getPageTitle()).to.eq('gatewayApp.conceptsConceptNameTagMap.home.createOrEditLabel');
    await conceptNameTagMapUpdatePage.cancel();
  });

  it('should create and save ConceptNameTagMaps', async () => {
    const nbButtonsBeforeCreate = await conceptNameTagMapComponentsPage.countDeleteButtons();

    await conceptNameTagMapComponentsPage.clickOnCreateButton();

    await promise.all([conceptNameTagMapUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974')]);

    expect(await conceptNameTagMapUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );

    await conceptNameTagMapUpdatePage.save();
    expect(await conceptNameTagMapUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await conceptNameTagMapComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ConceptNameTagMap', async () => {
    const nbButtonsBeforeDelete = await conceptNameTagMapComponentsPage.countDeleteButtons();
    await conceptNameTagMapComponentsPage.clickOnLastDeleteButton();

    conceptNameTagMapDeleteDialog = new ConceptNameTagMapDeleteDialog();
    expect(await conceptNameTagMapDeleteDialog.getDialogTitle()).to.eq('gatewayApp.conceptsConceptNameTagMap.delete.question');
    await conceptNameTagMapDeleteDialog.clickOnConfirmButton();

    expect(await conceptNameTagMapComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
