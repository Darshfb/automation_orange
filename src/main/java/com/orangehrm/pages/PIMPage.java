package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PIMPage extends BasePage {
  private final By pimHeader =
      By.xpath("//h6[contains(@class,'oxd-topbar-header-breadcrumb') and text()='PIM']");
  private final By addEmployeeTab = By.xpath("//a[text()='Add Employee']");
  private final By employeeListTab = By.xpath("//a[text()='Employee List']");

  // Add Employee fields
  private final By firstNameInput = By.name("firstName");
  private final By lastNameInput = By.name("lastName");
  private final By employeeIdInput =
      By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input");
  private final By saveButton = By.cssSelector("button[type='submit']");
  private final By successToast = By.cssSelector(".oxd-toast-content-text");

  // Employee List search fields
  private final By employeeNameSearchInput =
      By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div//input");
  private final By employeeIdSearchInput =
      By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div//input");
  private final By searchButton = By.cssSelector("button[type='submit']");

  // Edit / Delete fields
  private final By middleNameInput = By.name("middleName");
  private final By personalDetailsSaveButton =
      By.xpath("//button[@type='submit' and contains(., 'Save')]");
  private final By confirmDeleteButton =
      By.xpath("//button[contains(normalize-space(),'Yes, Delete')]");

  public PIMPage(WebDriver driver) {
    super(driver);
  }

  public boolean isPimHeaderDisplayed() {
    return isDisplayed(pimHeader);
  }

  public void navigateToAddEmployee() {
    click(addEmployeeTab);
  }

  public void navigateToEmployeeList() {
    click(employeeListTab);
  }

  public void clickReset() {
    try {
      click(By.xpath("//button[contains(., 'Reset')]"));
      waitForSpinnerToDisappear();
    } catch (Exception ignored) {
    }
  }

  public String getEmployeeIdFromForm() {
    return find(employeeIdInput).getAttribute("value");
  }

  public void createEmployee(String firstName, String lastName) {
    type(firstNameInput, firstName);
    type(lastNameInput, lastName);
  }

  public void clickSave() {
    click(saveButton);
    try {
      wait.until(
          org.openqa.selenium.support.ui.ExpectedConditions.urlContains("viewPersonalDetails"));
    } catch (Exception e) {
      log.warn("Redirect to personal details took too long.");
    }
    waitForPageLoad();
  }

  public void searchEmployeeById(String empId) {
    type(employeeIdSearchInput, empId);
    try {
      click(By.xpath("//button[@type='submit' and contains(., 'Search')]"));
    } catch (Exception e) {
      click(By.cssSelector("button.oxd-button--secondary"));
    }
    waitForSpinnerToDisappear();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException ignored) {
    }
  }

  public boolean isEmployeeIdInList(String empId) {
    By rowLocator =
        By.xpath(
            "//div[@class='oxd-table-card' and descendant::div[contains(., '" + empId + "')]]");
    return isDisplayed(rowLocator);
  }

  public boolean isSuccessToastDisplayed() {
    return isDisplayed(successToast);
  }

  public void clickEditEmployee(String empId) {
    By editBtn =
        By.xpath(
            "//div[@class='oxd-table-card' and descendant::div[contains(., '"
                + empId
                + "')]]//button[descendant::i[contains(@class, 'pencil')]]");
    click(editBtn);
    waitForPageLoad();
  }

  public void updateMiddleName(String middleName) {
    type(middleNameInput, middleName);
    waitForSpinnerToDisappear();
    click(personalDetailsSaveButton);
    // Wait for database sync, then refresh to avoid stale local state
    try {
      Thread.sleep(5000);
    } catch (InterruptedException ignored) {
    }
    driver.navigate().refresh();
    waitForPageLoad();
    waitForSpinnerToDisappear();
  }

  public boolean isMiddleNameInList(String empId, String middleName) {
    By rowLocator =
        By.xpath(
            "//div[@class='oxd-table-card' and descendant::div[contains(., '"
                + empId
                + "')] and descendant::div[contains(., '"
                + middleName
                + "')]]");
    return isDisplayed(rowLocator);
  }

  public void clickDeleteEmployee(String empId) {
    By deleteBtn =
        By.xpath(
            "//div[@class='oxd-table-card' and descendant::div[contains(., '"
                + empId
                + "')]]//button[descendant::i[contains(@class, 'trash')]]");
    click(deleteBtn);
    try {
      Thread.sleep(1500);
    } catch (InterruptedException ignored) {
    }
    click(confirmDeleteButton);
    waitForPageLoad();
  }
}
