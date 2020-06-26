package de.fhac.mazenet.client.logic;

import de.fhac.mazenet.server.generated.*;
import de.fhac.mazenet.server.networking.MazeComMessageFactory;
import de.fhac.mazenet.server.networking.XmlOutputStream;

import java.io.IOException;

/**
 * Created by Lukas Szimtenings on 6/25/2020.
 */
public class AwaitMoveHandler extends MessageHandler
{
    private XmlOutputStream out;

    public AwaitMoveHandler(XmlOutputStream out) {
        this.out = out;
    }

    @Override
    public void handle(MazeCom message)
    {
        //TODO: Implement
        System.out.println("Awaiting movement");
        BoardData boardData = message.getAwaitMoveMessage().getBoard();
        STATE.setBoardData(boardData);
        MoveMessageData move = new MoveMessageData();
        move.setShiftCard(boardData.getShiftCard());
        move.setNewPinPos(newPosition(5, 5));
        move.setShiftPosition(newPosition(0, 1));
        MazeCom mazeCom = new MazeCom();
        mazeCom.setMessagetype(MazeComMessagetype.MOVE);
        mazeCom.setMoveMessage(move);
        try {
            out.write(mazeCom);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PositionData newPosition(int col, int row){
        PositionData ret = new PositionData();
        ret.setCol(col);
        ret.setRow(row);
        return ret;
    }
}
