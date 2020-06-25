# TODO
* MessageHandler schreiben für
    * LoginMessage
    * LoginReplyMessage
    * AwaitMoveMessage
    * MoveMessage
    * MoveInfoMessage
    * GameStatusMessage
    * ControlServerMessage
    * AcceptMessage
    * WinMessage
    * DisconnectMessage
# Run

* IDE
* `mvn clean compile exec:java [-Dexec.args="--help"]`
* `mvn clean compile assembly:single` -> `java -jar target/Client-0.0.1-SNAPSHOT.jar [--help]`
