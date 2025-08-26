#!user/bin/env groovy

pipeline {
    agent any
    tools {
        maven "Maven"
    }

    environment {
        DOCKER_HUB_CREDS = credentials('docker-hub-creds')
        AWS_ECR_CREDS = credentials('aws-ecr-credentials')
        AWS_ECR_SERVER = '314146337686.dkr.ecr.us-east-1.amazonaws.com'
        AWS_ECR_BACK_REPO = "${AWS_ECR_SERVER}/ecomm.ci.cd.back"
        AWS_ECR_FRONT_REPO = "${AWS_ECR_SERVER}/ecomm.ci.cd.front"
        ANSIBLE_HOST_KEY_CHECKING = 'False'
    }

    stages {

         stage('checkout') {
             steps {
                 git branch: 'master', credentialsId: 'github-creds', url: 'https://github.com/abdessamadabidar/ECOMM-CI-CD.git'
             }
         }


         stage('Increment Backend version') {
             steps {
                 script {
                     echo "Incrementing backend version"
                     sh """
                         mvn build-helper:parse-version versions:set \
                             -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.nextMinorVersion}.\\\${parsedVersion.incrementalVersion} \
                             versions:commit
                     """
                     def pom = readMavenPom file: 'pom.xml'
                     def version = pom.version
                     env.BACK_IMAGE_TAG = "$version-$BUILD_NUMBER"


                 }
             }
         }

         stage('Increment Frontend version') {
             steps {
                 script {
                     echo "Incrementing frontend version"
                     sh 'cd client && npm version patch'

                     def packageJson = readJSON file: 'client/package.json'
                     def packageJsonVersion = packageJson.version

                     env.FRONT_IMAGE_TAG = "$packageJsonVersion-$BUILD_NUMBER"
                 }
             }
         }

         stage('Build') {
             steps {
                 //I'm skipping running tests temporary
                 sh 'mvn clean package -DskipTests'
             }

              post {
                 success {
                     echo 'Maven build completed successfully'
                     archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                 }
             }
         }

         stage('Code Quality') {
             environment {
                 scannerHome = tool 'SonarQube'
             }

             steps {
                 withSonarQubeEnv('sonar') {
                     sh """
                         ${scannerHome}/bin/sonar-scanner \
                         -Dsonar.projectKey=ecomm-ci-cd \
                         -Dsonar.projectName=ecomm-ci-cd \
                         -Dsonar.sources=src/ \
                         -Dsonar.java.binaries=target/classes 
                     """
                 }
             }
         }


         stage('Back-end') {
             steps {
               sh "docker build -t ${AWS_ECR_BACK_REPO}:${BACK_IMAGE_TAG} ."
               sh 'echo $AWS_ECR_CREDS_PSW | docker login -u $AWS_ECR_CREDS_USR --password-stdin $AWS_ECR_SERVER'
               sh "docker push ${AWS_ECR_BACK_REPO}:${BACK_IMAGE_TAG}"
             }
         }

         stage('Front-end') {
             steps {
                 sh "docker build -t ${AWS_ECR_FRONT_REPO}:${FRONT_IMAGE_TAG} client/"
                 sh 'echo $AWS_ECR_CREDS_PSW | docker login -u $AWS_ECR_CREDS_USR --password-stdin $AWS_ECR_SERVER'
                 sh "docker push ${AWS_ECR_FRONT_REPO}:${FRONT_IMAGE_TAG}"
             }
         }


         stage('Commit version updates to remote Git Repo') {
             steps {
                 script {
                     withCredentials([string(credentialsId: 'jenkins-github-access-token', passwordVariable: 'PASS', variable: 'ACCESS_TOKEN')]) {
                         sh 'git config user.email jenkins@exemple.com'
                         sh 'git config user.name "jenkins"'

                         sh "git remote set-url origin https://${USER}:${ACCESS_TOKEN}@github.com/abdessamadabidar/ECOMM-CI-CD.git"
                         sh 'git add .'
                         sh 'git commit -m "ci: version bump"'
                         sh 'git push origin HEAD:master'
                     }
                 }
             }
         }

        stage('Checkout IaC') {
            steps {
                git branch: 'master', credentialsId: 'github-creds', url: 'https://github.com/abdessamadabidar/IaC.git'
            }
        }

        stage('Terraform: Provision Amazon EKS cluster') {
            steps {
                dir('ecomm-ci-cd/terraform') {
                    sh 'terraform init'
                    sh 'terraform apply -auto-approve'
                }
            }
        }

        stage('Ansible: Run Setup ArgoCD playbook') {
            steps {
                dir('ecomm-ci-cd/ansible') {
                    sh 'ansible-playbook -i hosts setup-argocd-playbook.yaml'
                }
            }
        }

        stage('Ansible: Run Deploy ArgoCD playbook') {
            steps {
                dir('ecomm-ci-cd/ansible') {
                    sh 'ansible-playbook -i hosts deploy-argocd-playbook.yaml'
                }
            }
        }

    }



    post {
        always {
            junit(testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true)
            cleanWs()
        }
    }
}
