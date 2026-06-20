package com.orangehrm.stepdefs;

import com.orangehrm.drivers.DriverFactory;
import com.orangehrm.services.AdminService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class AdminSteps {
  private final AdminService adminService;

  public AdminSteps() {
    this.adminService = new AdminService(DriverFactory.getDriver());
  }

  @Given("the user navigates to the Admin module")
  public void the_user_navigates_to_the_admin_module() {
    adminService.navigateToAdmin();
  }

  @When("the user searches for the username {string}")
  public void the_user_searches_for_the_username(String username) {
    adminService.searchUser(username);
  }

  @Then("the user should see {string} in the search results list")
  public void the_user_should_see_in_the_search_results_list(String username) {
    Assert.assertTrue(
        adminService.isUserFoundInResults(username),
        "Expected user " + username + " to be found in results table");
  }

  @When("the user searches for a non-existing username {string}")
  public void the_user_searches_for_a_non_existing_username(String username) {
    adminService.searchUser(username);
  }

  @Then("a {string} message should be displayed in the results table")
  public void a_message_should_be_displayed_in_the_results_table(String message) {
    Assert.assertTrue(
        adminService.isNoRecordsFoundMessageDisplayed(),
        "Expected 'No Records Found' message to be visible");
  }

  private String generatedUsername;
  private String generatedPassword;

  @When("the user creates a system user linked to that employee")
  public void the_user_creates_a_system_user_linked_to_that_employee() {
    this.generatedUsername = "user" + System.currentTimeMillis();
    this.generatedPassword = "Password123!";
    // Use full employee name to reliably pick the correct employee from suggestions
    String fullName = PIMSteps.lastCreatedEmployeeName;
    adminService.createSystemUser("ESS", fullName, "Enabled", generatedUsername, generatedPassword);
  }

  @Then("the new user should be successfully found in the system users search")
  public void the_new_user_should_be_successfully_found_in_the_system_users_search() {
    boolean isFound = adminService.isUserFoundInResultsWithRetry(generatedUsername, 5, 2);
    Assert.assertTrue(isFound, "New user was not found in results!");
  }

  @When("the user edits the system user status to {string}")
  public void the_user_edits_the_system_user_status_to(String status) {
    adminService.updateUserStatus(generatedUsername, status);
  }

  @Then("the user should see the status {string} in the search results list")
  public void the_user_should_see_the_status_in_the_search_results_list(String status) {
    adminService.searchUser(generatedUsername);
    boolean isFound = adminService.isUserStatusInResults(generatedUsername, status);
    Assert.assertTrue(isFound, "Updated status " + status + " was not found in results list!");
  }

  @When("the user deletes the created system user")
  public void the_user_deletes_the_created_system_user() {
    adminService.deleteUser(generatedUsername);
  }

  @Then("the system user search should return no results")
  public void the_system_user_search_should_return_no_results() {
    adminService.searchUser(generatedUsername);
    boolean isFound = adminService.isUserFoundInResults(generatedUsername);
    Assert.assertFalse(isFound, "Deleted system user was still found in results list!");
  }
}
