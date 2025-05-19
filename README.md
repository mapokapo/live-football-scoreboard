# Live Football World Cup Scoreboard

This is a simple Java library project which supports management and display of live football match scores on a scoreboard.

This project is for demonstration purposes and does not contain any real or live data.

## Features

- Create teams
- Create matches
- Start and finish matches
- Update scores
- Display scoreboard summary

## Technologies Used

- Java 21
- Maven
- JUnit 5
- Lombok

## Design principles applied

- SOLID principles
- DRY (excluding testing code)
- DI
- Repository pattern
- Facade pattern (Scoreboard -> Match + Team)
- TDD

## How to run

1. Clone the repository
2. Navigate to the project directory
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```
4. Run the tests using Maven (optional):
   ```bash
   mvn test
   ```
5. Run the application using Maven:
   ```bash
   mvn exec:java -Dexec.mainClass="me.mapokapo.App"
   ```

## Documentation

All code is documented using JavaDoc and the generated documentation can be found (here)[https://mapokapo.github.io/live-football-scoreboard/javadoc/]
