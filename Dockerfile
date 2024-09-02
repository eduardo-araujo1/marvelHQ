FROM maven:3.9.6-amazoncorretto-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build ./app/target/*.jar ./apihq.jar
ENTRYPOINT java -jar apihq.jar
EXPOSE 8080