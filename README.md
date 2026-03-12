# 🎓 Web Service SOAP — Gestion des Étudiants

Service web SOAP construit avec **Spring Boot 3.2.4** et **Spring Web Services** pour gérer des étudiants : créer, consulter et lister.

---

## 📋 Table des matières

- [Technologies](#-technologies)
- [Architecture](#-architecture)
- [Prérequis](#️-prérequis)
- [Installation & Lancement](#-installation--lancement)
- [Opérations disponibles](#-opérations-disponibles)
- [Exemples de requêtes](#-exemples-de-requêtes)
- [Données initiales](#-données-initiales)
- [Configuration](#-configuration)
- [Accès au WSDL](#-accès-au-wsdl)

---

## 🛠 Technologies

| Technologie | Version |
|---|---|
| Java | 21 |
| Spring Boot | 3.2.4 |
| Spring Web Services | ✓ |
| Maven | Wrapper inclus |
| JAXB2 | 3.1.0 |
| Base de données | MySQL |

---

## 📁 Architecture

```
Web_service_SOAP-SOAP/
├── pom.xml                           # Configuration Maven
├── mvnw / mvnw.cmd                   # Maven Wrapper
│
└── src/main/
    ├── java/com/example/demo/
    │   ├── WebServiceApplication.java     # Point d'entrée
    │   ├── model/Student.java             # Entité Student
    │   ├── repository/StudentRepository   # Accès données
    │   ├── web/services/soap/
    │   │   ├── StudentEndpoint.java       # Endpoint SOAP
    │   │   └── config/WebServiceConfig    # Configuration WSDL
    │   └── init/DataInitializer           # Chargement initial (5 étudiants)
    │
    ├── java/com/kholty/student/
    │   └── (Classes JAXB générées)        # Requêtes/Réponses SOAP
    │
    └── resources/
        ├── application.properties         # Propriétés app
        └── student.xsd                    # Schéma SOAP
```

---

## ⚙️ Prérequis

- **Java 21** ou plus récent
- **Maven Wrapper** inclus dans le projet

---

## 🚀 Installation & Lancement

### 1. Construire le projet

```bash
# Windows
.\mvnw.cmd clean install

# macOS / Linux
./mvnw clean install
```

### 2. Lancer l'application

```bash
# Windows
.\mvnw.cmd spring-boot:run

# macOS / Linux
./mvnw spring-boot:run
```

✓ Application disponible sur **http://localhost:8081**

✓ **5 étudiants** chargés automatiquement au démarrage

---

## 📡 Opérations disponibles

Le service SOAP propose **3 opérations** principales :

| Opération | Entrée | Sortie |
|---|---|---|
| **addStudentRequest** | name, email | Student (id, name, email) |
| **getStudentRequest** | id | Student ou vide |
| **getAllStudentsRequest** | — | Liste des étudiants |

---

## 📨 Exemples de requêtes

**Endpoint SOAP :** `http://localhost:8081/ws`  
**WSDL :** `http://localhost:8081/ws/student.wsdl`

### ➕ Ajouter un étudiant

**Requête :**
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

**Réponse :**
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

**Réponse (exemple) :**
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
      <!-- ... autres étudiants ... -->
    </ns2:getAllStudentsResponse>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

---

## 📊 Données initiales

Au démarrage, **5 étudiants** sont chargés automatiquement :

| ID | Nom | Email |
|---|---|---|
| 1 | Ahmed Kholty | ahmed.kholty@example.com |
| 2 | Fatima Hassan | fatima.hassan@example.com |
| 3 | Mohamed Ali | mohamed.ali@example.com |
| 4 | Leila Mansour | leila.mansour@example.com |
| 5 | Omar Saada | omar.saada@example.com |

**Fichier :** `src/main/java/com/example/demo/init/DataInitializer.java`

---

## 🔧 Configuration

### Modifier le port

Éditez `src/main/resources/application.properties` :

```properties
server.port=8081
```

### Ajouter des étudiants au démarrage

Éditez `src/main/java/com/example/demo/init/DataInitializer.java`

---

## 🌐 Accès au WSDL

**URL :** `http://localhost:8081/ws/student.wsdl`

### Utiliser avec un client SOAP

Outils recommandés : Postman, SoapUI, Wizler

1. Lancez l'application avec `spring-boot:run`
2. Ouvrez votre client SOAP
3. Cible : `http://localhost:8081/ws`
4. Collez une requête depuis les exemples ci-dessus
5. Envoyez

---

## 📝 Référence rapide

| Concept | Valeur |
|---|---|
| **Endpoint** | `http://localhost:8081/ws` |
| **WSDL** | `http://localhost:8081/ws/student.wsdl` |
| **Namespace** | `http://kholty.com/student` |
| **Port** | `8081` |
| **Base données** | MySQL |

---

## 📚 Fichiers clés

- `src/main/java/com/example/demo/WebServiceApplication.java` — Point d'entrée
- `src/main/java/com/example/demo/web/services/soap/StudentEndpoint.java` — Opérations SOAP
- `src/main/java/com/example/demo/model/Student.java` — Modèle données
- `src/main/resources/student.xsd` — Schéma XML
- `src/main/java/com/example/demo/web/services/soap/config/WebServiceConfig.java` — Configuration WSDL
