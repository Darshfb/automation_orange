package com.orangehrm.services;

import com.orangehrm.pages.BuzzPage;
import org.openqa.selenium.WebDriver;

public class BuzzService {
  private final BuzzPage buzzPage;

  public BuzzService(WebDriver driver) {
    this.buzzPage = new BuzzPage(driver);
  }

  public void navigateToBuzz() {
    buzzPage.navigateToBuzzModule();
  }

  public void postBuzzMessage(String message) {
    buzzPage.enterPostContent(message);
    buzzPage.clickPost();
  }

  public boolean verifyPostCreated(String message) {
    return buzzPage.isPostContentVisibleInFeed(message);
  }
}
