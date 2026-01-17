pipeline {
    agent any

    tools {
        maven 'MAVEN'
        jdk 'Java_JDK'
    }

    environment {
        BROWSER = "${params.BROWSER}"
        ENV = "${params.ENV ?: 'qa'}"
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/nextgentestingacademy/travel-site.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Execute Tests') {
            steps {
                bat "mvn test -Denv=$ENV"
            }
        }

        stage('Publish Reports') {
            steps {
                junit 'target/surefire-reports/*.xml'
                publishHTML([
                  allowMissing: true,
                  alwaysLinkToLastBuild: true,
                  keepAll: true,
                  reportDir: 'test-output',
                  reportFiles: 'ExtentReport.html',
                  reportName: 'Extent Report'])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/**/*.html', fingerprint: true
        }
    }
}