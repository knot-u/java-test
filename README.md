# Voting System REST API - Belongs to Santiago Cardona Arboleda

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


## Evidence for Submission

Include screenshots for:
Succes Log In:
Authorized
<img width="1570" height="793" alt="image" src="https://github.com/user-attachments/assets/b6214418-f14a-400d-be78-838827add129" />

Unauthorized
<img width="1583" height="895" alt="image" src="https://github.com/user-attachments/assets/87731344-3aab-42ec-9105-12b321e65351" />

Voter Creation:
<img width="1571" height="904" alt="image" src="https://github.com/user-attachments/assets/e5d4303b-9d66-4634-a0cc-e98bf3c4f938" />

Voter Already Exists:
<img width="1582" height="751" alt="image" src="https://github.com/user-attachments/assets/cd5817df-636c-4833-99fe-64d298f9dd37" />

Get All Voters:
<img width="1559" height="753" alt="image" src="https://github.com/user-attachments/assets/691ff22e-f72e-485d-b01d-cef9334bfcb3" />

Crate new Candidate:
<img width="1559" height="846" alt="image" src="https://github.com/user-attachments/assets/af801869-5a4a-408e-ad36-59aac522f64d" />

Create Existing Candidadte: 
<img width="1547" height="775" alt="image" src="https://github.com/user-attachments/assets/44737f32-d010-42da-847e-ef8fec7623c3" />

Get All Candidades:
<img width="1576" height="851" alt="image" src="https://github.com/user-attachments/assets/49562903-a851-4dd0-9916-872d5dd80f88" />

Vote:
<img width="1570" height="911" alt="image" src="https://github.com/user-attachments/assets/332b78aa-7382-46df-8814-1924cfbcc87c" />

Votes statistics:
<img width="1201" height="941" alt="image" src="https://github.com/user-attachments/assets/4c4b6e7f-b632-4452-ba64-1adf532601a9" />

Swagger UI:
<img width="1262" height="1019" alt="image" src="https://github.com/user-attachments/assets/8e3cf813-4251-4572-a045-e95073ac5759" />

Unit Tests:
![Uploading image.png…]()


