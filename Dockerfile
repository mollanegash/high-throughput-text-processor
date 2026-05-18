# Stage 1: Build the Spring Boot application using Maven
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the built executable JAR inside a lean JRE environment
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Configure memory limits to strictly fit inside Render's 512MB Free Tier
ENTRYPOINT ["java", "-Xmx350m", "-Xms250m", "-jar", "app.jar"]
