package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TimePage extends BasePage {
  private final By timeHeader =
      By.xpath("//h6[contains(@class,'oxd-topbar-header-breadcrumb') and text()='Time']");
  private final By employeeNameInput =
      By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div//input");
  private final By viewButton = By.cssSelector("button[type='submit']");
  private final By timeMenu = By.xpath("//span[text()='Time']");

  public TimePage(WebDriver driver) {
    super(driver);
  }

  public boolean isTimeHeaderDisplayed() {
    return isDisplayed(timeHeader);
  }

  public void navigateToTimeModule() {
    click(timeMenu);
    waitForPageLoad();
  }

  public void searchTimesheetForEmployee(String empName) {
    type(employeeNameInput, empName);
    click(viewButton);
    waitForPageLoad();
  }
}
