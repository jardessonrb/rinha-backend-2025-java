# Dockerfile
FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/rinha2025-0.0.1.jar app.jar
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-XX:+UseG1GC", "-jar", "app.jar"]
