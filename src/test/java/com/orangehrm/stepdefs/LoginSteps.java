package com.orangehrm.stepdefs;

import com.orangehrm.drivers.DriverFactory;
import com.orangehrm.services.LoginService;
import com.orangehrm.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {
  private final LoginService loginService;

  public LoginSteps() {
    this.loginService = new LoginService(DriverFactory.getDriver());
  }

  @Given("the user is on the OrangeHRM login page")
  public void the_user_is_on_the_orange_hrm_login_page() {
    DriverFactory.getDriver().get(ConfigReader.getProperty("url"));
    loginService.waitForLoginPageLoad();
  }

  @When("the user enters the configured admin username and password")
  public void the_user_enters_the_configured_admin_username_and_password() {
    loginService.login(
        ConfigReader.getProperty("admin.username"), ConfigReader.getProperty("admin.password"));
  }

  @When("clicks the login button")
  public void clicks_the_login_button() {
    // Handled in service but we can call standard or wait
  }

  @Then("the user should be redirected to the dashboard page")
  public void the_user_should_be_redirected_to_the_dashboard_page() {
    Assert.assertTrue(loginService.isDashboardLoaded(), "Dashboard failed to load!");
  }

  @When("the user enters username {string} and password {string}")
  public void the_user_enters_username_and_password(String username, String password) {
    loginService.loginWithEmptyFields(username, password);
  }

  @Then("an error message {string} should be displayed")
  public void an_error_message_should_be_displayed(String expectedError) {
    Assert.assertTrue(loginService.isErrorMessageDisplayed(), "Error alert should be visible");
    Assert.assertEquals(loginService.getErrorMessage(), expectedError);
  }

  @Then("a field validation message {string} should be displayed under the missing field")
  public void a_field_validation_message_should_be_displayed_under_the_missing_field(
      String expectedMessage) {
    Assert.assertEquals(loginService.getInputValidationError(), expectedMessage);
  }

  @Given("the user is logged in to the dashboard")
  public void the_user_is_logged_in_to_the_dashboard() {
    the_user_is_on_the_orange_hrm_login_page();
    the_user_enters_the_configured_admin_username_and_password();
    the_user_should_be_redirected_to_the_dashboard_page();
  }
}
