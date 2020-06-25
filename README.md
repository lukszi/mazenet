# TODO

* `Client.java`
  * Refactoring in Teamnamen
  * Teammitglieder in Javadoc eintragen
* `pom.xml`
  * `artifactId` anpassen
  * Die Mainklasse `<main.class>` umbenennen
  * Serverversion der `dependecies` anpassen
* `.gitignore` kontrollieren

# Run

* IDE
* `mvn clean compile exec:java [-Dexec.args="--help"]`
* `mvn clean compile assembly:single` -> `java -jar target/Client-0.0.1-SNAPSHOT.jar [--help]`
