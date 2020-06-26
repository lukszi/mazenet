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

* Awaitmovehandler methode nach externer position frage
* MessageHandler auf interface
* Interne States
* Threading
 
 KI
# Run

* IDE
* `mvn clean compile exec:java [-Dexec.args="--help"]`
* `mvn clean compile assembly:single` -> `java -jar target/Client-0.0.1-SNAPSHOT.jar [--help]`
