FROM gradle:8.5-jdk17-alpine as builder
WORKDIR /build

# 그래들 파일이 변경되었을 때만 새롭게 의존패키지 다운로드 받게함.
COPY build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

# 빌더 이미지에서 애플리케이션 빌드
COPY . /build
RUN gradle build -x test --parallel

# APP
FROM openjdk:17
WORKDIR /app

# 빌더 이미지에서 jar 파일만 복사
COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./app.jar

# Docker Compose 파일에서 MySQL 데이터베이스 설정 추가
# MySQL 데이터베이스 컨테이너 정의
# 다음과 같이 Docker Compose 파일에서 정의된 MySQL 설정을 Dockerfile에 추가합니다.
RUN apt-get update && apt-get install -y mysql-client
RUN echo "version: '3.7'" >> docker-compose.yml
RUN echo "services:" >> docker-compose.yml
RUN echo "  db:" >> docker-compose.yml
RUN echo "    image: mysql:8.0.33" >> docker-compose.yml
RUN echo "    platform: linux/amd64" >> docker-compose.yml
RUN echo "    environment:" >> docker-compose.yml
RUN echo "      MYSQL_DATABASE: interior" >> docker-compose.yml
RUN echo "      MYSQL_ROOT_PASSWORD: 1234567" >> docker-compose.yml
RUN echo "    ports:" >> docker-compose.yml
RUN echo "      - '3309:3306'" >> docker-compose.yml
RUN echo "    restart: unless-stopped" >> docker-compose.yml

EXPOSE 8080

# root 대신 nobody 권한으로 실행
USER nobody
ENTRYPOINT [                                                \
    "java",                                                 \
    "-jar",                                                 \
    "-Djava.security.egd=file:/dev/./urandom",              \
    "-Dsun.net.inetaddr.ttl=0",                             \
    "app.jar"              \
]