pipeline {
  agent any
  tools {
    maven 'MAVEN_3_9_11'
    jdk 'JDK_24'
  }

  stages {
    stage ('Compile Project') {
      steps {
        withMaven(maven : 'MAVEN_3_9_11') {
            bat 'mvn clean compile'
        }
      }
    }

    stage('Validate Checkstyle') {
      steps {
        withMaven(maven: 'MAVEN_3_9_11') {
          bat 'mvn checkstyle:check'
        }
      }
    }

    stage('Validate Unit Tests') {
      steps {
        withMaven(maven: 'MAVEN_3_9_11') {
          bat 'mvn test'
        }
      }
    }

    stage('Validate Test Coverage') {
      steps {
        withMaven(maven: 'MAVEN_3_9_11') {
          bat 'mvn clean verify jacoco:report'
          bat 'mvn jacoco:check'
        }
      }
    }

	 stage ('SonarQube Analysis') {
        steps {
            withSonarQubeEnv('sonarLocal') {
                bat 'mvn clean verify sonar:sonar -Dsonar.projectKey=learning-center'
            }
        }
     }

    stage ('package Project') {
        steps {
            withMaven(maven : 'MAVEN_3_9_11') {
                bat 'mvn package'
            }
        }
    }


    }
}
