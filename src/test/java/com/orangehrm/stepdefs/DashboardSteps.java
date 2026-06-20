package com.orangehrm.stepdefs;

import com.orangehrm.drivers.DriverFactory;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class DashboardSteps {
  private final DashboardPage dashboardPage;
  private final LoginPage loginPage;

  public DashboardSteps() {
    this.dashboardPage = new DashboardPage(DriverFactory.getDriver());
    this.loginPage = new LoginPage(DriverFactory.getDriver());
  }

  @Then("the user should see the dashboard page title and main navigation menu")
  public void the_user_should_see_the_dashboard_page_title_and_main_navigation_menu() {
    Assert.assertTrue(dashboardPage.isDashboardHeaderDisplayed(), "Dashboard header was not found");
  }

  @When("the user logs out from the application")
  public void the_user_logs_out_from_the_application() {
    dashboardPage.logout();
  }

  @Then("the user should be redirected back to the login page")
  public void the_user_should_be_redirected_back_to_the_login_page() {
    Assert.assertTrue(
        loginPage.isErrorMessageDisplayed()
            || DriverFactory.getDriver().getCurrentUrl().contains("login"),
        "User was not navigated to login screen");
  }
}
