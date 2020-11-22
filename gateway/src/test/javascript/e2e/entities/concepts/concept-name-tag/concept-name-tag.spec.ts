import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ConceptNameTagComponentsPage, ConceptNameTagDeleteDialog, ConceptNameTagUpdatePage } from './concept-name-tag.page-object';

const expect = chai.expect;

describe('ConceptNameTag e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let conceptNameTagComponentsPage: ConceptNameTagComponentsPage;
  let conceptNameTagUpdatePage: ConceptNameTagUpdatePage;
  let conceptNameTagDeleteDialog: ConceptNameTagDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ConceptNameTags', async () => {
    await navBarPage.goToEntity('concept-name-tag');
    conceptNameTagComponentsPage = new ConceptNameTagComponentsPage();
    await browser.wait(ec.visibilityOf(conceptNameTagComponentsPage.title), 5000);
    expect(await conceptNameTagComponentsPage.getTitle()).to.eq('gatewayApp.conceptsConceptNameTag.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(conceptNameTagComponentsPage.entities), ec.visibilityOf(conceptNameTagComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ConceptNameTag page', async () => {
    await conceptNameTagComponentsPage.clickOnCreateButton();
    conceptNameTagUpdatePage = new ConceptNameTagUpdatePage();
    expect(await conceptNameTagUpdatePage.getPageTitle()).to.eq('gatewayApp.conceptsConceptNameTag.home.createOrEditLabel');
    await conceptNameTagUpdatePage.cancel();
  });

  it('should create and save ConceptNameTags', async () => {
    const nbButtonsBeforeCreate = await conceptNameTagComponentsPage.countDeleteButtons();

    await conceptNameTagComponentsPage.clickOnCreateButton();

    await promise.all([
      conceptNameTagUpdatePage.setUuidInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      conceptNameTagUpdatePage.setTagInput('tag'),
      conceptNameTagUpdatePage.setDescriptionInput('description'),
    ]);

    expect(await conceptNameTagUpdatePage.getUuidInput()).to.eq(
      '64c99148-3908-465d-8c4a-e510e3ade974',
      'Expected Uuid value to be equals to 64c99148-3908-465d-8c4a-e510e3ade974'
    );
    expect(await conceptNameTagUpdatePage.getTagInput()).to.eq('tag', 'Expected Tag value to be equals to tag');
    expect(await conceptNameTagUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await conceptNameTagUpdatePage.save();
    expect(await conceptNameTagUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await conceptNameTagComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ConceptNameTag', async () => {
    const nbButtonsBeforeDelete = await conceptNameTagComponentsPage.countDeleteButtons();
    await conceptNameTagComponentsPage.clickOnLastDeleteButton();

    conceptNameTagDeleteDialog = new ConceptNameTagDeleteDialog();
    expect(await conceptNameTagDeleteDialog.getDialogTitle()).to.eq('gatewayApp.conceptsConceptNameTag.delete.question');
    await conceptNameTagDeleteDialog.clickOnConfirmButton();

    expect(await conceptNameTagComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
