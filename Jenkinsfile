pipeline {
    agent any

    environment {
        REMOTE          = 'https://user1:1234@selenoid.autotests.cloud/wd/hub'
        BROWSER         = 'chrome'
        BROWSER_VERSION = '127.0'
        BROWSER_SIZE    = '1920x1080'
        TELEGRAM_BOT_TOKEN = credentials('telegram-bot-token')
        TELEGRAM_CHAT_ID   = credentials('telegram-chat-id')
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run tests') {
            steps {
                bat """
                  gradlew.bat clean test ^
                    -Dbrowser=%BROWSER% ^
                    -DbrowserVersion=%BROWSER_VERSION% ^
                    -DbrowserSize=%BROWSER_SIZE% ^
                    -Dremote=%REMOTE%
                """
            }
        }

        stage('Allure report') {
            steps {
                allure includeProperties: false,
                       results: [[path: 'build/allure-results']]
            }
        }
    }

    post {

        success {
            script {
                bat """
                  curl -s -X POST "https://api.telegram.org/bot%TELEGRAM_BOT_TOKEN%/sendMessage" ^
                    -d "chat_id=%TELEGRAM_CHAT_ID%" ^
                    -d "text=✅ UI tests PASSED: %JOB_NAME% #%BUILD_NUMBER%"
                """
            }
        }

        failure {
            script {
                bat """
                  curl -s -X POST "https://api.telegram.org/bot%TELEGRAM_BOT_TOKEN%/sendMessage" ^
                    -d "chat_id=%TELEGRAM_CHAT_ID%" ^
                    -d "text=❌ UI tests FAILED: %JOB_NAME% #%BUILD_NUMBER%. Подробнее: %BUILD_URL%"
                """
            }
        }

        always {
            archiveArtifacts artifacts: 'build/reports/tests/test/**',
                              fingerprint: true
        }
    }
}