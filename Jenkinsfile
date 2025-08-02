#!user/bin/env groovy

pipeline {
    agent any
    environment {
        registry = 'abdessamadabidar/ecomm-ci-cd'
        backImage = ''
        frontImage = 'ecommfront'
    }

    stages {

        stage('checkout') {
            steps {
                checkout scm
            }
        }

        stage('Back-end') {
            steps {
                // Build Docker Image
               sh backImage = docker.build registry + '/ecommback:0.0.1'
            }
        }


    }
}
