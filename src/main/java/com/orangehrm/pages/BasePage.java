package com.orangehrm.pages;

import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.LoggerUtils;
import java.time.Duration;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
  protected WebDriver driver;
  protected WebDriverWait wait;
  protected final Logger log;

  public BasePage(WebDriver driver) {
    this.driver = driver;
    int timeout = ConfigReader.getIntProperty("timeout.explicit");
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    this.log = LoggerUtils.getLogger(this.getClass());
  }

  protected WebElement find(By locator) {
    return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  protected WebElement waitForVisibility(By locator) {
    log.info("Waiting for visibility of element: {}", locator);
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public void waitForSpinnerToDisappear() {
    try {
      WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
      shortWait.until(
          ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".oxd-form-loader")));
    } catch (Exception e) {
      // Logged as debug; normal if spinner was already gone or didn't appear
      log.debug("Spinner loader did not appear or already invisible.");
    }
  }

  protected void click(By locator) {
    log.info("Clicking element: {}", locator);
    int attempts = 0;
    while (attempts < 3) {
      try {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        return;
      } catch (ElementClickInterceptedException e) {
        log.warn("Click intercepted on {}. Waiting for spinner...", locator);
        waitForSpinnerToDisappear();
        attempts++;
      } catch (StaleElementReferenceException e) {
        log.warn("Stale element reference on {}. Retrying...", locator);
        attempts++;
      }
    }
    driver.findElement(locator).click();
  }

  protected void type(By locator, String text) {
    log.info("Typing text '{}' into element: {}", text, locator);
    int attempts = 0;
    while (attempts < 3) {
      try {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        element.sendKeys(text);
        return;
      } catch (ElementClickInterceptedException e) {
        log.warn("Type intercepted on {}. Waiting for spinner...", locator);
        waitForSpinnerToDisappear();
        attempts++;
      } catch (StaleElementReferenceException e) {
        log.warn("Stale element reference on {}. Retrying...", locator);
        attempts++;
      }
    }
    WebElement element = driver.findElement(locator);
    element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
    element.sendKeys(text);
  }

  protected String getText(By locator) {
    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    String text = element.getText();
    log.info("Retrieved text '{}' from element: {}", text, locator);
    return text;
  }

  protected boolean isDisplayed(By locator) {
    try {
      WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
      boolean displayed = element.isDisplayed();
      log.info("Element displayed status: {} for locator: {}", displayed, locator);
      return displayed;
    } catch (TimeoutException | NoSuchElementException e) {
      log.warn("Element not displayed: {}", locator);
      return false;
    }
  }

  protected List<WebElement> findElements(By locator) {
    return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
  }

  protected void selectFromDropdown(By dropdownLocator, By optionLocator) {
    log.info("Selecting option from dropdown locator: {}", dropdownLocator);
    click(dropdownLocator);
    click(optionLocator);
  }

  public void waitForPageLoad() {
    wait.until(
        webDriver ->
            ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .equals("complete"));
  }
}
