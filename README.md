# Система управления банковскими картами

## Локальный запуск через консоль

Для сборки и запуска приложения необходим **JDK 25**

Запускаем Postgres через Docker

```
docker compose up -d
```

Устанавливаем переменную с ключом для подписи JWT (ключ в base64)

```
export JWT_SIGNING_KEY="YjRua2M0cmQ1LWpzMG4tdzNiLXQwazNuLWQzZjQxdC1zMWduMW5nLWszeQ=="
```

Собираем и запускаем приложение

```
mvn clean spring-boot:run
```

Доступные эндпоинты: http://localhost:8888/swagger-ui.html
