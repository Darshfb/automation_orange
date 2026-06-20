package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyInfoPage extends BasePage {
  private final By myInfoHeader =
      By.xpath("//h6[contains(@class,'oxd-topbar-header-breadcrumb') and text()='PIM']"); // PIM is
  // breadcrumb
  // header for
  // MyInfo
  // details
  private final By myInfoMenu = By.xpath("//span[text()='My Info']");
  private final By personalDetailsHeader = By.xpath("//h6[text()='Personal Details']");

  public MyInfoPage(WebDriver driver) {
    super(driver);
  }

  public boolean isMyInfoHeaderDisplayed() {
    return isDisplayed(personalDetailsHeader);
  }

  public void navigateToMyInfoModule() {
    click(myInfoMenu);
    waitForPageLoad();
  }
}
