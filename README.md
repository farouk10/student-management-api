# API de Gestion des Étudiants - API REST Spring Boot

Une application Spring Boot complète offrant à la fois une API REST et une interface web pour la gestion des étudiants avec authentification JWT.

## 🚀 Fonctionnalités

- **Opérations CRUD Complètes** - Créer, Lire, Mettre à jour, Supprimer des étudiants
- **Authentification JWT** - Authentification sécurisée sans état
- **Autorisation Basée sur les Rôles** - Rôles ADMIN et USER
- **Double Support API** - API REST avec pattern DTO + API legacy
- **Interface Web** - Interface utilisateur basée sur Thymeleaf
- **Validation des Données** - Validation complète des entrées avec Bean Validation
- **Intégration MapStruct** - Utilitaires de mapping d'objets
- **Formats de Données Multiples** - Réponses API JSON et vues HTML
- **Gestion des Erreurs** - Pages d'erreur personnalisées et messages de validation
- **Sécurité** - Protection CSRF, chiffrement des mots de passe, configuration CORS

## 🛠️ Technologies

- **Backend**: Spring Boot 3.5.6, Spring Security 6, Spring Data JPA, Spring MVC
- **Authentification**: JWT (JSON Web Tokens)
- **Base de Données**: H2 (Développement), MySQL prêt
- **Templates**: Thymeleaf avec intégration Spring Security
- **Mapping d'Objets**: MapStruct
- **Validation**: Jakarta Bean Validation
- **Outil de Build**: Maven
- **Version Java**: 25

## 📚 Structure du Projet
```
src/
├── controller/                         # Contrôleurs REST & MVC
│   ├── AuthController.java             # Endpoints d'authentification JWT
│   ├── EtudiantController.java         # API REST legacy
│   ├── EtudiantController_mapper.java  # API REST basée sur DTO
│   └── EtudiantController_th.java      # Interface web Thymeleaf
├── entity/                             # Entités JPA
│   ├── Etudiant.java                   # Entité Étudiant
│   └── User.java                       # Entité Utilisateur pour l'authentification
├── repository/                         # Repositories Spring Data
├── service/                            # Couche logique métier
├── dto/                                # Data Transfer Objects
├── security/                           # Configuration JWT & filtres
├── mapper/                             # Mappers d'objets MapStruct
└── resources/
    ├── templates/                      # Templates Thymeleaf
    └── application.properties
```

## 🚦 Démarrage

### Prérequis

- Java 25 ou supérieur
- Maven 3.9+
- MySQL 8.0 (optionnel, pour la production)

### Installation

1. Cloner le dépôt
```bash
git clone https://github.com/votrenomdutilisateur/student-management-api.git
cd student-management-api
```

2. Configurer l'application (optionnel)
```bash
# Copier et modifier les propriétés de l'application si nécessaire
cp src/main/resources/application.properties src/main/resources/application-dev.properties
```

3. Compiler le projet
```bash
mvn clean install
```

4. Lancer l'application
```bash
mvn spring-boot:run
```

L'application démarrera sur `http://localhost:8080`

### Utilisateurs Par Défaut

L'application crée des utilisateurs par défaut au démarrage :

- **Admin**: `admin` / `admin123` (ROLE_ADMIN)
- **Utilisateur**: `user` / `user123` (ROLE_USER)

## 🔐 Authentification

### Inscription d'un nouvel utilisateur
```http
POST /api/auth/signup
Content-Type: application/json

{
  "username": "nouveautilisateur",
  "email": "user@example.com",
  "password": "password123",
  "role": "ROLE_USER"
}
```

### Connexion
```http
POST /api/auth/signin
Content-Type: application/json

{
  "username": "user",
  "password": "password123"
}
```

**Réponse:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "user",
  "email": "user@example.com",
  "role": "ROLE_USER"
}
```

## 📋 Points de Terminaison de l'API

### 🔐 Endpoints d'Authentification

| Méthode | Endpoint | Description | Accès |
|---------|----------|-------------|-------|
| POST | `/api/auth/signin` | Connexion utilisateur | Public |
| POST | `/api/auth/signup` | Inscription utilisateur | Public |

### 🎓 Endpoints de Gestion des Étudiants (API basée sur DTO)

| Méthode | Endpoint | Description | Accès |
|---------|----------|-------------|-------|
| GET | `/api/etudiants` | Obtenir tous les étudiants (avec mapping DTO) | ADMIN, USER |
| GET | `/api/etudiants/{id}` | Obtenir un étudiant par ID (avec mapping DTO) | ADMIN, USER |
| POST | `/api/etudiants` | Créer un nouvel étudiant | ADMIN |
| PUT | `/api/etudiants/{id}` | Mettre à jour un étudiant | ADMIN |
| DELETE | `/api/etudiants/{id}` | Supprimer un étudiant | ADMIN |

### 🔄 Endpoints Étudiants Legacy

| Méthode | Endpoint | Description | Accès |
|---------|----------|-------------|-------|
| GET | `/api-1/etudiants/list` | Obtenir tous les étudiants (entité directe) | ADMIN, USER |
| GET | `/api-1/etudiants/{id}` | Obtenir un étudiant par ID (entité directe) | ADMIN, USER |
| POST | `/api-1/etudiants` | Créer un étudiant (entité directe) | ADMIN |
| PUT | `/api-1/etudiants/{id}` | Mettre à jour un étudiant (entité directe) | ADMIN |
| DELETE | `/api-1/etudiants/{id}` | Supprimer un étudiant (entité directe) | ADMIN |

### 🌐 Endpoints de l'Interface Web

| Méthode | Endpoint | Description | Accès |
|---------|----------|-------------|-------|
| GET | `/` | Page d'accueil | Public |
| GET | `/etudiants/list` | Page liste des étudiants | ADMIN, USER |
| GET | `/etudiants/ajout` | Formulaire d'ajout d'étudiant | ADMIN |
| POST | `/etudiants/save` | Enregistrer un étudiant | ADMIN |
| GET | `/etudiants/modifier/{id}` | Formulaire de modification | ADMIN |
| POST | `/etudiants/update/{id}` | Mettre à jour un étudiant | ADMIN |
| GET | `/etudiants/delete/{id}` | Supprimer un étudiant | ADMIN |
| GET | `/etudiants/details/{id}` | Page détails de l'étudiant | ADMIN, USER |
| GET | `/login` | Page de connexion | Public |
| GET | `/dashboard` | Tableau de bord admin | ADMIN |

## 📊 Structure de l'Entité Étudiant
```json
{
  "id": Long,
  "nom": String,        // @NotBlank
  "prenom": String,     // @NotBlank
  "email": String,      // @Email, @NotBlank
  "age": Integer        // @Min(18), @Max(100), @NotNull
}
```

## 🔧 Configuration

### Propriétés de l'Application
```properties
# Configuration JWT
security.jwt.secret-key=votre-cle-secrete-256-bits
security.jwt.expiration-time=86400000

