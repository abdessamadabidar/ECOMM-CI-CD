#!user/bin/env groovy

pipeline {
    agent any
    environment {
        registry = 'abdessamadabidar/ecomm-ci-cd'
    }

    stages {

        stage('checkout') {
            steps {
                checkout scm
            }
        }

//        stage('Back-end') {
//            steps {
//                // Build Docker Image
//               sh "docker build -t ${registry}/ecommback:latest ."
//            }
//        }
//

        stage('Front-end') {
            steps {
                sh "docker build -t ${registry}/ecommfront:latest client/"
            }
        }


    }
}
