pipeline {
    agent any
    environment {
        SONARQUBE_URL = 'http://192.168.100.10:9000'
        SONARQUBE_USERNAME = 'admin'
        SONARQUBE_PASSWORD = 'Facebook1'
        BRANCH_NAME = 'FournisseurTest'
        registry = "mohamedridhaa/achat-back"
        registryCredential = 'DockerHub'
        dockerImage = ''
        INFISICAL_TOKEN = credentials('infisical-service-token')
    }
    stages {
        stage("clone repo") {
            steps {
                script {
                    try {
                        git url: 'https://github.com/Team-As-A-Service/Achat.git',
                            branch: 'FournisseurTest',
                            credentialsId: 'github'
                    } catch (Exception e) {
                        emailext (attachLog: true, body: 'The pipeline number'+":$BUILD_NUMBER"+' is failed !! Please check the logs file bellow !!', subject: 'Jenkins Pipeline Failed', to: 'metjaku@gmail.com')
                        throw e
                    }
                }
            }
        }/*
        stage('Gitleaks - Secret Management') {
            steps {
                script {
                    try {
                        def output = sh(script: 'docker run -v /var/lib/jenkins/workspace/Achats:/path zricethezav/gitleaks:latest detect --source="/path" --verbose', returnStdout: true).trim()
                        println(output)
                    } catch (Exception e) {
                        emailext (attachLog: true, body: 'The pipeline number'+":$BUILD_NUMBER"+' is failed !! Gitleaks detected potential secrets in the repository. Please review and remove them before proceeding.', subject: 'Jenkins Pipeline Failed', to: 'metjaku@gmail.com')
                        throw e
                    }
                }
            }
        }*/
         stage('Infisical Secret Managment') {
            steps {
                script {
                    try {
                       sh("docker run -e INFISICAL_TOKEN=${INFISICAL_TOKEN} --rm infisical infisical secrets --env=dev --path=/")
                    } catch (Exception e) {
                        emailext (attachLog: true, body: 'The pipeline number'+":$BUILD_NUMBER"+' is failed !! Gitleaks detected potential secrets in the repository. Please review and remove them before proceeding.', subject: 'Jenkins Pipeline Failed', to: 'metjaku@gmail.com')
                        throw e
                    }
                }
            }
        }
        stage('MVN CLI') {
            steps {
                script {
                    try {
                        sh 'mvn clean'
                        sh 'mvn compile'
                    } catch (Exception e) {
                        emailext (attachLog: true, body: 'The pipeline number'+":$BUILD_NUMBER"+' is failed !! Please check the logs file bellow !!', subject: 'Jenkins Pipeline Failed', to: 'metjaku@gmail.com')
                        throw e
                    }
                }
            }
        }
        stage('Unit Tests') {
                    steps {
                        script {
                            try {
                                sh 'mvn test'
                            } catch (Exception e) {
                                emailext (attachLog: true, body: 'The pipeline number'+":$BUILD_NUMBER"+' is failed !! Please check the logs file bellow !!', subject: 'Jenkins Pipeline Failed', to: 'metjaku@gmail.com')
                                throw e
                            }
                        }
                    }
                }
        stage('SonarQube') {

            steps {
                script {
                    try {
                        sh "mvn sonar:sonar -Dsonar.host.url=${SONARQUBE_URL} -Dsonar.login=${SONARQUBE_USERNAME} -Dsonar.password=${SONARQUBE_PASSWORD} "
                    } catch (Exception e) {
                        emailext (attachLog: true, body: 'The pipeline number'+":$BUILD_NUMBER"+' is failed !! Please check the logs file bellow !!', subject: 'Jenkins Pipeline Failed', to: 'metjaku@gmail.com')
                        throw e
                    }
                }
            }
        }

        stage('Nexus deployment') {
            steps {
                script {
                    try {
                        sh 'mvn clean deploy -DskipTests'
                    } catch (Exception e) {
                        emailext (attachLog: true, body: 'The pipeline number'+":$BUILD_NUMBER"+' is failed !! Please check the logs file bellow !!', subject: 'Jenkins Pipeline Failed', to: 'metjaku@gmail.com')
                        throw e
                    }
                }
            }
        }
        stage('Building image') {
            steps {
                script {
                    try {
                        dockerImage = docker.build registry + ":tagname"
                    } catch (Exception e) {
                        emailext (attachLog: true, body: 'The pipeline number'+":$BUILD_NUMBER"+' is failed !! Please check the logs file bellow !!', subject: 'Jenkins Pipeline Failed', to: 'metjaku@gmail.com')
                        throw e
                    }
                }
            }
        }
        stage('Deploy image on Docker') {
            steps {
                script {
                    try {
                        docker.withRegistry( '', registryCredential ) {
                            dockerImage.push()
                        }
                    } catch (Exception e) {
                        emailext (attachLog: true, body: 'The pipeline number'+":$BUILD_NUMBER"+' is failed !! Please check the logs file bellow !!', subject: 'Jenkins Pipeline Failed', to: 'metjaku@gmail.com')
                        throw e
                    }
                }
            }
        }
        /*stage('Docker Compose') {
            steps {
                script {
                    try {
                        sh "docker compose up -d"
                    } catch (Exception e) {
                        emailext (attachLog: true, body: 'The pipeline number'+":$BUILD_NUMBER"+' is failed !! Please check the logs file bellow !!', subject: 'Jenkins Pipeline Failed', to: 'metjaku@gmail.com')
                        throw e
                    }
                }
            }
        }
        stage('Terraform Deployment') {
        steps {
            script {
                try {
                        sh 'terraform init'
                        sh 'terraform plan'
                        sh 'terraform apply --auto-approve'
                } catch (Exception e) {
                    emailext (attachLog: true, body: 'The pipeline number'+":$BUILD_NUMBER"+' is failed !! Please check the logs file bellow !!', subject: 'Jenkins Pipeline Failed', to: 'metjaku@gmail.com')
                    throw e
                }
            }
        }
    }*/

    }
}
