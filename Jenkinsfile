pipeline {
  agent any
  stages {
    stage('GradleBuild') {
      steps {
        sh '''sh ./gradlew :app:assembleDebug
sh ./gradlew :app:assembleDebugAndroidTest'''
      }
    }
    stage('Assemble Release') {
      steps {
        sh 'sh ./gradlew :app:assembleAuto'
      }
    }
  }
}