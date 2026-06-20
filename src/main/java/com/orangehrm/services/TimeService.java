package com.orangehrm.services;

import com.orangehrm.pages.TimePage;
import org.openqa.selenium.WebDriver;

public class TimeService {
  private final TimePage timePage;

  public TimeService(WebDriver driver) {
    this.timePage = new TimePage(driver);
  }

  public void navigateToTime() {
    timePage.navigateToTimeModule();
  }

  public void searchEmployeeTimesheet(String empName) {
    timePage.searchTimesheetForEmployee(empName);
  }

  public boolean isTimeModuleLoaded() {
    return timePage.isTimeHeaderDisplayed();
  }
}
