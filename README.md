# Soul Breach - RPG Backend

Scalable backend for the **Soul Breach** universe, a creature-collection RPG. Built with **Kotlin** and **Ktor**, following the principles of **Hexagonal Architecture** (Ports and Adapters) and **Domain-Driven Design (DDD)**.

---

## Core Technologies
* **Language:** [Kotlin 2.3.0](https://kotlinlang.org/)
* **Framework:** [Ktor 3.4.0](https://ktor.io/)
* **Documentation:** [Swagger UI / OpenAPI](https://swagger.io/)
* **Testing:** [JUnit 5](https://junit.org/junit5/)

---

## 🏗️ Project Architecture

The project is organized into layers to ensure decoupling and testability:

* **`application`**: Contains the business logic and orchestration.
  * **`domain`**: The heart of the application.
    * **`model`**: Domain entities and Value Objects.
    * **`port`**: Inbound (Use Cases) and Outbound (Repository interfaces) ports.
    * **`service`**: Implementation of the business logic.
* **`infrastructure`**: Technical implementations and low-level details.
  * **`adapters/driver/rest`**: REST routes organized by domain features.
  * **`adapters/driven/persistence`**: Persistence implementations (Repositories).
  * **`adapters/dto`**: Data Transfer Objects for internal/external communication.

---

## 📁 Package Structure (Feature-Driven)

```text
src/main/kotlin/com/soulbreach/
├── application/                         
│   ├── domain/            
│       ├── model/             # Domain Entities (e.g., Player, Creature)
│       ├── port/              # UseCase interfaces & Repository Ports
│       ├── service/           # Application logic implementation
├── infrastructure/        
│   ├── adapters/
│       ├── dto/               # Request/Response DTOs & Commands
│       ├── driver/rest/       # Domain-grouped routes (e.g., player, combat)
│       └── driven/persistence # Persistence adapters (InMemory, SQL)

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

### Main Endpoints:
- POST /players: Register a new player. By default, a starting creature is automatically added to the player's team upon creation.
- POST /players/{id}/creatures: Add a creature to a player's team.
