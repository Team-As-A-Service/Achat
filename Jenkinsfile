pipeline {
    agent any
    environment {
        SONARQUBE_URL = 'http://192.168.1.15:9000'
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
                        emailext(attachLog: true, body: 'The pipeline number' + ":$BUILD_NUMBER" + ' is failed !! Please check the logs file below !!', subject: 'Jenkins Pipeline Failed', to: 'heni.m.nechi@gmail.com')
                        throw e
                    }
                }
            }
        }

        stage('Lancement de la commande Maven') {
            steps {
                script {
                    try {
                        sh 'mvn clean'
                        sh 'mvn compile'
                    } catch (Exception e) {
                        emailext(attachLog: true, body: 'The pipeline number' + ":$BUILD_NUMBER" + ' is failed !! Please check the logs file below !!', subject: 'Jenkins Pipeline Failed', to: 'heni.m.nechi@gmail.com')
                        throw e
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    try {
                        sh "mvn sonar:sonar -Dsonar.host.url=${SONARQUBE_URL} -Dsonar.login=${SONARQUBE_USERNAME} -Dsonar.password=${SONARQUBE_PASSWORD}"
                    } catch (Exception e) {
                        emailext(attachLog: true, body: 'The pipeline number' + ":$BUILD_NUMBER" + ' is failed !! Please check the logs file below !!', subject: 'Jenkins Pipeline Failed', to: 'heni.m.nechi@gmail.com')
                        throw e
                    }
                }
            }
        }

        stage('Unit Tests') {
            steps {
                script {
                    try {
                        // Étape pour exécuter les tests unitaires
                        sh 'mvn test'
                    } catch (Exception e) {
                        emailext(attachLog: true, body: 'The pipeline number' + ":$BUILD_NUMBER" + ' is failed !! Please check the logs file below !!', subject: 'Jenkins Pipeline Failed', to: 'heni.m.nechi@gmail.com')
                        throw e
                    }
                }
            }
        }

        stage('Nexus deployment') {
            steps {
                script {
                    try {
                        sh 'mvn deploy -DskipTests'
                    } catch (Exception e) {
                        emailext(attachLog: true, body: 'The pipeline number' + ":$BUILD_NUMBER" + ' is failed !! Please check the logs file below !!', subject: 'Jenkins Pipeline Failed', to: 'heni.m.nechi@gmail.com')
                        throw e
                    }
                }
            }
        }

        stage('Building our image') {
            steps {
                script {
                    try {
                        dockerImage = docker.build registry + ":$BUILD_NUMBER"
                    } catch (Exception e) {
                        emailext(attachLog: true, body: 'The pipeline number' + ":$BUILD_NUMBER" + ' is failed !! Please check the logs file below !!', subject: 'Jenkins Pipeline Failed', to: 'heni.m.nechi@gmail.com')
                        throw e
                    }
                }
            }
        }

        stage('Deploy our image') {
            steps {
                script {
                    try {
                        docker.withRegistry('', registryCredential) {
                            dockerImage.push()
                        }
                    } catch (Exception e) {
                        emailext(attachLog: true, body: 'The pipeline number' + ":$BUILD_NUMBER" + ' is failed !! Please check the logs file below !!', subject: 'Jenkins Pipeline Failed', to: 'heni.m.nechi@gmail.com')
                        throw e
                    }
                }
            }
        }

        stage('Cleaning up') {
            steps {
                script {
                    try {
                        sh "docker rmi $registry:$BUILD_NUMBER"
                    } catch (Exception e) {
                        emailext(attachLog: true, body: 'The pipeline number' + ":$BUILD_NUMBER" + ' is failed !! Please check the logs file below !!', subject: 'Jenkins Pipeline Failed', to: 'heni.m.nechi@gmail.com')
                        throw e
                    }
                }
            }
        }

        stage('Docker Compose') {
            steps {
                script {
                    try {
                        sh "docker compose up -d"
                    } catch (Exception e) {
                        emailext(attachLog: true, body: 'The pipeline number' + ":$BUILD_NUMBER" + ' is failed !! Please check the logs file below !!', subject: 'Jenkins Pipeline Failed', to: 'heni.m.nechi@gmail.com')
                        throw e
                    }
                }
            }
        }
    }
}
