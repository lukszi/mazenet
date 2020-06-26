package de.fhac.mazenet.client.logic;
import de.fhac.mazenet.server.generated.MazeCom;

public class MoveInfoHandler extends MessageHandler {
    @Override
    public void handle(MazeCom message) {
        STATE.boardData = message.getMoveInfoMessage().getBoardAfterMove();
    }
}
