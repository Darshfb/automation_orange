#!/bin/bash
echo "=============================================="
echo "Running OrangeHRM Test Suite (Local Dev)..."
echo "=============================================="
mvn clean verify

echo "=============================================="
echo "Launching Reports..."
echo "=============================================="

# Open Cucumber HTML Report
if [ -f "target/cucumber-reports/cucumber.html" ]; then
    if [[ "$OSTYPE" == "darwin"* ]]; then
        open target/cucumber-reports/cucumber.html
    else
        xdg-open target/cucumber-reports/cucumber.html
    fi
fi

# Open TestNG Report
if [ -f "target/surefire-reports/emailable-report.html" ]; then
    if [[ "$OSTYPE" == "darwin"* ]]; then
        open target/surefire-reports/emailable-report.html
    else
        xdg-open target/surefire-reports/emailable-report.html
    fi
fi
