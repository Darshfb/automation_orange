package com.orangehrm.stepdefs;

import com.github.javafaker.Faker;
import com.orangehrm.drivers.DriverFactory;
import com.orangehrm.services.PIMService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class PIMSteps {
  private final PIMService pimService;
  private final Faker faker;

  // Store variables within class session to carry state between steps
  private String generatedFirstName;
  private String generatedLastName;
  private String generatedEmployeeId;
  public static String lastCreatedEmployeeName;

  public PIMSteps() {
    this.pimService = new PIMService(DriverFactory.getDriver());
    this.faker = new Faker();
  }

  @Given("the user navigates to the PIM module")
  public void the_user_navigates_to_the_pim_module() {
    pimService.navigateToPim();
  }

  @When("the user adds a new employee with a dynamically generated name")
  public void the_user_adds_a_new_employee_with_a_dynamically_generated_name() {
    this.generatedFirstName = faker.name().firstName();
    this.generatedLastName = faker.name().lastName();
    lastCreatedEmployeeName = this.generatedFirstName + " " + this.generatedLastName;
    this.generatedEmployeeId =
        pimService.addEmployeeAndGetId(generatedFirstName, generatedLastName);
  }

  @Then("the employee should be successfully found in the employee list search")
  public void the_employee_should_be_successfully_found_in_the_employee_list_search() {
    boolean isFound = pimService.verifyEmployeeCreatedInList(generatedEmployeeId);
    Assert.assertTrue(
        isFound,
        "Newly created employee with ID " + generatedEmployeeId + " was not found in PIM list!");
  }

  @When("the user edits the employee middle name to {string}")
  public void the_user_edits_the_employee_middle_name_to(String middleName) {
    pimService.editEmployeeMiddleName(generatedEmployeeId, middleName);
  }

  @Then("the employee list should display the updated middle name")
  public void the_employee_list_should_display_the_updated_middle_name() {
    boolean isFound = pimService.verifyEmployeeMiddleNameInList(generatedEmployeeId, "QAUpdated");
    Assert.assertTrue(isFound, "Updated employee middle name was not found in PIM list!");
  }

  @When("the user deletes the created employee")
  public void the_user_deletes_the_created_employee() {
    pimService.deleteEmployee(generatedEmployeeId);
  }

  @Then("the employee search should return no results")
  public void the_employee_search_should_return_no_results() {
    boolean isFound = pimService.verifyEmployeeCreatedInList(generatedEmployeeId);
    Assert.assertFalse(isFound, "Deleted employee was still found in PIM list!");
  }
}
