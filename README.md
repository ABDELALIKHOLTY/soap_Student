# 🎓 Web Service SOAP — Student Management

Service web SOAP multi-opérations basé sur **Spring Boot 3.2.4**, **Spring Web Services** et **MySQL** pour gérer les étudiants.

---

## 📋 Contenu

- [Technologie](#technologies)
- [Structure du projet](#structure)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Opérations SOAP](#opérations-soap)
- [Exemples de requêtes](#exemples)
- [Configuration](#configuration)
- [WSDL](#wsdl)

---

## 🛠 Technologie

| Composant | Version | Description |
|---|---|---|
| Java | 21 | JDK 21 |
| Spring Boot | 3.2.4 | Framework Web |
| Spring Web Services | 3.2.4 | Services SOAP |
| MySQL | 8.0+ | Base de données |
| JAXB2 Maven Plugin | 3.1.0 | Génération code SOAP |
| HikariCP | Inclus | Pool connexion |
| Lombok | 1.18+ | Annotations Java |
| Maven | Wrapper | Build & package |

---

## 📁 Structure

```
src/main/java/
├── com/example/demo/
│   ├── WebServiceApplication.java          # Point d'entrée Spring Boot
│   ├── model/
│   │   └── Student.java                    # Entité JPA (id, name, email)
│   ├── repository/
│   │   └── StudentRepository.java          # Interface JpaRepository
│   ├── Exception/
│   │   └── StudentException.java           # Exception SOAP personnalisée
│   └── web/services/soap/
│       ├── endpoint/
│       │   └── StudentEndpoint.java        # 3 opérations SOAP
│       └── config/
│           └── WebServiceConfig.java       # Configuration WSDL
│
├── com/kholty/student/                     # Classes JAXB générées
│   ├── AddStudentRequest.java
│   ├── AddStudentResponse.java
│   ├── GetStudentRequest.java
│   ├── GetStudentResponse.java
│   ├── GetAllStudentsRequest.java
│   ├── GetAllStudentsResponse.java
│   └── StudentType.java
│
src/main/resources/
├── application.properties                  # Config MySQL, port 8081
└── student.xsd                             # Schéma SOAP (source JAXB)
```

---

## ⚙️ Prérequis

- **Java 21+** installé
- **Maven** (v3.6+) — Wrapper inclus dans le projet
- **MySQL 8.0+** en local ou accessible en réseau

### Vérifier les installations

```bash
java -version        # Doit afficher Java 21
mvn -version         # Doit afficher Maven 3.6+
mysql --version      # Doit afficher MySQL 8.0+
```

---

## 🚀 Installation

### 1. Créer la base de données MySQL

```sql
CREATE DATABASE soap;
CREATE USER 'root'@'localhost' IDENTIFIED BY 'kholty';
GRANT ALL PRIVILEGES ON soap.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Construire le projet

```bash
cd Web_service_SOAP-SOAP
./mvnw clean install
```

Cela génère les classes JAXB via `jaxb2-maven-plugin` à partir de `student.xsd`.

### 3. Lancer l'application

```bash
./mvnw spring-boot:run
```

✓ Application disponible sur **http://localhost:8081**  
✓ WSDL disponible sur **http://localhost:8081/ws/student.wsdl**  
✓ Logs affichent les requêtes SQL (si `spring.jpa.show-sql=true`)

---

## 📡 Opérations SOAP

**Endpoint :** `http://localhost:8081/ws`  
**Namespace :** `http://kholty.com/student`

### 1. Ajouter un étudiant

```
POST /ws HTTP/1.1
Content-Type: application/soap+xml

addStudentRequest(name: String, email: String) → Student
```

### 2. Récupérer un étudiant

```
POST /ws HTTP/1.1
Content-Type: application/soap+xml

getStudentRequest(id: Long) → Student | StudentException
```

### 3. Récupérer tous les étudiants

```
POST /ws HTTP/1.1
Content-Type: application/soap+xml

getAllStudentsRequest() → List<Student> | StudentException
```

**Remarques:**
- Pas de DataInitializer : Les données proviennent uniquement de MySQL
- Les opérations écrivent/lisent directement dans la BD MySQL
- HikariCP gère le pool de connexions
- JPA Hibernate génère automatiquement les requêtes SQL

---

## 📨 Exemples SOAP

### ➕ Ajouter étudiant

**Requête :**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" 
                   xmlns:tns="http://kholty.com/student">
  <SOAP-ENV:Body>
    <tns:addStudentRequest>
      <tns:name>Ahmed Kholty</tns:name>
      <tns:email>ahmed@example.com</tns:email>
    </tns:addStudentRequest>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

**Réponse :**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Body>
    <ns2:addStudentResponse xmlns:ns2="http://kholty.com/student">
      <ns2:student>
        <ns2:id>1</ns2:id>
        <ns2:name>Ahmed Kholty</ns2:name>
        <ns2:email>ahmed@example.com</ns2:email>
      </ns2:student>
    </ns2:addStudentResponse>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

---

### 🔍 Récupérer étudiant par ID

**Requête :**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" 
                   xmlns:tns="http://kholty.com/student">
  <SOAP-ENV:Body>
    <tns:getStudentRequest>
      <tns:id>1</tns:id>
    </tns:getStudentRequest>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

**Réponse :**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Body>
    <ns2:getStudentResponse xmlns:ns2="http://kholty.com/student">
      <ns2:student>
        <ns2:id>1</ns2:id>
        <ns2:name>Ahmed Kholty</ns2:name>
        <ns2:email>ahmed@example.com</ns2:email>
      </ns2:student>
    </ns2:getStudentResponse>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

**Erreur (ID inexistant) :**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Body>
    <SOAP-ENV:Fault>
      <faultcode>SOAP-ENV:Server</faultcode>
      <faultstring>student with id (999) not found!</faultstring>
    </SOAP-ENV:Fault>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

---

### 📋 Récupérer tous les étudiants

**Requête :**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" 
                   xmlns:tns="http://kholty.com/student">
  <SOAP-ENV:Body>
    <tns:getAllStudentsRequest/>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

**Réponse :**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Body>
    <ns2:getAllStudentsResponse xmlns:ns2="http://kholty.com/student">
      <ns2:student>
        <ns2:id>1</ns2:id>
        <ns2:name>Ahmed Kholty</ns2:name>
        <ns2:email>ahmed@example.com</ns2:email>
      </ns2:student>
      <ns2:student>
        <ns2:id>2</ns2:id>
        <ns2:name>Ali Hassan</ns2:name>
        <ns2:email>ali@example.com</ns2:email>
      </ns2:student>
    </ns2:getAllStudentsResponse>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

**Erreur (table vide) :**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Body>
    <SOAP-ENV:Fault>
      <faultcode>SOAP-ENV:Server</faultcode>
      <faultstring>students are not exist yet!</faultstring>
    </SOAP-ENV:Fault>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

---

## 🔧 Configuration

### application.properties

Fichier : `src/main/resources/application.properties`

```properties
# Serveur
spring.application.name=demo
server.port=8081

# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/soap
spring.datasource.username=root
spring.datasource.password=kholty

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# HikariCP
spring.datasource.hikari.minimumIdle=5
spring.datasource.hikari.maximumPoolSize=20
spring.datasource.hikari.idleTimeout=30000
spring.datasource.hikari.maxLifetime=2000000
spring.datasource.hikari.connectionTimeout=30000
spring.datasource.hikari.poolName=HikariPoolBooks
```

### Modifier la connexion MySQL

Pour utiliser une autre BD ou utilisateur, modifiez :

```properties
spring.datasource.url=jdbc:mysql://HOST:PORT/DATABASE
spring.datasource.username=USER
spring.datasource.password=PASSWORD
```

### Schéma & Classe Student

**Modèle :** `src/main/java/com/example/demo/model/Student.java`

```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
}
```

**Schéma XSD :** `src/main/resources/student.xsd`
- Source pour la génération JAXB2
- Définit les messages SOAP

---

## 🌐 WSDL

**URL :** `http://localhost:8081/ws/student.wsdl`

### Tester avec un client SOAP

Outils : **Postman**, **SoapUI**, **Insomnia**, **VS Code REST Client**

**Étapes :**
1. Compier l'URL du WSDL : `http://localhost:8081/ws/student.wsdl`
2. Importer dans le client SOAP
3. Envoyer une requête depuis les exemples ci-dessus

---

## 📚 Fichiers importants

| Fichier | Rôle |
|---|---|
| `src/main/java/com/example/demo/WebServiceApplication.java` | Point d'entrée Spring Boot |
| `src/main/java/com/example/demo/web/services/soap/endpoint/StudentEndpoint.java` | 3 opérations SOAP |
| `src/main/java/com/example/demo/model/Student.java` | Entité JPA (id, name, email) |
| `src/main/java/com/example/demo/repository/StudentRepository.java` | Accès données MySQL |
| `src/main/java/com/example/demo/Exception/StudentException.java` | Exception SOAP |
| `src/main/java/com/example/demo/web/services/soap/config/WebServiceConfig.java` | Configuration WSDL |
| `src/main/resources/student.xsd` | Schéma SOAP (source JAXB) |
| `src/main/resources/application.properties` | Configuration BD, port, etc. |
| `pom.xml` | Dépendances Maven |

---

## 📝 Flux de fonctionnement

```
Client SOAP
    ↓
HTTP POST /ws
    ↓
MessageDispatcherServlet (Spring WS)
    ↓
StudentEndpoint (3 opérations)
    ↓
StudentRepository (JpaRepository)
    ↓
MySQL Database (table student)
    ↓
Réponse SOAP → Client
```
