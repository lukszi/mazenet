package de.fhac.mazenet.client.logic;

import de.fhac.mazenet.server.game.Board;
import de.fhac.mazenet.server.game.Position;
import de.fhac.mazenet.server.generated.*;
import de.fhac.mazenet.server.networking.XmlOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    	Position shiftpos =new Position(0, 1);
    	Position playerpos=new Position(5, 5);
    	
    	
        System.out.println("Awaiting movement");
        BoardData boardData = message.getAwaitMoveMessage().getBoard();
        Board board = new Board(boardData);
        List<Board> boards = createFakeBoards(board);
        
        MoveMessageData mv = getMove(board);
        if(!(shiftpos.isLooseShiftPosition())&& getInverseMove(boardData).equals(shiftpos)) {
        	//fehler
        }else {
        	
            MoveMessageData move = new MoveMessageData();
            move.setShiftCard(boardData.getShiftCard());
            move.setNewPinPos(playerpos);
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
    
    private MoveMessageData getMove(Board originalBoard)
    {
        List<Board> boards = createFakeBoards(originalBoard);
        boards.add((Board)originalBoard.clone());
    
        Map<Board, List<Position>> reachablePositions = boards.stream()
                .collect(
                        Collectors.toMap(
                                board -> board,
                                board -> board.getAllReachablePositions(board.findPlayer(STATE.getId()))));
        
        return null;
    }
    
    private List<Board> createFakeBoards(Board board)
    {
        // Get all allowed shifts
        ArrayList<MoveMessageData> allowedShifts = getAllAllowedShifts(board).stream()
                .map(this::createMoveMessageData)
                .collect(Collectors.toCollection(ArrayList::new));
        
        // Apply all allowed shifts
        return allowedShifts.stream()
                .map(board::fakeShift)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    private MoveMessageData createMoveMessageData(Position allowedShift)
    {
        return null;
    }
    
    private List<Position> getAllAllowedShifts(Board board)
    {
        return null;
    }
    
    private Position getInverseMove(BoardData boardData) {
    	Position pos=(Position)boardData.getForbidden();
    	return pos.getOpposite();
    }

}
