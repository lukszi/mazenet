package de.fhac.mazenet.client.logic;

import de.fhac.mazenet.server.game.Position;
import de.fhac.mazenet.server.generated.*;
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
        //TODO: Implement Positionen holen und Karten rotation
    	Position shiftpos=new Position(0, 1);
    	Position figurpos=new Position(5, 5);
    	
    	//
        System.out.println("Awaiting movement");
        BoardData boardData = message.getAwaitMoveMessage().getBoard();
        if(getOppositemove(boardData).equals(shiftpos)&&shiftpos.isLooseShiftPosition()) {
        	//Fehlermeldung
        }else {
        	MoveMessageData move = new MoveMessageData();  
            move.setShiftCard(boardData.getShiftCard());
            move.setNewPinPos(figurpos);
            move.setShiftPosition(shiftpos);       
            boardData.setForbidden(shiftpos);
            STATE.setBoardData(boardData);
            MazeCom mazeCom = new MazeCom();
            mazeCom.setMessagetype(MazeComMessagetype.MOVE);
            mazeCom.setMoveMessage(move);
            try {
                out.write(mazeCom);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }   
    }
    
    private Position getOppositemove(BoardData boardData) {
    	Position boardDatapos =(Position) boardData.getForbidden();
    	Position gegenueberpos=boardDatapos.getOpposite();
    	return gegenueberpos;
    }
}
