# Ads Application (Платформа для перепродажи вещей)

Бэкенд-часть приложения для платформы по перепродаже вещей (аналог Avito).

## Стек технологий

- **Java 17**
- **Spring Boot 2.7.15** (Web, Data JPA, Security, Validation)
- **PostgreSQL** (база данных)
- **Liquibase** (миграции базы данных)
- **MapStruct** (маппинг Entity <-> DTO)
- **Lombok** (утилиты для кода)
- **Springdoc OpenAPI** (документация Swagger)
- **Maven**

## Запуск проекта

1. Установите и запустите PostgreSQL.
2. Создайте базу данных `ads_db`.
3. В настройках запуска (Environment variables) задайте переменную `DB_PASSWORD` с вашим паролем от PostgreSQL.
4. Запустите приложение через `HomeworkApplication`.
5. Документация Swagger доступна по адресу: `http://localhost:8080/swagger-ui.html`