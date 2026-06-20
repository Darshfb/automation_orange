package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BuzzPage extends BasePage {
  private final By buzzHeader =
      By.xpath("//h6[contains(@class,'oxd-topbar-header-breadcrumb') and text()='Buzz']");
  private final By buzzMenu = By.xpath("//span[text()='Buzz']");
  private final By postTextarea = By.cssSelector("textarea.oxd-buzz-post-input");
  private final By postButton =
      By.xpath(
          "//textarea[contains(@class,'oxd-buzz-post-input')]/ancestor::form//button[@type='submit']");

  public BuzzPage(WebDriver driver) {
    super(driver);
  }

  public boolean isBuzzHeaderDisplayed() {
    return isDisplayed(buzzHeader);
  }

  public void navigateToBuzzModule() {
    click(buzzMenu);
    waitForPageLoad();
  }

  public void enterPostContent(String message) {
    click(postTextarea);
    type(postTextarea, message);
  }

  public void clickPost() {
    click(postButton);
    waitForPageLoad();
  }

  public boolean isPostContentVisibleInFeed(String message) {
    try {
      // Explicit wait: dynamically wait for the post to appear in the DOM
      wait.until(d -> d.findElement(By.tagName("body")).getText().contains(message));
      return true;
    } catch (org.openqa.selenium.TimeoutException e) {
      log.warn("Post not visible after initial wait. Refreshing feed page...");
      driver.navigate().refresh();
      waitForPageLoad();
      try {
        // Try one more time after refresh
        wait.until(d -> d.findElement(By.tagName("body")).getText().contains(message));
        return true;
      } catch (org.openqa.selenium.TimeoutException ex) {
        return false;
      }
    }
  }
}
