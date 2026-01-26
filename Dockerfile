# Этап 1: Сборка
# Обновляем версию Gradle до 8.x (например, 8.5)
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src ./src
# Используем встроенный gradle или установленный в образе
RUN gradle clean bootJar -x test

# Этап 2: Запуск
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Ограничение памяти для Free Tier (512MB)
ENTRYPOINT ["java", "-Xmx300m", "-Xss512k", "-XX:MaxMetaspaceSize=128m", "-jar", "app.jar"]