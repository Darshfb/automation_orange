package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LeavePage extends BasePage {
  private final By leaveHeader =
      By.xpath("//h6[contains(@class,'oxd-topbar-header-breadcrumb') and text()='Leave']");
  private final By applyTab = By.xpath("//a[text()='Apply']");
  private final By myLeaveTab = By.xpath("//a[text()='My Leave']");

  // Apply Leave form
  private final By leaveTypeDropdown = By.cssSelector(".oxd-select-text");
  private final By leaveTypeOption = By.xpath("//div[@role='listbox']//div[2]");
  private final By fromDateInput =
      By.xpath("//label[text()='From Date']/parent::div/following-sibling::div//input");
  private final By toDateInput =
      By.xpath("//label[text()='To Date']/parent::div/following-sibling::div//input");
  private final By commentsTextarea =
      By.xpath("//label[text()='Comments']/parent::div/following-sibling::div/textarea");
  private final By applyButton = By.cssSelector("button[type='submit']");

  public LeavePage(WebDriver driver) {
    super(driver);
  }

  public boolean isLeaveHeaderDisplayed() {
    return isDisplayed(leaveHeader);
  }

  public void navigateToApplyLeave() {
    click(applyTab);
  }

  public void navigateToMyLeave() {
    click(myLeaveTab);
  }

  public void fillApplyLeaveForm(String fromDate, String toDate, String comments) {
    try {
      click(leaveTypeDropdown);
      click(leaveTypeOption);

      type(fromDateInput, fromDate);
      type(toDateInput, toDate);
      type(commentsTextarea, comments);
    } catch (Exception e) {
      log.warn(
          "Failed to fill leave form. Entitlements may be missing for this user. Error: {}",
          e.getMessage());
    }
  }

  public void submitApplyLeave() {
    try {
      click(applyButton);
      waitForPageLoad();
    } catch (Exception e) {
      log.warn("Failed to click apply button: {}", e.getMessage());
    }
  }
}
