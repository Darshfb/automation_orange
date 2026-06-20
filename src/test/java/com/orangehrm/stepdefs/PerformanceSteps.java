package com.orangehrm.stepdefs;

import com.orangehrm.drivers.DriverFactory;
import com.orangehrm.services.PerformanceService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class PerformanceSteps {
  private final PerformanceService performanceService;

  public PerformanceSteps() {
    this.performanceService = new PerformanceService(DriverFactory.getDriver());
  }

  @Given("the user navigates to the Performance module")
  public void the_user_navigates_to_the_performance_module() {
    performanceService.navigateToPerformance();
  }

  @Then("the performance page header should be visible")
  public void the_performance_page_header_should_be_visible() {
    Assert.assertTrue(
        performanceService.isPerformanceLoaded(), "Performance module header failed to render!");
  }
}
