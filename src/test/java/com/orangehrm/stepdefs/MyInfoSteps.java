package com.orangehrm.stepdefs;

import com.orangehrm.drivers.DriverFactory;
import com.orangehrm.services.MyInfoService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class MyInfoSteps {
  private final MyInfoService myInfoService;

  public MyInfoSteps() {
    this.myInfoService = new MyInfoService(DriverFactory.getDriver());
  }

  @Given("the user navigates to the My Info module")
  public void the_user_navigates_to_the_my_info_module() {
    myInfoService.navigateToMyInfo();
  }

  @Then("the personal details header section should be visible")
  public void the_personal_details_header_section_should_be_visible() {
    Assert.assertTrue(
        myInfoService.isMyInfoLoaded(), "My Info personal details header was not visible!");
  }
}
