# Этап 1: Сборка
FROM gradle:7.6-jdk17 AS build
WORKDIR /app
# Копируем файлы конфигурации Gradle для кэширования зависимостей
COPY build.gradle settings.gradle ./
# Копируем исходный код
COPY src ./src
# Собираем JAR-файл без запуска тестов (для экономии времени и памяти)
RUN gradle clean bootJar -x test

# Этап 2: Запуск
FROM openjdk:17-jdk-slim
WORKDIR /app
# Копируем только готовый JAR-файл из этапа сборки
COPY --from=build /app/build/libs/*.jar app.jar

# Настройка порта (Render использует переменную PORT)
EXPOSE 8080

# Оптимизация памяти JVM для 512 МБ лимита Render
# -Xmx300m ограничивает кучу, чтобы оставить место для самой ОС и стека
ENTRYPOINT ["java", "-Xmx300m", "-Xss512k", "-XX:MaxMetaspaceSize=128m", "-jar", "app.jar"]