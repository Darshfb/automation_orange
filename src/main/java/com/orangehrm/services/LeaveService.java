package com.orangehrm.services;

import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LeavePage;
import org.openqa.selenium.WebDriver;

public class LeaveService {
  private final DashboardPage dashboardPage;
  private final LeavePage leavePage;

  public LeaveService(WebDriver driver) {
    this.dashboardPage = new DashboardPage(driver);
    this.leavePage = new LeavePage(driver);
  }

  public void navigateToLeave() {
    dashboardPage.navigateToLeave();
    leavePage.waitForPageLoad();
  }

  public void applyForLeave(String fromDate, String toDate, String comments) {
    leavePage.navigateToApplyLeave();
    leavePage.fillApplyLeaveForm(fromDate, toDate, comments);
    leavePage.submitApplyLeave();
  }
}
