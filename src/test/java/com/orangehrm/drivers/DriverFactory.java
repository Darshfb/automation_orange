package com.orangehrm.drivers;

import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.LoggerUtils;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {
  private static final Logger log = LoggerUtils.getLogger(DriverFactory.class);
  private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

  public static WebDriver getDriver() {
    if (driverThreadLocal.get() == null) {
      initializeDriver();
    }
    return driverThreadLocal.get();
  }

  private static synchronized void initializeDriver() {
    String browser = ConfigReader.getProperty("browser", "chrome").toLowerCase();
    String execMode = ConfigReader.getProperty("execution.mode", "local").toLowerCase();
    WebDriver driver;

    log.info("Initializing driver. Browser: {}, Mode: {}", browser, execMode);

    if (execMode.equals("remote")) {
      driver = setupRemoteDriver(browser);
    } else {
      driver = setupLocalDriver(browser);
      com.orangehrm.utils.NetworkUtils.applyNetworkProfile(driver);
    }

    int implicitWait = ConfigReader.getIntProperty("timeout.implicit");
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
    try {
      boolean headless =
          Boolean.parseBoolean(ConfigReader.getProperty("browser.headless", "false"));
      if (headless) {
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
      } else {
        driver.manage().window().maximize();
      }
    } catch (Exception e) {
      log.warn("Failed to set window size or maximize: {}", e.getMessage());
    }

    driverThreadLocal.set(driver);
  }

  private static WebDriver setupLocalDriver(String browser) {
    boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("browser.headless", "false"));
    switch (browser) {
      case "firefox":
        FirefoxOptions ffOptions = new FirefoxOptions();
        if (headless) {
          ffOptions.addArguments("-headless");
          ffOptions.addArguments("--width=1920");
          ffOptions.addArguments("--height=1080");
        }
        return new FirefoxDriver(ffOptions);
      case "edge":
        EdgeOptions edgeOptions = new EdgeOptions();
        if (headless) {
          edgeOptions.addArguments("--headless=new");
          edgeOptions.addArguments("--window-size=1920,1080");
        }
        String edgeDevice = ConfigReader.getProperty("mobile.device", "");
        if (!edgeDevice.isEmpty()) {
          Map<String, String> mobileEmulation = new HashMap<>();
          mobileEmulation.put("deviceName", edgeDevice);
          edgeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        }
        return new EdgeDriver(edgeOptions);
      case "chrome":
      default:
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-gpu");
        if (headless) {
          chromeOptions.addArguments("--headless=new");
          chromeOptions.addArguments("--window-size=1920,1080");
        }
        String chromeDevice = ConfigReader.getProperty("mobile.device", "");
        if (!chromeDevice.isEmpty()) {
          Map<String, String> mobileEmulation = new HashMap<>();
          mobileEmulation.put("deviceName", chromeDevice);
          chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        }
        return new ChromeDriver(chromeOptions);
    }
  }

  private static WebDriver setupRemoteDriver(String browser) {
    String gridUrl = ConfigReader.getProperty("grid.url");
    boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("browser.headless", "false"));
    try {
      URL url = new URL(gridUrl);
      switch (browser) {
        case "firefox":
          FirefoxOptions ffOptions = new FirefoxOptions();
          if (headless) {
            ffOptions.addArguments("-headless");
            ffOptions.addArguments("--width=1920");
            ffOptions.addArguments("--height=1080");
          }
          return new RemoteWebDriver(url, ffOptions);
        case "edge":
          EdgeOptions edgeOptions = new EdgeOptions();
          if (headless) {
            edgeOptions.addArguments("--headless=new");
            edgeOptions.addArguments("--window-size=1920,1080");
          }
          String remoteEdgeDevice = ConfigReader.getProperty("mobile.device", "");
          if (!remoteEdgeDevice.isEmpty()) {
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", remoteEdgeDevice);
            edgeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
          }
          return new RemoteWebDriver(url, edgeOptions);
        case "chrome":
        default:
          ChromeOptions chromeOptions = new ChromeOptions();
          if (headless) {
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--window-size=1920,1080");
          }
          String remoteChromeDevice = ConfigReader.getProperty("mobile.device", "");
          if (!remoteChromeDevice.isEmpty()) {
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", remoteChromeDevice);
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
          }
          return new RemoteWebDriver(url, chromeOptions);
      }
    } catch (MalformedURLException e) {
      log.error("Invalid grid URL specified: {}", gridUrl, e);
      throw new RuntimeException("Malformed remote grid URL: " + e.getMessage());
    }
  }

  public static void quitDriver() {
    if (driverThreadLocal.get() != null) {
      log.info("Quitting WebDriver for thread: {}", Thread.currentThread().getName());
      driverThreadLocal.get().quit();
      driverThreadLocal.remove();
    }
  }
}
