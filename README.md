# Soul Breach - RPG Backend

Scalable backend for the **Soul Breach** universe, a creature-collection RPG. Built with **Kotlin** and **Ktor**, following the principles of **Hexagonal Architecture** (Ports and Adapters) and **Domain-Driven Design (DDD)**.

---

## Core Technologies
* **Language:** [Kotlin 2.3.0](https://kotlinlang.org/)
* **Framework:** [Ktor 3.4.0](https://ktor.io/)
* **Documentation:** [Swagger UI / OpenAPI](https://swagger.io/)
* **Testing:** [JUnit 5](https://junit.org/junit5/)
* **Database:** [PostgreSQL](https://www.postgresql.org/) (by Docker)
* **ORM:** [Exposed](https://jetbrains.github.io/Exposed/)

---

## Project Architecture

The project is organized into layers to ensure decoupling and testability:

* **`application`**: Contains the business logic and orchestration.
  * **`domain`**: The heart of the application.
    * **`model`**: Domain entities and Value Objects.
  * **`port`**: Inbound (Use Cases) and Outbound (Repository interfaces) ports.
  * **`service`**: Implementation of the business logic.
* **`infrastructure`**: Technical implementations and low-level details.
  * **`driver/adapter/rest`**: REST routes organized by domain features.
  * **`driven/adapter/persistence`**: Persistence implementations (Repositories).
  * **`dto`**: Data Transfer Objects for internal/external communication.

---

## Package Structure (Feature-Driven)

```
├── application/                         
│   └── domain/
│       ├── event                       # Domain Event
│       └── model/                      # Aggregates and VO (e.g., Player, Creature, Combat)
│           └──exception                # Model exceptions
│   └── ports/                          # UseCase interfaces & Repository Ports
│       ├── in                          # Commands & queries incoming comunication
│       └── out                         # Outcoming comunication ports
│   └── service                         # Application logic implementation
├── infrastructure/        
│   └──  driven/                        
│       └── adapter/                    # Outcoming adapters
│           └──  persistence            # Persistence Adapters (DAOs, Tables and Repositories)
│       └──  driver                     # Incoming adapters
│           ├── adapeter/rest/routes    # Agruping context routing
│           ├── event                   # Driver events
│           ├── request                 
│           └── response/
│               └── dto

```

## Installation & Setup

### 1. Clone the Repository
```bash
   git clone [https://github.com/your-username/soul-breach.git](https://github.com/your-username/soul-breach.git)
   cd soul-breach
```
### 2. IntelliJ IDEA Configuration
- For an optimized development experience, it is highly recommended to install the Ktor IntelliJ IDEA plugin

### 3. Running the Server
- The server will be available at http://localhost:8080

## API Documentation (Swagger)
- Explore and test the endpoints interactively from your browser: 👉 http://localhost:8080/swagger

### REST API (Management)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/players` | Registers a new player. A starting creature is automatically added by default. |
| **GET** | `/players/{id}` | Retrieves player details, including their current team and stats. |
| **POST** | `/players/{id}/creatures` | Adds a specific creature to the player's team. |
| **POST** | `/combats` | Initializes a new combat instance. Requires `playerId` in the request body. |

### WebSockets (Real-Time Combat)

Once a combat instance is created, players must connect via WebSocket to send and receive actions.

**Connection URL:**
`ws://localhost:8080/ws/combats/{combatId}?playerId={playerId}`

**Action Payload (Client -> Server):**
To perform an attack or action during an active turn, send the following JSON:

```json
{
    "type": "ACTION",
    "activeId": "f9347115-33c2-4cfb-b8f7-2fa91085c922",
    "targetId": "2743271d-022f-41f4-b955-8208ac276209"
}
