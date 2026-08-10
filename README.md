# MediaCentre-API
Service ENT de gestion des ressources numériques éducatives fournies principalement par le GAR, voir plus.

[![Coverage](https://raw.githubusercontent.com/GIP-RECIA/MediaCentre-API/badges/jacoco.svg)](https://github.com/GIP-RECIA/MediaCentre-API/actions/workflows/badges.yml)
[![Branches](https://raw.githubusercontent.com/GIP-RECIA/MediaCentre-API/badges/branches.svg)](https://github.com/GIP-RECIA/MediaCentre-API/actions/workflows/badges.yml)



### To run with external configuration

- `mvn spring-boot:run   -Dspring-boot.run.arguments="--spring.profiles.active=local --spring.config.additional-location=optional:file:${PATH_PROPERTIES}/MediaCentre/"`
