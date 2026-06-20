package com.orangehrm.stepdefs;

import com.github.javafaker.Faker;
import com.orangehrm.drivers.DriverFactory;
import com.orangehrm.services.BuzzService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class BuzzSteps {
  private final BuzzService buzzService;
  private final Faker faker;
  private String generatedPostMessage;

  public BuzzSteps() {
    this.buzzService = new BuzzService(DriverFactory.getDriver());
    this.faker = new Faker();
  }

  @Given("the user navigates to the Buzz module")
  public void the_user_navigates_to_the_buzz_module() {
    buzzService.navigateToBuzz();
  }

  @When("the user posts a dynamic message on the feed")
  public void the_user_posts_a_dynamic_message_on_the_feed() {
    this.generatedPostMessage = "QA Automation Post " + System.currentTimeMillis();
    buzzService.postBuzzMessage(generatedPostMessage);
  }

  @Then("the message should be successfully displayed on the social timeline")
  public void the_message_should_be_successfully_displayed_on_the_social_timeline() {
    boolean isPosted = buzzService.verifyPostCreated(generatedPostMessage);
    Assert.assertTrue(isPosted, "Newly created Buzz post was not found on feed!");
  }
}
