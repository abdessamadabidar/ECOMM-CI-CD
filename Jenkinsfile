#!user/bin/env groovy

pipeline {
    agent any
    environment {
        registry = 'abdessamadabidar/ecomm-ci-cd'
        BACK_IMAGE_VERSION = ''
        FRONT_IMAGE_VERSION = ''


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

               script {
                   echo 'Before increment'
                   def version = readMavenPom().getVersion() // 0.0.1-SNAPSHOT
                   echo "${version}"


                   def parts = version.tokenize('-')  // ['0.0.1', 'SNAPSHOT']
                   def versionNums = parts[0].tokenize('.') // ['0', '0', '1']

                   def major = versionNums[0]
                   def minor = versionNums[1]
                   def patch = versionNums[2] as Integer
                   patch = patch + 1
                   def suffix = (parts.size() > 1) ? parts[1] : null

                   BACK_IMAGE_VERSION = major + '.' + minor + '.' + patch + '-' + suffix

                   echo 'After increment'
                   echo "${BACK_IMAGE_VERSION}"
               }

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
