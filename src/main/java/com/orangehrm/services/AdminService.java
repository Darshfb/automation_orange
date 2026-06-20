package com.orangehrm.services;

import com.orangehrm.pages.AdminPage;
import com.orangehrm.pages.DashboardPage;
import org.openqa.selenium.WebDriver;

public class AdminService {
  private final DashboardPage dashboardPage;
  private final AdminPage adminPage;

  public AdminService(WebDriver driver) {
    this.dashboardPage = new DashboardPage(driver);
    this.adminPage = new AdminPage(driver);
  }

  public void navigateToAdmin() {
    dashboardPage.navigateToAdmin();
    adminPage.waitForPageLoad();
  }

  public void searchUser(String username) {
    adminPage.searchUser(username);
  }

  public boolean isUserFoundInResults(String username) {
    return adminPage.isUserFoundInResults(username);
  }

  public boolean isUserFoundInResultsWithRetry(String username, int attempts, int intervalSeconds) {
    for (int i = 0; i < attempts; i++) {
      searchUser(username);
      if (isUserFoundInResults(username)) {
        return true;
      }
      try {
        Thread.sleep(intervalSeconds * 1000L);
      } catch (InterruptedException ignored) {
      }
    }
    return false;
  }

  public void createSystemUser(
      String role, String employeeName, String status, String newUsername, String newPassword) {
    adminPage.clickAdd();
    adminPage.selectUserRole(role);
    adminPage.selectStatus(status);
    adminPage.enterEmployeeName(employeeName);
    adminPage.enterNewUsername(newUsername);
    adminPage.enterUserPassword(newPassword);
    adminPage.clickSaveUser();
  }

  public boolean isNoRecordsFoundMessageDisplayed() {
    return adminPage.isNoRecordsFoundMessageDisplayed();
  }

  public void updateUserStatus(String username, String status) {
    adminPage.searchUser(username);
    adminPage.clickEditUser(username);
    adminPage.selectStatus(status);
    adminPage.clickSaveUser();
  }

  public void deleteUser(String username) {
    adminPage.searchUser(username);
    adminPage.clickDeleteUser(username);
  }

  public boolean isUserStatusInResults(String username, String status) {
    return adminPage.isUserStatusInResults(username, status);
  }
}
