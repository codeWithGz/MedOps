FROM maven:3.9.8-eclipse-temurin-17 AS build

WORKDIR /MedOps

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk

WORKDIR /MedOps

COPY --from=build /MedOps/target/MedOps-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
