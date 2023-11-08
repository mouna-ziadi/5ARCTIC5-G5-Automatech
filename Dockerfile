FROM openjdk:11
WORKDIR /app
EXPOSE 8080
COPY /2.7.13-2.7.13.jar /5ARCTIC5-G5-Automatech.jar
ENTRYPOINT ["java", "-jar", "/5ARCTIC5-G5-Automatech.jar"]
