package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
  private final By usernameInput = By.name("username");
  private final By passwordInput = By.name("password");
  private final By loginButton = By.cssSelector("button[type='submit']");
  private final By errorMessage = By.cssSelector(".oxd-alert-content-text");
  private final By inputErrorMessage = By.cssSelector(".oxd-input-field-error-message");

  public LoginPage(WebDriver driver) {
    super(driver);
  }

  public void enterUsername(String username) {
    type(usernameInput, username);
  }

  public void enterPassword(String password) {
    type(passwordInput, password);
  }

  public void clickLogin() {
    click(loginButton);
    waitForPageLoad();
  }

  public String getErrorMessageText() {
    return getText(errorMessage);
  }

  public String getInputValidationMessage() {
    return getText(inputErrorMessage);
  }

  public boolean isErrorMessageDisplayed() {
    return isDisplayed(errorMessage);
  }
}
