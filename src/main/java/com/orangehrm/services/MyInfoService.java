package com.orangehrm.services;

import com.orangehrm.pages.MyInfoPage;
import org.openqa.selenium.WebDriver;

public class MyInfoService {
  private final MyInfoPage myInfoPage;

  public MyInfoService(WebDriver driver) {
    this.myInfoPage = new MyInfoPage(driver);
  }

  public void navigateToMyInfo() {
    myInfoPage.navigateToMyInfoModule();
  }

  public boolean isMyInfoLoaded() {
    return myInfoPage.isMyInfoHeaderDisplayed();
  }
}
