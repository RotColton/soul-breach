# Design Documentation

## 1. Arquitectura: Ports & Adapters (Hexagonal)
Este proyecto utiliza una arquitectura de Puertos y Adaptadores orientada al dominio. 
El objetivo principal es aislar la lógica de negocio (el "núcleo") de las tecnologías externas (bases de datos, frameworks web, etc.).

### 1.1. Capas del Sistema

| Capa | Responsabilidad                                                                                                                                                                 | Dependencias |
| :--- |:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| :--- |
| **Domain** | Define el estado y las reglas de negocio puras (`Combat`, `Creature`, `Player`). Contiene la lógica de validación de turnos, cálculo de daño, generador de bonificaciones, etc. | Ninguna |
| **Ports** | Define las interfaces de entrada (`Use Cases`) y de salida (`Ports`). Actúa como el contrato del sistema.                                                                       | Domain |
| **Service** | Orquestador de la aplicación. Implementa la lógica de los casos de uso. Ejemplo: ejecución de un turno de combate.                                                              | Domain, Ports |
| **Infrastructure (Driver)** | Adaptadores de entrada. Gestiona la comunicación externa (WebSockets de Ktor, Rutas REST y DTOs).                                                                               | Application, Domain |
| **Infrastructure (Driven)** | Adaptadores de salida. Implementa la persistencia de datos (Exposed ORM, DAOs y Repositorios SQL).                                                                              | Application, Domain |

```
[ Driver Adapters ]        [ Application ]         [ Driven Adapters ]
(WebSockets / REST)  -->  [ Services / Ports ]  -->   (Exposed DB)
        ^                         |                        ^
        |                         v                        |
        +---------------- [ Domain Model ] ----------------+
```

---

**Packages Structure:**

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

### 1.3. Justificación de Puertos y Adaptadores

La elección de esta arquitectura se basa en tres pilares fundamentales:

1. **Testabilidad y Mantenibilidad:** Al aislar el dominio, podemos realizar pruebas unitarias sobre la lógica de combate (daño, muerte de criaturas, rotación de turnos) 
sin depender de la base de datos o del servidor Ktor. Esto asegura que el "corazón" del juego sea robusto.

2. **Independencia Tecnológica:** Los puertos actúan como contratos. Si en el futuro decidimos cambiar la base de datos SQL (Exposed) por una solución NoSQL o 
un sistema de caché en memoria como Redis para mejorar la latencia de los combates, 
solo necesitamos implementar un nuevo adaptador en la capa de infraestructura sin tocar la lógica de negocio.

3. **Escalabilidad:** La arquitectura facilita la transición hacia microservicios. 
Si el contexto de "Combat" crece demasiado, puede extraerse a su propio servicio independiente manteniendo su API de puertos intacta.

---

## 2. Modelado de Dominio

### 2.1. Entidades Principales (Creature, Attributes & Combat)

* **`Attributes`:** Se han modelado como objetos de valor (Value Objects) que contienen las estadísticas elementales: `hp` (puntos de vida), `attack` (daño) y `speed` (velocidad). 
La inicialización es dinámica; los valores se asignan por defecto según la **clase de criatura** instanciada (ej. un `DEFENDER` tendrá más HP, mientras que un `WARRIOR` tendrá más Velocidad. 
En cambio, un `ENCHANTER` tendrá más puntos de ataque pero menos velocidad).

* **`Creature`:** Es la entidad activa.

* **`Combat`:** Actúa como el **Aggregate Root**. Coordina a los dos jugadores, sus listas de criaturas y el ciclo de vida de la partida.

### 2.2. Gestión de Turnos y Estado

* **Orden de Turno:** Se calcula al inicio del combate basándose en el atributo `speed`. 
El sistema genera una lista de IDs (`turnOrder`) donde la criatura más rápida ocupa la primera posición. 
Tras cada acción exitosa, se desplaza el índice de la lista (rotación), asegurando un flujo continuo.

* **Estado del Combate:** Se mantiene en memoria durante la ejecución para maximizar la velocidad de respuesta en WebSockets y 
se **persiste de forma definitiva** únicamente cuando el juego alcanza un estado `FINISHED`.

* **Condición de Victoria:** Al final de cada turno, el dominio evalúa la vitalidad de las criaturas de ambos jugadores. 
Si todas las criaturas de un jugador tienen `hp <= 0`, se declara al oponente como ganador automáticamente.

### 2.3. Decisiones de Diseño Relevantes

* **Muerte y Eventos de Dominio:** Cuando una criatura muere, el modelo de dominio dispara un **Evento de Dominio (`CreatureDied`)**.
Este evento es capturado por el servicio para eliminar la persistencia de la criatura.

* **Limpieza Post-Combate:** Una decisión clave ha sido la eliminación de la persistencia de criaturas muertas al finalizar el juego. 
Esto mantiene la base de datos limpia de entidades que ya no tienen utilidad en el historial activo, optimizando el almacenamiento a largo plazo.

---

## 3. Escalabilidad y Alta Disponibilidad
Ante un escenario de millones de jugadores concurrentes, el sistema evolucionaría de la siguiente manera:

### 3.1. Evolución de Repositorios y Particionado

* **De SQL a NoSQL:** Los repositorios de combate pasarían de una base de datos relacional (PostgreSQL/MySQL) a una base de datos documental (como MongoDB o DynamoDB). 
Los combates son documentos independientes que no requieren uniones (joins) complejas, lo que permite lecturas y escrituras ultra rápidas.

* **Sharding (Particionado):** Particionaríamos los combates utilizando el CombatID o el RegionID como Partition Key. 
Esto permite distribuir la carga entre múltiples nodos, evitando cuellos de botella en un solo servidor de base de datos.

