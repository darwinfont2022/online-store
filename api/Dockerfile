# stage 1 - build
FROM maven:3.8.5-jdk-11-slim AS build

WORKDIR /build
COPY . ./
RUN mvn clean package

# stage 2 — build production image
FROM openjdk:11

ENV STORE_HOME ./
WORKDIR $STORE_HOME
COPY --from=build /build/target/store-0.0.1-SNAPSHOT.jar $STORE_HOME/app.jar
ENTRYPOINT java -jar $STORE_HOME/app.jar