import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ConceptProposalComponentsPage, ConceptProposalDeleteDialog, ConceptProposalUpdatePage } from './concept-proposal.page-object';

const expect = chai.expect;

describe('ConceptProposal e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let conceptProposalComponentsPage: ConceptProposalComponentsPage;
  let conceptProposalUpdatePage: ConceptProposalUpdatePage;
  let conceptProposalDeleteDialog: ConceptProposalDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ConceptProposals', async () => {
    await navBarPage.goToEntity('concept-proposal');
    conceptProposalComponentsPage = new ConceptProposalComponentsPage();
    await browser.wait(ec.visibilityOf(conceptProposalComponentsPage.title), 5000);
    expect(await conceptProposalComponentsPage.getTitle()).to.eq('gatewayApp.conceptsConceptProposal.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(conceptProposalComponentsPage.entities), ec.visibilityOf(conceptProposalComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ConceptProposal page', async () => {
    await conceptProposalComponentsPage.clickOnCreateButton();
    conceptProposalUpdatePage = new ConceptProposalUpdatePage();
    expect(await conceptProposalUpdatePage.getPageTitle()).to.eq('gatewayApp.conceptsConceptProposal.home.createOrEditLabel');
    await conceptProposalUpdatePage.cancel();
  });

  it('should create and save ConceptProposals', async () => {
    const nbButtonsBeforeCreate = await conceptProposalComponentsPage.countDeleteButtons();

    await conceptProposalComponentsPage.clickOnCreateButton();

    await promise.all([
      conceptProposalUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      conceptProposalUpdatePage.setEncounterInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      conceptProposalUpdatePage.setOriginalTextInput('originalText'),
      conceptProposalUpdatePage.setFinalTextInput('finalText'),
      conceptProposalUpdatePage.setObsUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      conceptProposalUpdatePage.setObsConceptUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      conceptProposalUpdatePage.setStateInput('state'),
      conceptProposalUpdatePage.setCommentsInput('comments'),
      conceptProposalUpdatePage.setLocaleInput('locale'),
    ]);

    expect(await conceptProposalUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await conceptProposalUpdatePage.getEncounterInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Encounter value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await conceptProposalUpdatePage.getOriginalTextInput()).to.eq(
      'originalText',
      'Expected OriginalText value to be equals to originalText'
    );
    expect(await conceptProposalUpdatePage.getFinalTextInput()).to.eq('finalText', 'Expected FinalText value to be equals to finalText');
    expect(await conceptProposalUpdatePage.getObsUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected ObsUuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await conceptProposalUpdatePage.getObsConceptUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected ObsConceptUuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await conceptProposalUpdatePage.getStateInput()).to.eq('state', 'Expected State value to be equals to state');
    expect(await conceptProposalUpdatePage.getCommentsInput()).to.eq('comments', 'Expected Comments value to be equals to comments');
    expect(await conceptProposalUpdatePage.getLocaleInput()).to.eq('locale', 'Expected Locale value to be equals to locale');

    await conceptProposalUpdatePage.save();
    expect(await conceptProposalUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await conceptProposalComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ConceptProposal', async () => {
    const nbButtonsBeforeDelete = await conceptProposalComponentsPage.countDeleteButtons();
    await conceptProposalComponentsPage.clickOnLastDeleteButton();

    conceptProposalDeleteDialog = new ConceptProposalDeleteDialog();
    expect(await conceptProposalDeleteDialog.getDialogTitle()).to.eq('gatewayApp.conceptsConceptProposal.delete.question');
    await conceptProposalDeleteDialog.clickOnConfirmButton();

    expect(await conceptProposalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
