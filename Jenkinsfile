#!user/bin/env groovy

pipeline {
    agent any
    environment {
        registry = 'abdessamadabidar/ecomm-ci-cd'
        BACK_IMAGE_VERSION = ''
        FRONT_IMAGE_VERSION = ''

        IMAGE = readMavenPom().getArtifactId()
        VERSION = readMavenPom().getVersion()
    }

    stages {

        stage('checkout') {
            steps {
                checkout scm
            }
        }

        stage('Backend auto versioning') {
            steps {
                // Increment backend release version
                // pom.xml ~ xml
                echo ${IMAGE}
                echo ${VERSION}


            }
        }


//        stage('Frontend auto versioning') {
//            steps {
//                // Increment front release version
//                // package.json ~ json
//            }
//        }
//
//        stage('Back-end') {
//            steps {
//                // Build Docker Image
//               sh "docker build -t ${registry}/ecommback:latest ."
//            }
//        }
//
//
//        stage('Front-end') {
//            steps {
//                sh "docker build -t ${registry}/ecommfront:latest client/"
//            }
//        }


    }
}
