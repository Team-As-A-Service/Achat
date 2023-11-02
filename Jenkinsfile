pipeline {
    agent any
    environment {
            SONARQUBE_URL = 'http://192.168.33.130:9000'
            SONARQUBE_USERNAME = 'admin'
            SONARQUBE_PASSWORD = 'azerty'
        }

    stages {
        stage('Clonage') {
            steps {
                script {
                    git(branch: 'main', credentialsId: 'ssh', url: 'https://ghp_5Uuc6I1nwqdVaFtYyIDY6otLMTd77E1Y@github.com/Team-As-A-Service/Achat.git')
                }
            }
        }

        stage('Maven Clean') {
                    steps {
                        sh 'mvn clean'
                    }
                }

                stage('Maven Compile') {
                    steps {
                        sh 'mvn compile'
                    }
                }
              stage('SonarQube Analysis') {
                         steps {
                             script {
                                 sh "mvn sonar:sonar -Dsonar.host.url=${SONARQUBE_URL} -Dsonar.login=${SONARQUBE_USERNAME} -Dsonar.password=${SONARQUBE_PASSWORD}"
                             }
                         }
              }


    stage('Déploiement') {
            steps {
                script {
                    sh 'mvn deploy -DskipTests'
                }
            }
        }



stage('Docker build image') {
            steps {
                script {
                    sh 'docker build -t nidhal2/achat:1-0 .'
                }
            }

        }
        stage('Docker push image') {
            steps {
                script {
                    sh "docker login -u nidhal2 -p 213JMT6518 "
                    sh "docker push nidhal2/achat:1-0"
                }
            }

        }


    }
}










