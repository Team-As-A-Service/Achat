pipeline {
    agent any
    environment {
            SONARQUBE_URL = 'http://192.168.1.23:9000'
            SONARQUBE_USERNAME = 'admin'
            SONARQUBE_PASSWORD = 'adminadmin'
        }
    stages {
        stage('Récupération du code de sa propre branche') {
            steps {
                script {
                    git(branch: 'OperateurTest', credentialsId: 'git-ssh', url: 'git@github.com:Team-As-A-Service/Achat.git')
                }
            }
        }

        stage('Lancement de la commande Maven') {
            steps {
                script {
                    sh 'mvn clean'
                    sh 'mvn compile'
                }
            }
        }

        stage('SonarQube Analysis') {
                         steps {
                             script {
                                 sh "mvn sonar:sonar -Dsonar.host.url=${SONARQUBE_URL} -Dsonar.login=${SONARQUBE_USERNAME} -Dsonar.password=${SONARQUBE_PASSWORD}"
                             }
                         }
              }
        stage('Unit Tests') {
                    steps {
                        // Étape pour exécuter les tests unitaires
                        sh 'mvn test'
                    }
                }
        stage('Nexus deploiment') {
            steps {
                script {
                    sh 'mvn deploy -DskipTests'
                }
            }
        }
    }
}
