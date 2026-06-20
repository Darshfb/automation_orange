# OrangeHRM Advanced Test Automation Framework

An enterprise-grade, highly scalable UI and API hybrid test automation framework built to test the OrangeHRM application. 

This framework goes beyond standard functional testing by incorporating continuous integration, containerized parallel execution, and advanced resiliency testing (mobile emulation and network throttling) to ensure the application behaves flawlessly under real-world conditions.

## 🚀 Tech Stack
- **Language**: Java 17
- **Core Automation**: Selenium WebDriver 4
- **BDD Framework**: Cucumber 7
- **Test Runner**: TestNG
- **API Testing**: RestAssured
- **Build Tool**: Apache Maven
- **Reporting**: Allure Reports
- **CI/CD**: GitHub Actions & Docker Compose

## ✨ Key Features Implemented

1. **Hybrid UI/API Testing**: Utilizes RestAssured (`ApiUtils.java`) to handle test prerequisites and data setup at the API layer, significantly accelerating test execution times.
2. **Scalable CI/CD Pipeline**: Fully integrated with GitHub Actions (`maven.yml`). On every push, it automatically spins up a Docker Selenium Grid, executes 23 test scenarios in parallel, and publishes the Allure report as a downloadable artifact.
3. **Advanced Reporting**: Custom Cucumber hooks automatically capture a full-page screenshot *and* the entire HTML DOM source upon any test failure, embedding them directly into the Allure report for rapid debugging.
4. **Mobile Emulation**: Uses Chrome DevTools Protocol (CDP) to natively emulate mobile viewports (e.g., iPhone 12 Pro) and touch interactions.
5. **Network Resiliency Testing**: Uses CDP to throttle network conditions programmatically. It can simulate "Offline", "Slow 3G", and "Fast 3G" networks to ensure the application degrades gracefully.
6. **Code Quality Standards**: Integrated `spotless-maven-plugin` to enforce Google Java Style code formatting across the repository.

---

## 💻 How to Run Locally

### Prerequisites
- JDK 17
- Apache Maven
- Docker (Optional, for Grid execution)

### 1. Standard Execution (Desktop)
To run the entire suite locally using your default browser and network:
```bash
mvn clean verify
```

### 2. Mobile Emulation Execution
To test the responsive layout of the application, specify a mobile device name:
```bash
mvn clean verify -Dmobile.device="iPhone 12 Pro"
```

### 3. Network Throttling Execution
To test how the application handles poor connectivity, specify a network profile (`offline`, `slow_3g`, `fast_3g`):
```bash
mvn clean verify -Dnetwork.profile="slow_3g"
```
*(Note: You can combine both Mobile and Network flags simultaneously!)*

### 4. Docker Grid Execution
To run the tests headlessly against a containerized Selenium Grid (exactly as it runs in CI):
```bash
docker compose up -d
mvn clean verify -Dexecution.mode=remote
```

---

## 📊 Viewing Test Reports
This framework uses Allure for comprehensive reporting. After any execution completes, generate and serve the interactive HTML report by running:
```bash
mvn allure:serve
```

## 🧹 Code Formatting
To automatically format all Java source code to comply with Google Java styling standards:
```bash
mvn spotless:apply
```
