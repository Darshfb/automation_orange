package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PerformancePage extends BasePage {
  private final By performanceHeader =
      By.xpath("//h6[contains(@class,'oxd-topbar-header-breadcrumb') and text()='Performance']");
  private final By performanceMenu = By.xpath("//span[text()='Performance']");

  public PerformancePage(WebDriver driver) {
    super(driver);
  }

  public boolean isPerformanceHeaderDisplayed() {
    return isDisplayed(performanceHeader);
  }

  public void navigateToPerformanceModule() {
    click(performanceMenu);
    waitForPageLoad();
  }
}
