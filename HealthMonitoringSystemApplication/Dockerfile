# Build application using Maven
FROM maven:3.9.5-eclipse-temurin-21 AS build

ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

# Work directory in the container
WORKDIR /app

# Copy the Maven project files (pom.xml and source code)
COPY pom.xml .
COPY src ./src

# Copy settings.xml template and replace placeholders
COPY src/main/resources/settings.xml.template /root/.m2/settings.xml.template

RUN sed "s/\${GITHUB_USERNAME}/$GITHUB_USERNAME/g; s/\${GITHUB_TOKEN}/$GITHUB_TOKEN/g" /root/.m2/settings.xml.template > /root/.m2/settings.xml

RUN ls -l /root/.m2/settings.xml && cat /root/.m2/settings.xml

# Build the application and package it as a JAR file
RUN mvn clean package -DskipTests

# Create the final image with the JAR and the model
FROM eclipse-temurin:21-jre

# Set work directory as /app
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/HealthMonitoringSystemApplication-0.0.1-SNAPSHOT.jar app.jar

# Diagnostic step to ensure that the model is correctly copied (optional)
RUN ls -R /app

# Set Java environment variable
ENV JAVA_OPTS=""

# Set database-related environment variables
ENV DATABASE_URL=""
ENV DATABASE_USERNAME=""
ENV DATABASE_PASSWORD=""

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# Expose the application port
EXPOSE 8080
