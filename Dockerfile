FROM openjdk:17-jdk-slim-buster as BUILD_IMAGE

ENV WORK_DIR=/usr/app/

# app 작업 디렉토리 설정
WORKDIR $WORK_DIR

# gradle 실행을 위한 필수 디렉토리 준비
COPY gradlew $WORK_DIR
COPY build.gradle $WORK_DIR
COPY settings.gradle $WORK_DIR
COPY gradle $WORK_DIR/gradle
RUN chmod +x ./gradlew
RUN ./gradlew build

COPY src src

# jar 파일 build
RUN ./gradlew bootjar

FROM openjdk:17-jdk-slim-buster

ENV WORK_DIR=/usr/app/

WORKDIR $WORK_DIR

COPY --from=BUILD_IMAGE $WORK_DIR/build/libs/*.jar demo-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", \
"-jar", \
"-Dspring.profiles.active=${PROFILE_OPTION}", \
"-Dspring.datasource.url=${SPRING_DATASOURCE_URL}", \
"demo-0.0.1-SNAPSHOT.jar"]