# Base de données H2 (Développement)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true

# Configuration JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

### Configuration MySQL (Production)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=votre_nom_utilisateur
spring.datasource.password=votre_mot_de_passe
spring.jpa.hibernate.ddl-auto=update
```

## 🗄️ Accès à la Base de Données

### Console H2 (Développement)

Accédez à la console H2 à :
```
http://localhost:8080/h2-console
```

- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Nom d'utilisateur**: `sa`
- **Mot de passe**: (laisser vide)

## 🧪 Test avec Postman

### 1. Flux d'Authentification

1. **Inscription**: POST vers `/api/auth/signup`
2. **Connexion**: POST vers `/api/auth/signin` (obtenir le token JWT)
3. **Accès aux Endpoints Protégés**: Inclure l'en-tête `Authorization: Bearer <token>`

### 2. Exemples d'Appels API

**Créer un Étudiant:**
```http
POST /api/etudiants
Authorization: Bearer votre_token_jwt
Content-Type: application/json

{
  "nom": "Dupont",
  "prenom": "Jean",
  "email": "jean.dupont@email.com",
  "age": 22
}
```

**Obtenir Tous les Étudiants:**
```http
GET /api/etudiants
Authorization: Bearer votre_token_jwt
```

## 🛡️ Fonctionnalités de Sécurité

- ✅ Authentification JWT sans état
- ✅ Chiffrement des mots de passe avec BCrypt
- ✅ Contrôle d'accès basé sur les rôles (ADMIN vs USER)
- ✅ Protection CSRF pour les endpoints web
- ✅ Configuration CORS pour les endpoints API
- ✅ Validation des entrées avec messages d'erreur personnalisés
- ✅ Gestion de session avec politique STATELESS

## 📝 Règles de Validation

- **Nom/Prénom**: Non vide
- **Email**: Format email valide, non vide
- **Âge**: Entre 18 et 100 ans, non null
- **Nom d'utilisateur**: Unique, non vide
- **Mot de passe**: Chiffré avec BCrypt

## 🎯 Exemples d'Utilisation

### Utilisation de l'Interface Web

1. Accédez à `http://localhost:8080`
2. Connectez-vous avec les identifiants admin
3. Accédez aux fonctionnalités de gestion des étudiants via l'interface

### Utilisation de l'API REST
```bash
# Obtenir le token JWT
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Utiliser le token pour accéder aux endpoints
curl -X GET http://localhost:8080/api/etudiants \
  -H "Authorization: Bearer VOTRE_TOKEN_JWT"
```

## 🐛 Dépannage

### Problèmes Courants

- **Échec de l'authentification**: Vérifiez le nom d'utilisateur/mot de passe et les rôles utilisateur
- **Accès refusé**: Assurez-vous que l'utilisateur a le rôle requis (ADMIN pour les opérations d'écriture)
- **Erreurs de validation**: Vérifiez le corps de la requête par rapport aux règles de validation
- **Problèmes de base de données**: Vérifiez l'accès à la console H2 pour le développement

### Logs

Activez les logs de débogage dans `application.properties`:
```properties
logging.level.hightech.edu.inscription=DEBUG
logging.level.org.springframework.security=DEBUG
```

## 🤝 Contribution

1. Forkez le projet
2. Créez votre branche de fonctionnalité (`git checkout -b feature/NouvelleFonctionnalite`)
3. Committez vos changements (`git commit -m 'Ajout d'une nouvelle fonctionnalité'`)
4. Poussez vers la branche (`git push origin feature/NouvelleFonctionnalite`)
5. Ouvrez une Pull Request

## 📝 Licence

Ce projet est sous licence MIT - voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 👤 Auteur

Votre Nom - [GitHub](https://github.com/votrenomdutilisateur)

---

**Note**: Ce projet démontre le développement Spring Boot de niveau entreprise avec les meilleures pratiques de sécurité, plusieurs versions d'API, et des interfaces REST et web.
