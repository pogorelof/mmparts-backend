# Этап 1: Сборка (Build)
# Используем Gradle 8.12, так как он поддерживает Spring Boot 4.0.0
FROM gradle:8.12-jdk17 AS build
WORKDIR /app

# Копируем только файлы сборки для кэширования зависимостей
COPY build.gradle settings.gradle ./
# Копируем исходный код
COPY src ./src

# Сборка проекта. Флаг --no-daemon экономит память в контейнере
RUN gradle clean bootJar -x test --no-daemon

# Этап 2: Запуск (Runtime)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Копируем JAR. Используем маску, чтобы не зависеть от версии в названии
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

# Порт для Render
EXPOSE 8080

# Настройки JVM для стабильной работы на бесплатном тарифе (512MB RAM)
# -Xmx320m оставляет запас для метаспейса и стеков потоков
ENTRYPOINT ["java", "-Xmx320m", "-Xss512k", "-XX:MaxMetaspaceSize=128m", "-jar", "app.jar"]