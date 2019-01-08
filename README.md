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
 - Redis Pub/Sub
 - JUnit 4
 - Log4j2
 
## Prerequisites
 - Install PostgreSQL (port: 5432)
 - Install Redis (port: 6379)
 
## Database Setup
```
# Open command line from project directory and Login to PostgreSQL server
psql -U postgres -h localhost

# Create User
CREATE USER adv_user WITH password 'adv_pass';

# Create Database
CREATE DATABASE adv_works OWNER adv_user;

# Change owner of schema "public" and grant access
GRANT ALL PRIVILEGES ON DATABASE adv_works TO adv_user;
ALTER SCHEMA public OWNER TO adv_user;

# Logout from PostgreSQL server
\q

# Create Database Data
psql -U adv_user -d adv_works -a -f docker-entrypoint-initdb.d/postgres-db.sql
```

## Build and Run
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