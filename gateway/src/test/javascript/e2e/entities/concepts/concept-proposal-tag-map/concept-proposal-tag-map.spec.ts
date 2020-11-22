import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  ConceptProposalTagMapComponentsPage,
  ConceptProposalTagMapDeleteDialog,
  ConceptProposalTagMapUpdatePage,
} from './concept-proposal-tag-map.page-object';

const expect = chai.expect;

describe('ConceptProposalTagMap e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let conceptProposalTagMapComponentsPage: ConceptProposalTagMapComponentsPage;
  let conceptProposalTagMapUpdatePage: ConceptProposalTagMapUpdatePage;
  let conceptProposalTagMapDeleteDialog: ConceptProposalTagMapDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ConceptProposalTagMaps', async () => {
    await navBarPage.goToEntity('concept-proposal-tag-map');
    conceptProposalTagMapComponentsPage = new ConceptProposalTagMapComponentsPage();
    await browser.wait(ec.visibilityOf(conceptProposalTagMapComponentsPage.title), 5000);
    expect(await conceptProposalTagMapComponentsPage.getTitle()).to.eq('gatewayApp.conceptsConceptProposalTagMap.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(conceptProposalTagMapComponentsPage.entities), ec.visibilityOf(conceptProposalTagMapComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ConceptProposalTagMap page', async () => {
    await conceptProposalTagMapComponentsPage.clickOnCreateButton();
    conceptProposalTagMapUpdatePage = new ConceptProposalTagMapUpdatePage();
    expect(await conceptProposalTagMapUpdatePage.getPageTitle()).to.eq('gatewayApp.conceptsConceptProposalTagMap.home.createOrEditLabel');
    await conceptProposalTagMapUpdatePage.cancel();
  });

  it('should create and save ConceptProposalTagMaps', async () => {
    const nbButtonsBeforeCreate = await conceptProposalTagMapComponentsPage.countDeleteButtons();

    await conceptProposalTagMapComponentsPage.clickOnCreateButton();

    await promise.all([conceptProposalTagMapUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974')]);

    expect(await conceptProposalTagMapUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );

    await conceptProposalTagMapUpdatePage.save();
    expect(await conceptProposalTagMapUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await conceptProposalTagMapComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ConceptProposalTagMap', async () => {
    const nbButtonsBeforeDelete = await conceptProposalTagMapComponentsPage.countDeleteButtons();
    await conceptProposalTagMapComponentsPage.clickOnLastDeleteButton();

    conceptProposalTagMapDeleteDialog = new ConceptProposalTagMapDeleteDialog();
    expect(await conceptProposalTagMapDeleteDialog.getDialogTitle()).to.eq('gatewayApp.conceptsConceptProposalTagMap.delete.question');
    await conceptProposalTagMapDeleteDialog.clickOnConfirmButton();

    expect(await conceptProposalTagMapComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