* **Escalabilidad mediante Event Sourcing:** Para manejar millones de eventos de combate sin bloquear la base de datos, implementaremos un patrón de **Event Sourcing**:
  * **Escritura de Alto Rendimiento:** Las inserciones "append-only" son órdenes de magnitud más rápidas y escalables en bases de datos distribuidas como DynamoDB.
  * Separación de Lectura y Escritura (CQRS):** 
    * El **Write Model** se encarga de validar y guardar los eventos en un *Event Store*.
    * El **Read Model** (Proyecciones) construye una vista optimizada del combate en Redis para que el socket la lea instantáneamente.
    Esto permite escalar la lectura y la escritura de forma independiente.
  * **Replay y Recuperación:** En caso de fallo de un nodo del clúster, 
  el nuevo nodo puede reconstruir el estado exacto de cualquier combate activo simplemente releyendo su flujo de eventos desde el último *Snapshot*.
  * **Análisis de Datos Masivo:** Al tener todos los eventos históricos, podemos aplicar procesos de Big Data para equilibrar el juego (balance de personajes) 
  analizando qué criaturas ganan más o cuántos turnos dura un combate promedio.

---

### 3.2. Estrategia de Caching

* **Estado Activo (Redis):** El estado del combate se mantendría en un clúster de Redis. 
Al ser un juego por turnos, no necesitamos consultar el disco en cada ataque; 
solo persistiríamos en la DB principal al final de la partida o cada X turnos (Check-pointing).

---

### 3.3. Migración a Servicios Cloud (AWS / PlayFab)

Migraríamos la lógica de gestión de usuarios y economía a PlayFab para delegar la persistencia de datos persistentes y la gestión de sesiones.

***A. PlayFab: Gestión de Jugadores y LiveOps:***

* **PlayFab Economy & Inventory:**
    * Para gestionar las criaturas y objetos de cada jugador de forma segura. 
  Asegura la integridad de los datos (evita trampas en los atributos) antes de que estos entren al motor de combate.
* **Matchmaking (PlayFab Matchmaker):**
    * Proporciona un sistema de colas basado en *tickets* que agrupa jugadores por nivel de habilidad (ELO) o latencia regional,
  entregando un `CombatID` de forma automática y una IP de servidor automáticamente.
* **PlayFab CloudScript:**
    * Para ejecutar lógica post-combate, como la entrega de recompensas o subida de nivel, sin cargar el servidor de juego principal.

***B. AWS: Infraestructura de Cómputo y Escalado:***

* **Amazon GameLift:**
  * Servicio dedicado para desplegar y escalar servidores de juegos multijugador.
    Gestiona automáticamente el despliegue de nuestras instancias de Ktor en diferentes regiones del mundo. 
  Si hay un pico de jugadores en Europa, GameLift levanta más servidores en esa región y los apaga cuando no se usan, optimizando costes.
  
* **Amazon ElastiCache (Redis):**
  * Para mantener el estado efímero del combate (vida actual, turnos). 
  Es mucho más rápido que cualquier base de datos en disco y permite que, si un contenedor de Ktor falla, otro pueda recuperar el estado del combate instantáneamente

* **Amazon DynamoDB:**
  * Para persistir los resultados históricos de los combates. 
  Es una base de datos NoSQL que ofrece latencia de milisegundos a cualquier escala, ideal para guardar millones de registros de "combates finalizados".

* **AWS Global Accelerator:** 
  * Para reducir el jitter y la latencia del WebSocket. 
  Enruta el tráfico de los jugadores a través de la red privada de AWS hacia el servidor más cercano, evitando los saltos congestionados de la internet pública.

---

### 3.6. ¿Qué mantendríamos propio?
A pesar de la migración, el "Battle Engine" (Motor de Combate) seguiría siendo nuestro código core por estas razones:

* **Reglas Propias:** El cálculo de daño elemental, la gestión de la velocidad y el sistema de eventos de dominio son lo que hace único al juego. 
Ningún servicio externo (PlayFab/AWS) ofrece una "lógica de combate" prefabricada que se adapte a nuestro diseño.

* **Portabilidad:** Al mantener el motor en contenedores Docker (ECS/EKS), no estamos "atrapados" al 100% por un proveedor (evitamos el vendor lock-in total).

--- 

## 4. Integración Futura

### 4.1. PlayFab como Puerto Externo

Dentro de nuestra arquitectura hexagonal, PlayFab no "invade" el dominio, sino que se trata como un Driven Adapter (Adaptador de Salida).

- **Implementación:** Crearíamos una clase PlayFabPlayerAdapter que implemente nuestra interfaz de puerto PlayerRepository.

- **Funcionamiento:** Cuando el servicio necesite validar el equipo de un jugador o guardar su progreso, el adaptador realizará llamadas a la API de PlayFab.

- **Ventaja:** Si mañana queremos usar otro servicio de perfiles, solo cambiamos el adaptador; 
la lógica de combate de nuestro CombatService no se entera del cambio, ya que siempre interactúa con la interfaz del puerto.

### 4.2. Event Bus (AWS SNS / EventBridge)
Para desacoplar el combate de otros sistemas (como logros, analíticas o misiones), integraremos un Event Bus.

* **Flujo de Eventos:** Cada vez que el dominio genere un evento (ej. MatchFinished), el servicio lo enviará a un puerto llamado EventPublisher.

* **SNS / EventBridge:** El adaptador de este puerto publicará el evento en un tópico de AWS SNS o un bus de EventBridge.

* **Suscriptores:** Otros microservicios (o funciones Lambda) se suscribirán a estos eventos para:
  * Analíticas: Guardar estadísticas de uso.
  * Misiones: Comprobar si el jugador completó el reto "Mata 10 criaturas".
  * Notificaciones: Enviar un push notification al móvil del ganador.