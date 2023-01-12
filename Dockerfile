FROM openjdk:8
EXPOSE 8080
ADD target/Annual-Leave-Tracking-App-1.0-SNAPSHOT.jar my_app.jar
ENTRYPOINT ["java", "-jar", "my_app.jar"]

# docker build -t user_leave_tracking_app -f Dockerfile .
# docker ps
# docker images -a
# docker network ls
# docker run --name user_leave_tracking_app -p 8080:8080 user_leave_tracking_app
# docker image rm user_leave_tracking_app
# docker rmi $(docker images -a -q)