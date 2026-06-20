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
    // Sleep a short buffer before checking the feed
    try {
      Thread.sleep(3000);
    } catch (InterruptedException ignored) {
    }
    int attempts = 0;
    while (attempts < 15) {
      try {
        String bodyText = driver.findElement(By.tagName("body")).getText();
        if (bodyText.contains(message)) {
          return true;
        }
      } catch (Exception e) {
        log.warn("Attempt {} checking page body text failed: {}", attempts, e.getMessage());
      }
      if (attempts == 3 || attempts == 8) {
        log.info("Post not visible. Refreshing feed page...");
        try {
          driver.navigate().refresh();
          waitForPageLoad();
          try {
            Thread.sleep(2000);
          } catch (InterruptedException ignored) {
          }
        } catch (Exception e) {
          log.warn("Failed to refresh page: {}", e.getMessage());
        }
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
      attempts++;
    }
    return false;
  }
}
