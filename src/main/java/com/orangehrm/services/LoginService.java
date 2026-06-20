package com.orangehrm.services;

import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import org.openqa.selenium.WebDriver;

public class LoginService {
  private final LoginPage loginPage;
  private final DashboardPage dashboardPage;

  public LoginService(WebDriver driver) {
    this.loginPage = new LoginPage(driver);
    this.dashboardPage = new DashboardPage(driver);
  }

  public void login(String username, String password) {
    loginPage.enterUsername(username);
    loginPage.enterPassword(password);
    loginPage.clickLogin();
  }

  public void loginWithEmptyFields(String username, String password) {
    if (username != null && !username.isEmpty()) {
      loginPage.enterUsername(username);
    }
    if (password != null && !password.isEmpty()) {
      loginPage.enterPassword(password);
    }
    loginPage.clickLogin();
  }

  public boolean isErrorMessageDisplayed() {
    return loginPage.isErrorMessageDisplayed();
  }

  public String getErrorMessage() {
    return loginPage.getErrorMessageText();
  }

  public String getInputValidationError() {
    return loginPage.getInputValidationMessage();
  }

  public boolean isDashboardLoaded() {
    return dashboardPage.isDashboardHeaderDisplayed();
  }

  public void waitForLoginPageLoad() {
    loginPage.waitForPageLoad();
  }
}
