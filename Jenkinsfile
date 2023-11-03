pipeline {
    agent any
    environment {
            SONARQUBE_URL = 'http://192.168.1.11:9000'
            SONARQUBE_USERNAME = 'admin'
            SONARQUBE_PASSWORD = 'adminadmin'
            registry = "henidevops/devops-backend"
            registryCredential = 'dockerhub_id'
            dockerImage = ''
        }
    stages {
        stage('Récupération du code de sa propre branche') {
            steps {
                script {
                     try {
                        git(branch: 'OperateurTest', credentialsId: 'git-ssh', url: 'git@github.com:Team-As-A-Service/Achat.git')
                    } catch (Exception e) {
                        echo "Retrying the 'Récupération du code de sa propre branche' stage..."
                        git(branch: 'OperateurTest', credentialsId: 'git-ssh', url: 'git@github.com:Team-As-A-Service/Achat.git')
                    }
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
        stage('Building our image') {
                    steps {
                        script {
                            dockerImage = docker.build registry + ":$BUILD_NUMBER"
                        }
                    }
                }
                stage('Deploy our image') {
                    steps {
                        script {
                            docker.withRegistry( '', registryCredential ) {
                                dockerImage.push()
                            }
                        }
                    }
                }
                stage('Cleaning up') {
                    steps {
                        sh "docker rmi $registry:$BUILD_NUMBER"
                    }
                }
                stage('Docker Compose') {
                                    steps {
                                        sh "docker compose up -d"
                                    }
                                }
    }
}
