package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecruitmentPage extends BasePage {
  private final By recruitmentHeader =
      By.xpath("//h6[contains(@class,'oxd-topbar-header-breadcrumb') and text()='Recruitment']");
  private final By recruitmentMenu = By.xpath("//span[text()='Recruitment']");
  private final By addButton = By.xpath("//button[normalize-space()='Add']");

  // Add Candidate fields
  private final By firstNameInput = By.name("firstName");
  private final By lastNameInput = By.name("lastName");
  private final By emailInput =
      By.xpath("//label[text()='Email']/parent::div/following-sibling::div/input");
  private final By saveButton = By.cssSelector("button[type='submit']");

  public RecruitmentPage(WebDriver driver) {
    super(driver);
  }

  public boolean isRecruitmentHeaderDisplayed() {
    return isDisplayed(recruitmentHeader);
  }

  public void navigateToRecruitmentModule() {
    click(recruitmentMenu);
    waitForPageLoad();
  }

  public void clickAddCandidate() {
    click(addButton);
  }

  public void fillCandidateForm(String firstName, String lastName, String email) {
    type(firstNameInput, firstName);
    type(lastNameInput, lastName);
    type(emailInput, email);
  }

  public void saveCandidate() {
    click(saveButton);
    waitForPageLoad();
  }
}
