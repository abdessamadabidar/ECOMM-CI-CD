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

//        stage('Backend auto versioning') {
//            steps {
//                // Increment backend release version
//                // pom.xml ~ xml
//
//               script {
//
//                   // Read current version from pom.xml
//                   def pom = readMavenPom file: 'pom.xml'
//                   def version = pom.version
//
//                   // Split version: "0.0.1-SNAPSHOT"
//                   def parts = version.tokenize('-')   // ['0.0.1', 'SNAPSHOT']
//                   def versionNums = parts[0].tokenize('.')  // ['0', '0', '1']
//
//                   // Extract and increment patch
//                   def major = versionNums[0]
//                   def minor = versionNums[1]
//                   def patch = versionNums[2] as Integer
//                   patch = patch + 1
//
//                   // Handle suffix if exists
//                   def suffix = (parts.size() > 1) ? parts[1] : null
//
//                   // Construct new version string
//                   def newVersion = suffix ? "${major}.${minor}.${patch}-${suffix}".toString() : "${major}.${minor}.${patch}".toString()
//
//
//                   // Update version in pom object
//                   pom.version = newVersion
//                   env.BACK_IMAGE_VERSION = newVersion
//
//
//                   // Write back the updated pom.xml
//                   writeMavenPom model: pom, file: 'pom.xml'
//
//               }
//
//            }
//        }


        stage('Frontend auto versioning') {
            steps {
                // Increment front release version
                // package.json ~ json

                script {
                    def packageJson = readJSON file: 'client/package.json'
                    def version = packageJson.version

                    def versionNums = version.tokenize('.')
                    def maj = versionNums[0]
                    def min = versionNums[1]
                    def pat = versionNums[2] as Integer
                    pat = pat + 1

                    def newVersion = "${maj}.${min}.${pat}".toString()
                    packageJson.version = newVersion
                    env.FRONT_IMAGE_VERSION = newVersion

                    echo "${newVersion}"
                }
            }
        }


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
