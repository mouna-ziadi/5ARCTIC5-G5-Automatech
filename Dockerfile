FROM openjdk:11

# Set the working directory
WORKDIR /app

COPY target/*.jar /5ARCTIC5-G5-Automatech.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "/5ARCTIC5-G5-Automatech.jar"]
