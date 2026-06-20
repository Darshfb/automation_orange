package com.orangehrm.hooks;

import com.orangehrm.drivers.DriverFactory;
import com.orangehrm.utils.LoggerUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class CustomHooks {
  private static final Logger log = LoggerUtils.getLogger(CustomHooks.class);

  @Before
  public void setup(Scenario scenario) {
    log.info("Starting Scenario: [{}]", scenario.getName());
  }

  @After
  public void tearDown(Scenario scenario) {
    log.info("Ending Scenario: [{}] Status: {}", scenario.getName(), scenario.getStatus());

    if (scenario.isFailed()) {
      try {
        log.error("Scenario [{}] FAILED. Capturing screenshot...", scenario.getName());
        byte[] screenshot =
            ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", scenario.getName() + " - Failure Screenshot");

        String pageSource = DriverFactory.getDriver().getPageSource();
        scenario.attach(pageSource.getBytes(), "text/html", "DOM Source");

      } catch (Exception e) {
        log.error("Failed to capture screenshot: ", e);
      }
    }

    DriverFactory.quitDriver();
  }
}
