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

    stage ('Testing Project') {
      steps {
        withMaven(maven : 'MAVEN_3_9_11') {
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

	  /*stage ('sonarQube Analysis') {
			steps {
				withSonarQubeEnv('sonarLocal') {
					bat 'mvn clean verify sonar:sonar -Dsonar.projectKey=one'
				}
			}
		}*/

    stage ('package Project') {
        steps {
            withMaven(maven : 'MAVEN_3_9_11') {
                bat 'mvn package'
            }
        }
    }
		/* // Descomentar cuando se tenga instalado en Tomcat
		stage('Deploy tomcat') {
        steps {
            echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL} direcion ${env.WORKSPACE}"
            withMaven(maven : 'MAVEN_3_6_3') {
			bat '"C:\\Program Files\\Git\\mingw64\\bin\\curl.exe" -T ".\\target\\sistema-ventas-spring.war" "http://tomcat:tomcat@localhost:9090/manager/text/deploy?path=/sistema-ventas-spring&update=true"'
            }
        }
    }*/

    }
}
