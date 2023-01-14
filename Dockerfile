FROM maven:3.8.6-amazoncorretto-8 AS maven_build
WORKDIR /annual_app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM openjdk:8
WORKDIR /annual_app
WORKDIR /annual_app
COPY --from=maven_build /annual_app/target/Annual-Leave-Tracking-App-1.0-SNAPSHOT.jar my_app.jar
ENTRYPOINT ["java","-jar","my_app.jar"]
EXPOSE 8080