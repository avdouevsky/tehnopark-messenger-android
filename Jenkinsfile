pipeline {
  agent any
  stages {
    stage('GrudleBuild') {
      steps {
        sh '''sh ./gradlew :app:assembleDebug
sh ./gradlew :app:assembleDebugAndroidTest'''
      }
    }
  }
}