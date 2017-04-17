pipeline {
  agent any
  stages {
    stage('GradleBuild') {
      steps {
        sh 'sh ./gradlew :app:assembleDebug'
      }
    }
    stage('Assemble Release') {
      steps {
        sh 'sh ./gradlew :app:assembleAuto'
      }
    }
  }
}