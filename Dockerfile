FROM openjdk:17-jdk-slim-buster as builder

WORKDIR app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootjar

ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} application.jar
RUN java -Dspring.profiles.active=prod -jar ${JAR_FILE} extract


# 런타임
FROM openjdk:17-jdk-slim-buster

RUN addgroup --system --gid 1001 worker
RUN adduser --system --uid 1001 worker

WORKDIR app
ENV port 8080
ENV JAVA_OPTS="-Dspring.profiles.active=prod"
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./

USER worker

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]