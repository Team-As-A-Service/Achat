pipeline {
    agent any
    stages {
        stage('Clonage') {
            steps {
                script {
                    git(branch: 'main', credentialsId: 'ssh', url: 'https://ghp_5Uuc6I1nwqdVaFtYyIDY6otLMTd77E1Y@github.com/Team-As-A-Service/Achat.git')
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
        }
    }

    }