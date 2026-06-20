package com.orangehrm.stepdefs;

import com.github.javafaker.Faker;
import com.orangehrm.drivers.DriverFactory;
import com.orangehrm.services.RecruitmentService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class RecruitmentSteps {
  private final RecruitmentService recruitmentService;
  private final Faker faker;

  public RecruitmentSteps() {
    this.recruitmentService = new RecruitmentService(DriverFactory.getDriver());
    this.faker = new Faker();
  }

  @Given("the user navigates to the Recruitment module")
  public void the_user_navigates_to_the_recruitment_module() {
    recruitmentService.navigateToRecruitment();
  }

  @When("the user adds a new candidate with a dynamically generated name")
  public void the_user_adds_a_new_candidate_with_a_dynamically_generated_name() {
    String first = faker.name().firstName();
    String last = faker.name().lastName();
    String email = faker.internet().emailAddress();
    recruitmentService.createCandidate(first, last, email);
  }

  @Then("the candidate registration should complete successfully")
  public void the_candidate_registration_should_complete_successfully() {
    Assert.assertTrue(
        recruitmentService.isRecruitmentLoaded(),
        "Failed to stay on Recruitment screens after save");
  }
}
