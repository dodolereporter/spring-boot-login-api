# Login API with Spring-Boot

## Requirements

- Java 21
- PostgreSQL Server


## Setup

#### Configutration

Edit application.properties with your database credentials and jwt secret

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/<YOUR_DB_NAME>
spring.datasource.username=<YOUR_DB_USER>
spring.datasource.password=<YOUR_DB_PASSWORD>
spring.datasource.driver-class-name=org.postgresql.Driver
# hibernate properties
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto=update
app.jwt-secret=<JWT_SECRET>
app-jwt-expiration-milliseconds=604800000
```

#### Change default port

Add this line to application.properties

```properties
server.port=<PORT>
```

### API Usage

#### Register

```http
POST /api/v1/auth/register
```

| Parameter  | Type     | Description                 |
|:-----------|:---------|:----------------------------|
| `name`     | `string` | **Required**. Your name     |
| `username` | `string` | **Required**. Your username |
| `email`    | `string` | **Required**. Your email    |
| `password` | `string` | **Required**. Your password |

#### Login

```http
POST /api/v1/auth/login
```

| Parameter         | Type     | Description                          |
|:------------------|:---------|:-------------------------------------|
| `usernameOrEmail` | `string` | **Required**. Your username or email |
| `password`        | `string` | **Required**. Your password          |

## Footer

### Author

[@dodolereporter](https://github.com/dodolereporter)

### License

[MIT](https://choosealicense.com/licenses/mit/)
