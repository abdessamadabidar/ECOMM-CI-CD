#!user/bin/env groovy

pipeline {
    agent any
    environment {
        registry = 'abdessamadabidar/ecomm-ci-cd'
        backImage = 'ecommback'
        frontImage = 'ecommfront'
    }

    stages {
        stage('Back-end') {
            steps {
                // Build Docker Image
                docker.build registry + backImage + '0.0.1'
            }
        }


    }
}
