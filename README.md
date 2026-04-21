# Voting System REST API

RESTful API built with Spring Boot and MySQL to manage voters, candidates, votes, and election statistics.

## Tech Stack

- Java 21
- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- MySQL
- MapStruct
- Spring Validation
- OpenAPI (Swagger UI)

## Business Rules

- A voter can cast only one vote.
- Votes must reference existing voter and candidate.
- When a vote is cast:
  - `voter.hasVoted` becomes `true`.
  - `candidate.votes` increases by 1.
- A person cannot be both voter and candidate.
  - This implementation enforces it by unique name across `voters.name` and `candidates.name`.

## Prerequisites

- Java 21
- Maven 3.9+
- MySQL running on `localhost:3306`
- Database created: `prueba`

## Configuration

Main configuration is in `src/main/resources/application.properties`:

- `spring.datasource.url=jdbc:mysql://localhost:3306/prueba`
- `spring.datasource.username=root`
- `spring.datasource.password=1234`

Adjust username/password as needed for your local setup.

## Run Locally

```bash
mvn clean test
mvn spring-boot:run
```

API base URL:

- `http://localhost:8080`

Swagger UI:

- `http://localhost:8080/swagger-ui.html`

OpenAPI JSON:

- `http://localhost:8080/api-docs`

## API Endpoints

### Voters

- `POST /voters`
- `GET /voters`
- `GET /voters/{id}`
- `DELETE /voters/{id}`

### Candidates

- `POST /candidates`
- `GET /candidates`
- `GET /candidates/{id}`
- `DELETE /candidates/{id}`

### Votes

- `POST /votes`
- `GET /votes`
- `GET /votes/statistics`

## Example Requests (curl)

Create voter:

```bash
curl -X POST http://localhost:8080/voters \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice Johnson","email":"alice@example.com"}'
```

Create candidate:

```bash
curl -X POST http://localhost:8080/candidates \
  -H "Content-Type: application/json" \
  -d '{"name":"Bob Miller","party":"Independent"}'
```

Cast vote:

```bash
curl -X POST http://localhost:8080/votes \
  -H "Content-Type: application/json" \
  -d '{"voterId":1,"candidateId":1}'
```

Get statistics:

```bash
curl http://localhost:8080/votes/statistics
```

## Test Coverage

Current automated tests include:

- `VoteServiceTest` for one-vote rule and vote state updates.
- `VoteControllerTest` for vote creation and statistics endpoint.
- `PruebaApplicationTests` for Spring context bootstrapping.

## Optional Features

- JWT authentication can be added as an extra.
- Filtering and pagination for list endpoints can be added as an extra.

## Evidence for Submission

Include screenshots for:

- Successful vote creation response
- Rejected second vote attempt
- `/votes/statistics` response
- Swagger UI page with endpoints
