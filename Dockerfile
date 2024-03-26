FROM openjdk:17-jdk

EXPOSE 7070

ADD ./build/libs/ /app/
WORKDIR /app

ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "/app.jar"]