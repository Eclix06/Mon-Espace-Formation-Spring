# Mon Espace Formation — Backend (Spring Boot 3 + Java 21 + MongoDB)

Backend du projet Mon Espace Formation. Ce serveur fournit une API REST stateless consommée par le Frontend et utilise une architecture MVC stricte, avec persistance NoSQL via MongoDB.

Frontend associé: [Mon-Espace-Formation](https://github.com/Eclix06/Mon-Espace-Formation) (Vite, port de dev 5173)

---

## Sommaire

- [Stack technique](#stack-technique)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Configuration](#configuration)
- [Lancement](#lancement)
- [Architecture et conventions](#architecture-et-conventions)
- [API REST](#api-rest)
- [Validation et gestion d’erreurs](#validation-et-gestion-derreurs)
- [Sécurité et CORS](#sécurité-et-cors)
- [Base de données](#base-de-données)
- [Tests et qualité](#tests-et-qualité)
- [Docker (MongoDB)](#docker-mongodb)
- [Structure du projet](#structure-du-projet)
- [Intégration avec le Front](#intégration-avec-le-front)
- [Contribuer](#contribuer)
- [Licence](#licence)

---

## Stack technique

- Langage: Java 21 (LTS)
- Framework: Spring Boot 3
- Web: Spring Web MVC (API RESTful)
- Persistance: Spring Data MongoDB (MongoDB Community Server)
- Échange: JSON
- Sécurité: CORS autorisant le Frontend sur port 5173
- Gestion de versions: Git
- IDE recommandés: IntelliJ IDEA (Backend), VS Code (Frontend)

---

## Prérequis

- Java JDK 21
- Maven 3.8+ (ou wrapper Maven)
- MongoDB Community Server (local ou distant)
- Git
- Frontend (optionnel pour tests d’intégration): [Mon-Espace-Formation](https://github.com/Eclix06/Mon-Espace-Formation)

---

## Installation

```bash
# Cloner le dépôt backend
git clone https://github.com/Eclix06/Mon-Espace-Formation-Spring.git
cd Mon-Espace-Formation-Spring

# Construire le projet
mvn clean install
```

---

## Configuration

Le backend se configure via `application.yml` et/ou variables d’environnement.

Exemple `src/main/resources/application.yml`:
```yaml
server:
  port: ${PORT:8080}

spring:
  application:
    name: mon-espace-formation-api
  data:
    mongodb:
      uri: ${MONGODB_URI:mongodb://localhost:27017/mesf}

# CORS: origin(s) du frontend (Vite par défaut sur 5173)
cors:
  allowed-origins: ${CORS_ALLOWED_ORIGINS:http://localhost:5173}
  allowed-methods: GET,POST,PUT,PATCH,DELETE,OPTIONS
  allowed-headers: Authorization,Content-Type

logging:
  level:
    root: INFO
    org.springframework.web: INFO
    org.springframework.data.mongodb: INFO
```

Variables d’environnement:
- `MONGODB_URI` (ex: `mongodb://localhost:27017/mesf`)
- `CORS_ALLOWED_ORIGINS` (ex: `http://localhost:5173`)
- `PORT` (par défaut `8080`)

Extraits recommandés du `pom.xml` (Java 21 + Spring Boot 3):
```xml
<properties>
  <java.version>21</java.version>
</properties>

<dependencies>
  <!-- Web MVC -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <!-- MongoDB -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
  </dependency>

  <!-- Validation -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>

  <!-- Optionnel: Lombok -->
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>

  <!-- Tests -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>
```

---

## Lancement

- Développement:
  ```bash
  mvn spring-boot:run
  ```
- Production (JAR):
  ```bash
  java -jar target/mon-espace-formation-backend.jar
  ```

L’API écoute par défaut sur `http://localhost:8080`.

---

## Architecture et conventions

- MVC strict:
  - `model` (documents/DTO)
  - `repository` (MongoRepository)
  - `service` (logique métier, transactions applicatives)
  - `controller` (endpoints REST)
- RESTful:
  - Verbes HTTP standards: `GET`, `POST`, `PUT/PATCH`, `DELETE`
- Format d’échange: JSON
- API stateless:
  - Pas de session utilisateur en mémoire
  - Préparée pour une future authentification JWT

---

## API REST

Base path recommandé: `/api`

Exemples de ressources (adapter selon domaine réel):
- Utilisateurs:
  - `GET /api/users` — liste
  - `GET /api/users/{id}` — détail
  - `POST /api/users` — création
  - `PUT /api/users/{id}` — mise à jour
  - `DELETE /api/users/{id}` — suppression
- Formations:
  - `GET /api/formations`
  - `GET /api/formations/{id}`
  - `POST /api/formations`
  - `PUT /api/formations/{id}`
  - `DELETE /api/formations/{id}`
- Sessions / Inscriptions:
  - `GET /api/sessions`
  - `POST /api/enrollments`
  - etc.

Pagination/tri (recommandé):
- `GET /api/formations?page=0&size=20&sort=title,asc`

---

## Validation et gestion d’erreurs

Validation côté backend (Jakarta Validation):
```java
public record UserDto(
  @NotBlank String name,
  @Email String email
) {}
```

Gestion d’erreurs:
- `try-catch` au niveau service, exceptions spécifiques
- `@ControllerAdvice` global pour uniformiser les réponses d’erreur
- Statuts HTTP:
  - `200 OK` — succès
  - `201 Created` — ressource créée
  - `400 Bad Request` — validation/données invalides
  - `404 Not Found` — ressource introuvable
  - `500 Internal Server Error` — erreur serveur

---

## Sécurité et CORS

- CORS configuré pour autoriser le Frontend sur port 5173.
- API stateless, prête pour JWT.

Exemple `WebMvcConfigurer`:
```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
  @Value("${cors.allowed-origins:http://localhost:5173}")
  private String allowedOrigins;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
      .allowedOrigins(allowedOrigins.split(","))
      .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
      .allowedHeaders("Authorization","Content-Type")
      .allowCredentials(false);
  }
}
```

---

## Base de données

- Base: MongoDB Community Server
- Connexion: `spring.data.mongodb.uri` (ex: `mongodb://localhost:27017/mesf`)
- Dépôt Spring Data:
```java
public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByEmail(String email);
}
```

Bonnes pratiques:
- Indexer les champs critiques (ex: `email`)
- Séparer DTO (exposition) et modèles (persistance)
- Gérer correctement `ObjectId` <-> `String`

---

## Tests et qualité

- Lancer les tests:
  ```bash
  mvn test
  ```
- Outils:
  - JUnit 5, Spring Boot Test
  - Testcontainers (MongoDB) ou profil `test` dédié
- Qualité:
  - Respect de l’architecture MVC
  - Validation systématique des entrées
  - Couverture des services et contrôleurs

---

## Docker (MongoDB)

Exemple `docker-compose.yml` pour MongoDB:
```yaml
version: "3.8"
services:
  mongo:
    image: mongo:7
    container_name: mesf-mongo
    ports:
      - "27017:27017"
    environment:
      MONGO_INITDB_DATABASE: mesf
    volumes:
      - mongo_data:/data/db

volumes:
  mongo_data:
```

Démarrer:
```bash
docker compose up -d
```

Configurer ensuite l’API avec:
- `MONGODB_URI=mongodb://localhost:27017/mesf`

---

## Structure du projet

```
src/
  main/
    java/
      com/monespaceformation/
        MonEspaceFormationApplication.java
        config/        # CORS, config générale
        controller/    # endpoints REST
        service/       # logique métier
        repository/    # MongoRepository
        model/         # documents Mongo + DTO
        exception/     # gestion globale des erreurs
    resources/
      application.yml
  test/
    java/
      com/monespaceformation/...
```

---

## Intégration avec le Front

- Frontend: [Mon-Espace-Formation](https://github.com/Eclix06/Mon-Espace-Formation)
- Dev server (Vite): `http://localhost:5173`
- Base URL API côté front: `http://localhost:8080/api`
- CORS: définir `CORS_ALLOWED_ORIGINS=http://localhost:5173`
- Authentification: pas de session (stateless). L’intégration JWT pourra se faire sans modifier les contrats existants.
- Exemple de consommation côté front:
```ts
// Exemple fetch depuis Vite (TS/JS)
const res = await fetch('http://localhost:8080/api/formations?page=0&size=10', {
  method: 'GET',
  headers: { 'Content-Type': 'application/json' },
});
const data = await res.json();
```

---

## Contribuer

- Créez une branche par fonctionnalité
- Respectez l’architecture MVC et les conventions REST
- Ajoutez des tests et la validation des entrées
- Documentez les endpoints et impacts dans la PR
- Messages de commit clairs et atomiques

---

## Licence

À préciser (ex: MIT, Apache-2.0).
