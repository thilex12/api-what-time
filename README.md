# API-What-Time

## Présentation

API-What-Time est le backend Java Spring de l’application "What time ?". Cette application permet aux utilisateurs de créer, gérer et partager des événements, privé ou public, avec des fonctionnalités avancées telles que la gestion des tags, les notifications. Ce projet est dans le cadre d'un projet d'école à l'ISEN Caen.
## Fonctionnalités principales
- Création, modification, suppression d’événements
- Gestion des tags associés aux événements
- Inscription et suivi des utilisateurs
- Notifications automatiques lors des actions importantes
- Système d’autorisation et de visibilité des événements
- API REST sécurisée avec Spring Security
- Gestion des localisations pour les événements


## Structure du projet
- `src/main/java/fr/isencaen/api_what_time/` : code source principal
    - `controller/` : endpoints REST
    - `service/` : logique api
    - `repository/` : accès aux données
    - `config/` : configuration Spring
- `src/main/resources/` : fichiers de configuration
- `src/test/java/` : tests unitaires

## Démarrage rapide
1. Cloner le projet
2. Configurer la base de données dans `application.yml`
3. Lancer l’application :
4. Accéder à l’API sur `http://localhost:8080`
5. Accéder au SWAGGER `http://localhost:8080/swagger-ui/index.html#/`

## Auteurs
- [BAYARD Jean]()
- [PEYRACHE Arnaud]()
- [GUERRIER Alexandre]()
