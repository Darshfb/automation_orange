package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {
  private final By dashboardHeader =
      By.xpath("//h6[contains(@class,'oxd-topbar-header-breadcrumb') and text()='Dashboard']");
  private final By adminMenu = By.xpath("//span[text()='Admin']");
  private final By pimMenu = By.xpath("//span[text()='PIM']");
  private final By leaveMenu = By.xpath("//span[text()='Leave']");
  private final By userDropdown = By.cssSelector(".oxd-userdropdown");
  private final By logoutLink = By.xpath("//a[text()='Logout']");

  public DashboardPage(WebDriver driver) {
    super(driver);
  }

  public boolean isDashboardHeaderDisplayed() {
    return isDisplayed(dashboardHeader);
  }

  public void navigateToAdmin() {
    click(adminMenu);
  }

  public void navigateToPIM() {
    click(pimMenu);
  }

  public void navigateToLeave() {
    click(leaveMenu);
  }

  public void logout() {
    click(userDropdown);
    click(logoutLink);
  }
}
