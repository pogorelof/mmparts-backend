# Этап 1: Сборка
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Копируем файлы воркера (gradlew) и настройки
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

# Даем права на выполнение скрипта (важно для Linux/Render)
RUN chmod +x gradlew

# Скачиваем зависимости (кешируем этот слой)
RUN ./gradlew dependencies --no-daemon

# Копируем исходники и собираем
COPY src ./src
RUN ./gradlew clean bootJar -x test --no-daemon

# Этап 2: Запуск
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080

# Лимиты памяти для Render (Free Tier)
ENTRYPOINT ["java", "-Xmx320m", "-Xss512k", "-jar", "app.jar"]