package com.orangehrm.services;

import com.orangehrm.pages.RecruitmentPage;
import org.openqa.selenium.WebDriver;

public class RecruitmentService {
  private final RecruitmentPage recruitmentPage;

  public RecruitmentService(WebDriver driver) {
    this.recruitmentPage = new RecruitmentPage(driver);
  }

  public void navigateToRecruitment() {
    recruitmentPage.navigateToRecruitmentModule();
  }

  public void createCandidate(String firstName, String lastName, String email) {
    recruitmentPage.clickAddCandidate();
    recruitmentPage.fillCandidateForm(firstName, lastName, email);
    recruitmentPage.saveCandidate();
  }

  public boolean isRecruitmentLoaded() {
    return recruitmentPage.isRecruitmentHeaderDisplayed();
  }
}
