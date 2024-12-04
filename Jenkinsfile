pipeline {
    agent {
        label 'vm01'
    }
    tools {
        maven 'apache-maven-3.9.8'
    }
    environment {
		PROJECT_NAME = "scheduler"
        APP_NAME = "scheduler.jar"
        APP_HOME = "/home/angela/service/scheduler"
        GIT_CREDENTIALS_ID = '5486ab12-f4dc-43a7-9d7a-384505b067f1'
        DOCKER_IMAGE = 'local-scheduler-image:latest'
        JAVA_HOME = '/home/angela/graalvm-jdk-22'
        PATH = "${JAVA_HOME}/bin:${PATH}"
    }
    stages {
        stage('Checkout from GitHub') {
            steps {
                git credentialsId: "${GIT_CREDENTIALS_ID}", url: 'https://github.com/youngitn/topkey_scheduler.git', branch: 'master'
            }
        }
        stage('Maven Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Prepare Docker Image') {
            steps {
                script {
                    // 复制生成的 JAR 文件到 Dockerfile 所在目录
                    sh 'cp target/*.jar ${APP_HOME}/${APP_NAME}'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    // 进入工作目录
                    dir("${APP_HOME}") {
                        // 构建 Docker 镜像
                        sh 'docker build -t ${DOCKER_IMAGE} .'
                    }
                }
            }
        }
         stage('Deploy Docker Container') {
            steps {
                script {
                    
                    dir("${APP_HOME}") {
                        // 停止并删除现有的容器（如果存在）
                        sh 'docker compose down'
                        
                        sh 'docker compose up -d'
                    }
                }
            }
            
    }
}
}
