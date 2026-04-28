FROM jenkins/jenkins:lts

USER root

RUN apt-get update && \
    apt-get install -y maven docker.io git docker-compose && \
    apt-get clean

RUN usermod -aG docker jenkins

USER jenkins