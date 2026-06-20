pipeline {
    agent any

    tools {
        // Ensure you have a Maven tool configured in Jenkins Global Tool Configuration named 'maven-3'
        maven 'maven-3'
        // Ensure you have a JDK configured named 'jdk-17' (or match your Jenkins setup)
        jdk 'jdk-17'
    }

    environment {
        // You can set execution.mode to remote if you're using Docker Selenium Grid on Jenkins
        EXECUTION_MODE = 'local' 
        // Replace with your team's email or use a Jenkins credential
        RECIPIENT_EMAIL = 'your-team@company.com'
    }

    stages {
        stage('Checkout') {
            steps {
                // Pulls the code from the Git repository configured in the Jenkins job
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                echo "Running OrangeHRM Test Automation Suite..."
                // Runs Maven clean verify. -e enables stacktraces for debugging
                // We use catchError to ensure the pipeline continues to reporting even if tests fail
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    sh 'mvn clean verify -e -Dexecution.mode=${EXECUTION_MODE}'
                }
            }
        }
    }

    post {
        always {
            echo "Generating Allure Report..."
            // Requires the 'Allure Jenkins Plugin' to be installed
            allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            
            // Clean up workspace to save disk space
            cleanWs()
        }
        success {
            echo "Tests passed! Sending success email..."
            emailext (
                subject: "SUCCESS: OrangeHRM Automation Build #${BUILD_NUMBER}",
                body: """<p>Great news! The OrangeHRM automation test suite passed successfully.</p>
                         <p><strong>Build Number:</strong> ${BUILD_NUMBER}</p>
                         <p><strong>Build URL:</strong> <a href="${BUILD_URL}">${BUILD_URL}</a></p>
                         <p><strong>Allure Report:</strong> <a href="${BUILD_URL}allure/">View Full Test Report</a></p>""",
                to: "${RECIPIENT_EMAIL}",
                mimeType: 'text/html'
            )
        }
        unstable {
            echo "Tests failed! Sending failure email..."
            emailext (
                subject: "FAILED: OrangeHRM Automation Build #${BUILD_NUMBER}",
                body: """<p>Attention: Some tests in the OrangeHRM automation suite failed.</p>
                         <p><strong>Build Number:</strong> ${BUILD_NUMBER}</p>
                         <p><strong>Build URL:</strong> <a href="${BUILD_URL}">${BUILD_URL}</a></p>
                         <p><strong>Allure Report:</strong> <a href="${BUILD_URL}allure/">View Full Test Report</a></p>
                         <p>Please check the Allure report to investigate the failing scenarios.</p>""",
                to: "${RECIPIENT_EMAIL}",
                mimeType: 'text/html'
            )
        }
        failure {
            echo "Pipeline critically failed! Sending failure email..."
            emailext (
                subject: "CRITICAL FAILURE: OrangeHRM Automation Build #${BUILD_NUMBER}",
                body: """<p>Attention: The Jenkins pipeline encountered a critical error before tests could finish (e.g. compilation failure or infrastructure issue).</p>
                         <p><strong>Build Number:</strong> ${BUILD_NUMBER}</p>
                         <p><strong>Build URL:</strong> <a href="${BUILD_URL}">${BUILD_URL}</a></p>""",
                to: "${RECIPIENT_EMAIL}",
                mimeType: 'text/html'
            )
        }
    }
}
