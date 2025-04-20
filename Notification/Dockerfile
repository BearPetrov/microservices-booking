# Указываем базовый образ
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файл сборки (JAR) в контейнер
COPY target/Notification-0.0.1-SNAPSHOT.jar app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]

# Указываем порт, который будет использоваться
EXPOSE 8082