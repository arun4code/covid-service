FROM maven:3.6.3-jdk-8-slim as build

WORKDIR /home/app
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:8-jdk-alpine
EXPOSE 8081
COPY --from=build /home/app/target/covid-service-0.0.1-SNAPSHOT.jar covid-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","covid-service-0.0.1-SNAPSHOT.jar"]



# For Java 8, try this
# FROM openjdk:8-jdk-alpine

# For Java 11, try this
#FROM adoptopenjdk/openjdk11:alpine-jre

# Refer to Maven build -> finalName
#ARG JAR_FILE=target/covid-service-0.0.1-SNAPSHOT.jar 

# cd /opt/app
#WORKDIR /opt/app

# cp target/covid-service-0.0.1-SNAPSHOT.jar /opt/app/app.jar

#COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
#ENTRYPOINT ["java","-jar","app.jar"]

## sudo docker run -p 8080:8080 -t docker-spring-boot:1.0
## sudo docker run -p 80:8080 -t docker-spring-boot:1.0
## sudo docker run -p 443:8443 -t docker-spring-boot:1.0