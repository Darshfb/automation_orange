package com.orangehrm.services;

import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.PIMPage;
import org.openqa.selenium.WebDriver;

public class PIMService {
  private final DashboardPage dashboardPage;
  private final PIMPage pimPage;

  public PIMService(WebDriver driver) {
    this.dashboardPage = new DashboardPage(driver);
    this.pimPage = new PIMPage(driver);
  }

  public void navigateToPim() {
    dashboardPage.navigateToPIM();
    pimPage.waitForPageLoad();
  }

  public String addEmployeeAndGetId(String firstName, String lastName) {
    pimPage.navigateToAddEmployee();
    pimPage.createEmployee(firstName, lastName);
    String empId = pimPage.getEmployeeIdFromForm();
    pimPage.clickSave();
    return empId;
  }

  public boolean verifyEmployeeCreatedInList(String empId) {
    pimPage.navigateToEmployeeList();
    pimPage.clickReset();
    pimPage.searchEmployeeById(empId);
    return pimPage.isEmployeeIdInList(empId);
  }

  public void editEmployeeMiddleName(String empId, String middleName) {
    pimPage.navigateToEmployeeList();
    pimPage.clickReset();
    pimPage.searchEmployeeById(empId);
    pimPage.clickEditEmployee(empId);
    pimPage.updateMiddleName(middleName);
    pimPage.navigateToEmployeeList();
    pimPage.clickReset();
  }

  public boolean verifyEmployeeMiddleNameInList(String empId, String middleName) {
    pimPage.navigateToEmployeeList();
    pimPage.clickReset();
    pimPage.searchEmployeeById(empId);
    return pimPage.isMiddleNameInList(empId, middleName);
  }

  public void deleteEmployee(String empId) {
    pimPage.navigateToEmployeeList();
    pimPage.clickReset();
    pimPage.searchEmployeeById(empId);
    pimPage.clickDeleteEmployee(empId);
  }
}
