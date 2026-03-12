# 🎓 Web Service SOAP — Gestion des Étudiants

Service web SOAP développé avec **Spring Boot 3.2.4** et **Spring Web Services** permettant de gérer des étudiants (ajout, consultation, liste de tous les étudiants).

---

## 📋 Table des matières

- [Technologies](#-technologies)
- [Architecture du projet](#-architecture-du-projet)
- [Prérequis](#-prérequis)
- [Installation & Lancement](#-installation--lancement)
- [Services SOAP disponibles](#-services-soap-disponibles)
- [Exemples de requêtes SOAP](#-exemples-de-requêtes-soap)
- [Données initiales](#-données-initiales)
- [Accès au WSDL](#-accès-au-wsdl)

---

## 🛠 Technologies

| Technologie | Version |
|---|---|
| Java JDK | 21 |
| Spring Boot | 3.2.4 |
| Spring Web Services | Inclus dans starter |
| JAXB2 Maven Plugin | 3.1.0 |
| Lombok | Inclus |
| Maven | Wrapper (mvnw) |
| wsdl4j | Inclus |
| Base de données | H2 (en mémoire) |

---

## 📁 Architecture du projet

```
src/main/java/com/example/demo/
├── WebServiceApplication.java        # Point d'entrée Spring Boot
├── init/
│   └── DataInitializer.java          # Chargement de 5 étudiants au démarrage
├── endpoint/
│   └── StudentEndpoint.java          # Endpoint SOAP (traitement des requêtes)
├── model/
│   └── Student.java                  # Modèle métier (id, name, email)
├── repository/
│   └── StudentRepository.java        # Repository en mémoire
└── soap/config/
    └── WebServiceConfig.java         # Configuration SOAP WSDL

src/main/java/com/kholty/student/
└── (Classes générées par JAXB2)      # Requests/Responses générées du XSD

src/main/resources/
├── application.properties            # Configuration (port 8080)
└── student.xsd                       # Schéma XML des messages SOAP
```

---

## ⚙️ Prérequis

- **Java 21** installé
- **Maven Wrapper** inclus dans le projet (`mvnw.cmd` pour Windows)

---

## 🚀 Installation & Lancement

### 1️⃣ Cloner/Extraire le projet
```bash
cd Web_service_SOAP-SOAP
```

### 2️⃣ Construire le projet
```bash
.\mvnw.cmd clean install
```

Ou sur macOS/Linux:
```bash
./mvnw clean install
```

### 3️⃣ Démarrer le serveur SOAP
```bash
.\mvnw.cmd spring-boot:run
```

L'application démarre sur **http://localhost:8080**

**Confirmation du démarrage:**
```
✓ 5 étudiants ont été chargés dans la base de données
```

---

## 📡 Services SOAP disponibles

Le service SOAP expose **3 opérations** pour la gestion des étudiants:

### 1. **addStudentRequest** — Ajouter un étudiant
- **Entrée** : name, email
- **Sortie** : Student (id, name, email)

### 2. **getStudentRequest** — Récupérer un étudiant par ID
- **Entrée** : id
- **Sortie** : Student (id, name, email) ou null

### 3. **getAllStudentsRequest** — Récupérer tous les étudiants
- **Entrée** : (vide)
- **Sortie** : Liste complète des étudiants

---

## � Exemples de requêtes SOAP

L'endpoint SOAP est: **`http://localhost:8080/ws`**

### ➕ Ajouter un étudiant

```xml
<?xml version="1.0" encoding="UTF-8"?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" 
                   xmlns:tns="http://kholty.com/student">
  <SOAP-ENV:Body>
    <tns:addStudentRequest>
      <tns:name>Jean Dupont</tns:name>
      <tns:email>jean.dupont@example.com</tns:email>
    </tns:addStudentRequest>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

**Réponse:**
```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Body>
    <ns2:addStudentResponse xmlns:ns2="http://kholty.com/student">
      <ns2:student>
        <ns2:id>6</ns2:id>
        <ns2:name>Jean Dupont</ns2:name>
        <ns2:email>jean.dupont@example.com</ns2:email>
      </ns2:student>
    </ns2:addStudentResponse>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

---

### 🔍 Récupérer un étudiant par ID

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

**Réponse:**
```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Body>
    <ns2:getStudentResponse xmlns:ns2="http://kholty.com/student">
      <ns2:student>
        <ns2:id>1</ns2:id>
        <ns2:name>Ahmed Kholty</ns2:name>
        <ns2:email>ahmed.kholty@example.com</ns2:email>
      </ns2:student>
    </ns2:getStudentResponse>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

---

### 📋 Récupérer tous les étudiants

```xml
<?xml version="1.0" encoding="UTF-8"?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" 
                   xmlns:tns="http://kholty.com/student">
  <SOAP-ENV:Body>
    <tns:getAllStudentsRequest/>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

**Réponse (Exemple):**
```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Body>
    <ns2:getAllStudentsResponse xmlns:ns2="http://kholty.com/student">
      <ns2:student>
        <ns2:id>1</ns2:id>
        <ns2:name>Ahmed Kholty</ns2:name>
        <ns2:email>ahmed.kholty@example.com</ns2:email>
      </ns2:student>
      <ns2:student>
        <ns2:id>2</ns2:id>
        <ns2:name>Fatima Hassan</ns2:name>
        <ns2:email>fatima.hassan@example.com</ns2:email>
      </ns2:student>
      <!-- ... 3 autres étudiants ... -->
    </ns2:getAllStudentsResponse>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

---

## 📊 Données initiales

Au démarrage, **5 étudiants** sont automatiquement chargés:

| ID | Nom | Email |
|---|---|---|
| 1 | Ahmed Kholty | ahmed.kholty@example.com |
| 2 | Fatima Hassan | fatima.hassan@example.com |
| 3 | Mohamed Ali | mohamed.ali@example.com |
| 4 | Leila Mansour | leila.mansour@example.com |
| 5 | Omar Saada | omar.saada@example.com |

**Localisation:** `src/main/java/com/example/demo/init/DataInitializer.java`

---

## 🌐 Accès au WSDL

L'API SOAP est décrite par un WSDL accessible à:

**URL:** `http://localhost:8080/ws/student.wsdl`

### Utiliser avec Wizler / Postman / SoapUI

1. Lancez l'application
2. Ouvrez votre client SOAP (Wizler, Postman, SoapUI, etc.)
3. URL: `http://localhost:8080/ws`
4. Collez une requête SOAP depuis les exemples ci-dessus
5. Cliquez sur **Go/Send**

---

## 📝 Notes importantes

- **Noms des éléments SOAP:** Tous les éléments sont en **minuscules** 
  - ✅ `getAllStudentsRequest` 
  - ✅ `getStudentRequest`
  - ✅ `addStudentRequest`
  
- **Namespace:** `http://kholty.com/student`
- **Port:** `8080`
- **Endpoint:** `http://localhost:8080/ws`
- **Stockage:** En mémoire (données perdues au redémarrage)

---

## 🔧 Configuration

### Modifier le port par défaut
Éditez `src/main/resources/application.properties`:
```properties
server.port=8080
```

### Ajouter des étudiants au démarrage
Éditez `src/main/java/com/example/demo/init/DataInitializer.java`:
```java
studentRepository.ajouter("Votre Nom", "email@example.com");
```

---

## 📚 Ressources

- [WSDL](http://localhost:8080/ws/student.wsdl) — Définition du service
- Schéma XSD — `src/main/resources/student.xsd`
- Configuration SOAP — `src/main/java/com/example/demo/soap/config/WebServiceConfig.java`

---

## 🎯 Utilisation typique

```bash
# Terminal 1 - Démarrer l'application
cd Web_service_SOAP-SOAP
.\mvnw.cmd spring-boot:run

# Terminal 2 - Tester avec curl (exemple)
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: application/soap+xml" \
  -d @request.xml
```

---

**Développé avec ❤️ par Kholty**
