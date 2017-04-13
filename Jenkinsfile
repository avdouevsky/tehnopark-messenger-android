pipeline {
  agent any
  stages {
    stage('GradleBuild') {
      steps {
        sh '''sh ./gradlew :app:assembleDebug
sh ./gradlew :app:assembleDebugAndroidTest'''
      }
    }
  }
}
