# Property Ticket Microservice

A microservice-based property management system built using Spring Boot.

The application consists of two services:

* **Property Service**

  * Manage Buildings
  * Manage Tenants
  * Building Summary (Native SQL Query)

* **Ticket Service**

  * Manage Tickets
  * Validate Tenant and Building data through OpenFeign

## Technologies

* Java 17
* Spring Boot 3
* Spring Data JPA
* OpenFeign
* H2 Database
* Docker
* Docker Compose
* Java Stream API
* Native SQL Query

## Project Structure

property_service

ticket_service

docker-compose.yml

## How To Run

Build and start all services:

```bash
docker compose up --build
```

## Service URLs

Property Service:

http://localhost:8080

Ticket Service:

http://localhost:8081

## Sample Endpoints

### Property Service

GET /tenant

GET /tenant/{id}

POST /tenant

GET /building

GET /building/{id}

POST /building

GET /building/summary

### Ticket Service

GET /ticket

GET /ticket/{id}

POST /ticket

PUT /ticket/{id}

DELETE /ticket/{id}

## Assignment Requirements Implemented

* Spring IoC
* Java Stream
* Intermediate Native SQL Query
* Containerization with Docker
* Microservices using OpenFeign
=======
# property-ticket-microservice
Property and ticket management system built with Spring Boot, OpenFeign microservices, Docker, H2 Database, Java Streams, and native SQL queries.
