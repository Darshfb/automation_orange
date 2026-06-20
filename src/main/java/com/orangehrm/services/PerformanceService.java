package com.orangehrm.services;

import com.orangehrm.pages.PerformancePage;
import org.openqa.selenium.WebDriver;

public class PerformanceService {
  private final PerformancePage performancePage;

  public PerformanceService(WebDriver driver) {
    this.performancePage = new PerformancePage(driver);
  }

  public void navigateToPerformance() {
    performancePage.navigateToPerformanceModule();
  }

  public boolean isPerformanceLoaded() {
    return performancePage.isPerformanceHeaderDisplayed();
  }
}
