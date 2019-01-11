# AdventureWorks Product Review App
**Online Product Review Service:**
 - accepts product reviews
 - scans the review for any inappropriate language
 - posts the review live once approved or archives if not approved and notifies the reviewer via email (simulated) once
 the review status has been finalized

## Technology stack
 - Maven
 - Spring Boot 2.1.1
 - Java 8
 - JPA/Hibernate
 - PostgreSQL
 - Spring Rest
 - Spring Security
 - Spring Test
 - Redis Pub/Sub 
 - Log4j2
 - Swagger 2
 
## Docker
```
# From project directory run the following commands

# Build the project, skip tests
./mvnw clean install -DskipTests

# Create and start all the services
docker-compose up
```

## Local Environment
### Prerequisites
 - Install PostgreSQL (port: 5432)
 - Install Redis (port: 6379)
 
### Database Setup
```
# Open command line from project directory and Login to PostgreSQL server
psql -U postgres -h localhost

# Create User
CREATE USER adv_user WITH password 'adv_pass';

# Create Database
CREATE DATABASE adv_works OWNER adv_user;

# Grant privileges
GRANT ALL PRIVILEGES ON DATABASE adv_works TO adv_user;

# Logout from PostgreSQL server
\q

# Create Database Data
psql -U adv_user -d adv_works -a -f postgres-db/init.sql
```

### Build and Run
Build the project:
```
# From project directory run
./mvnw clean install
 ```

Run the app:
```
# Run the application (Maven)
./mvnw spring-boot:run

# Run the application (Java)
java -jar target/adventureworks-1.0.jar
```

## Paths
Swagger UI endpoint: http://localhost:8080/swagger-ui.html
Swagger docs endpoint: http://localhost:8080/v2/api-docs
Logs available under /logs folder