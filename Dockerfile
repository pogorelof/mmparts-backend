# Этап 1: Сборка
# Используем самую свежую версию Gradle (напр. 8.14+ или последнюю доступную)
FROM gradle:jdk21 AS build
WORKDIR /app

# Копируем файлы проекта
COPY build.gradle settings.gradle ./
COPY src ./src

# Собираем JAR
RUN gradle clean bootJar -x test

# Этап 2: Запуск
# Spring Boot 4 требует Java 17+, но лучше всего работает на Java 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Копируем результат сборки
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Оптимизация памяти для бесплатного тарифа (512MB RAM)
ENTRYPOINT ["java", "-Xmx350m", "-Xss512k", "-XX:MaxMetaspaceSize=128m", "-jar", "app.jar"]