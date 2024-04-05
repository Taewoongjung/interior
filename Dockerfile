FROM openjdk:17-alpine

EXPOSE 7070

ADD ./build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "/app.jar"]