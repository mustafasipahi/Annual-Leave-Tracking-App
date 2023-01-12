FROM openjdk:8
EXPOSE 8080
ADD target/Annual-Leave-Tracking-App-1.0-SNAPSHOT.jar my_app.jar
ENTRYPOINT ["java", "-jar", "my_app.jar"]