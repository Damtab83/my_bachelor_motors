# M-MOTORS

Ce projet est une application créée dans le cadre d'un **Bachelor - Développement d'une solution digitale en Java**.
Cette application a été faite sur **Spring Boot**, construite avec **Maven** et conçue pour fonctionner avec **Java 21**.

---

## Prérequis

Avant de lancer le projet, assurez-vous d’avoir installé :

- **Java Development Kit (JDK) 21**  
  ➤ [Télécharger JDK 21](https://www.graalvm.org/downloads/)

- **Apache Maven 3.9 ou supérieur**  
  ➤ [Télécharger Maven](https://maven.apache.org/download.cgi)

- **Docker**  
  ➤ [Télécharger Docker](https://www.docker.com/products/docker-desktop/)

- Un IDE comme **IntelliJ IDEA**, **Eclipse** ou **Visual Studio Code** (optionnel mais recommandé)

Vérifiez vos versions avec les commandes suivantes :

```bash
java -version
mvn -version
```

## Demarrage du projet

- Clonez le repository
```bash
git clone https://github.com/Damtab83/my_bachelor_motors.git
cd my_bachelor_motors
```

- Demarrez la database en local via docker-compose
```bash
docker-compose up -d
```

- Lancez l'application
```bash
mvn spring-boot:run
```

## Utilisation

Par défaut, l'application sera disponible à l'adresse : http://localhost:8080/api
Cette application n'est qu'une partie de l'application globale.

Vous retrouverez les repositories sur ces 2 liens:

- **Backend**  
  ➤ [Repository Back](https://github.com/Damtab83/my_bachelor_motors)

- **Frontend**  
  ➤ [Repository Front](https://github.com/Damtab83/m_motors_front)