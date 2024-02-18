FROM maven:3.8.5-openjdk-17 AS build
COPY . .
WORKDIR /sikpi
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
WORKDIR /sikpi
COPY --from=build /target/sikpi-0.0.1-SNAPSHOT.jar sikpi.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","sikpi.jar"]