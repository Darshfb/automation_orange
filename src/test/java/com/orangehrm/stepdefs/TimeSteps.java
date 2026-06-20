package com.orangehrm.stepdefs;

import com.orangehrm.drivers.DriverFactory;
import com.orangehrm.services.TimeService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class TimeSteps {
  private final TimeService timeService;

  public TimeSteps() {
    this.timeService = new TimeService(DriverFactory.getDriver());
  }

  @Given("the user navigates to the Time module")
  public void the_user_navigates_to_the_time_module() {
    timeService.navigateToTime();
  }

  @Then("the time header breadcrumb should be displayed")
  public void the_time_header_breadcrumb_should_be_displayed() {
    Assert.assertTrue(timeService.isTimeModuleLoaded(), "Time module failed to load!");
  }
}
