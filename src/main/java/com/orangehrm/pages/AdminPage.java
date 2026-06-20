package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminPage extends BasePage {
  private final By adminHeader =
      By.xpath("//h6[contains(@class,'oxd-topbar-header-breadcrumb') and text()='Admin']");
  private final By usernameSearchField =
      By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input");
  private final By searchButton = By.cssSelector("button[type='submit']");
  private final By noRecordsFoundText = By.xpath("//span[text()='No Records Found']");
  private final By searchResultsContainer = By.cssSelector(".oxd-table-body");

  private final By addButton = By.xpath("//button[normalize-space()='Add']");
  private final By userRoleDropdown =
      By.xpath(
          "//label[text()='User Role']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']");
  private final By statusDropdown =
      By.xpath(
          "//label[text()='Status']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']");
  private final By employeeNameInput =
      By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div//input");
  private final By newUsernameInput =
      By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input");
  private final By passwordInput =
      By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input");
  private final By confirmPasswordInput =
      By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input");
  private final By saveFormButton = By.xpath("//button[@type='submit']");

  public AdminPage(WebDriver driver) {
    super(driver);
  }

  public boolean isAdminHeaderDisplayed() {
    return isDisplayed(adminHeader);
  }

  public void clickAdd() {
    click(addButton);
  }

  public void selectUserRole(String role) {
    click(userRoleDropdown);
    By option = By.xpath("//div[@role='listbox']//*[text()='" + role + "']");
    click(option);
  }

  public void selectStatus(String status) {
    click(statusDropdown);
    By option = By.xpath("//div[@role='listbox']//*[text()='" + status + "']");
    click(option);
  }

  public void enterEmployeeName(String name) {
    type(employeeNameInput, name);
    // Wait for autocomplete suggestion dropdown to dynamically appear with the ACTUAL name (not
    // just 'Searching....')
    By suggestionsLocator =
        By.cssSelector(
            ".oxd-autocomplete-dropdown *[role='option'], .oxd-autocomplete-dropdown span");
    try {
      org.openqa.selenium.WebElement matchingOption =
          wait.until(
              d -> {
                java.util.List<org.openqa.selenium.WebElement> options =
                    d.findElements(suggestionsLocator);
                for (org.openqa.selenium.WebElement opt : options) {
                  String text = opt.getText().trim();
                  // Skip 'Searching....' or empty text
                  if (!text.isEmpty()
                      && !text.contains("Searching")
                      && (text.equals(name) || text.contains(name))) {
                    return opt;
                  }
                }
                return null; // Return null to make wait.until keep trying
              });
      matchingOption.click();
    } catch (Exception e) {
      log.warn(
          "Auto-suggestion for employee '{}' failed to load or click: {}", name, e.getMessage());
    }
  }

  public void enterNewUsername(String username) {
    type(newUsernameInput, username);
  }

  public void enterUserPassword(String password) {
    type(passwordInput, password);
    type(confirmPasswordInput, password);
  }

  public void clickSaveUser() {
    click(saveFormButton);
    try {
      wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("viewSystemUsers"));
    } catch (Exception e) {
      log.warn("Redirect back to system users list took too long.");
    }
    waitForSpinnerToDisappear();
  }

  public void searchUser(String username) {
    type(usernameSearchField, username);
    try {
      click(By.xpath("//button[@type='submit' and contains(., 'Search')]"));
    } catch (Exception e) {
      click(By.cssSelector("button.oxd-button--secondary"));
    }
    waitForSpinnerToDisappear();
  }

  public boolean isUserFoundInResults(String username) {
    By userRow =
        By.xpath(
            "//div[@class='oxd-table-card' and descendant::div[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                + username.toLowerCase()
                + "')]]");
    return isDisplayed(userRow);
  }

  public boolean isNoRecordsFoundMessageDisplayed() {
    return isDisplayed(noRecordsFoundText);
  }

  private final By confirmDeleteButton =
      By.xpath("//button[contains(normalize-space(),'Yes, Delete')]");

  public void clickEditUser(String username) {
    By editBtn =
        By.xpath(
            "//div[@class='oxd-table-card' and descendant::div[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                + username.toLowerCase()
                + "')]]//button[descendant::i[contains(@class, 'pencil')]]");
    click(editBtn);
    waitForPageLoad();
  }

  public void clickDeleteUser(String username) {
    By deleteBtn =
        By.xpath(
            "//div[@class='oxd-table-card' and descendant::div[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                + username.toLowerCase()
                + "')]]//button[descendant::i[contains(@class, 'trash')]]");
    click(deleteBtn);
    click(confirmDeleteButton);
    waitForPageLoad();
  }

  public boolean isUserStatusInResults(String username, String status) {
    By userRow =
        By.xpath(
            "//div[@class='oxd-table-card' and descendant::div[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                + username.toLowerCase()
                + "')] and descendant::div[contains(., '"
                + status
                + "')]]");
    return isDisplayed(userRow);
  }
}
