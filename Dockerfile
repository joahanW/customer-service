#FROM eclipse-temurin:17-jdk
FROM public.ecr.aws/docker/library/eclipse-temurin:17-jdk-alpine

LABEL author="johan" \
      company="Metrodata" \
      website="https://www.johanwork.com"

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} customerservice.jar

ENTRYPOINT ["java", "-jar", "/customerservice.jar"]

EXPOSE 8080