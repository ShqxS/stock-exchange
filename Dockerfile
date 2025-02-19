FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY gradlew build.gradle settings.gradle ./
COPY gradle gradle

RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew build -x test --no-daemon

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "-web -webAllowOthers -tcp -tcpAllowOthers -browser"]
