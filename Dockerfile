FROM openjdk:11
EXPOSE 8080
COPY target/5ARCTIC5-G5-Automatech.jar 5ARCTIC5-G5-Automatech.jar
ENTRYPOINT ["java","-jar","/5ARCTIC5-G5-Automatech.jar"]