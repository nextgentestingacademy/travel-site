pipeline {
    agent any

    tools {
        maven 'Maven-3.9.2'
        jdk 'jdk17'
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
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Execute Tests') {
            steps {
                sh "mvn test -Denv=$ENV"
            }
        }

        stage('Publish Reports') {
            steps {
                junit 'target/surefire-reports/*.xml'
                publishHTML([
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