# Используем базовый образ с OpenJDK 17
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем JAR-файл, собранный Maven, в контейнер
COPY target/*.jar app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]

# Указываем порт, который будет использоваться приложением
EXPOSE 8761