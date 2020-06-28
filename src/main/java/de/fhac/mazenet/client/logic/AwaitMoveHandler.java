package de.fhac.mazenet.client.logic;

import de.fhac.mazenet.server.game.Board;
import de.fhac.mazenet.server.game.Position;
import de.fhac.mazenet.server.generated.*;
import de.fhac.mazenet.server.networking.XmlOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        Treasure toFindTreasure = message.getAwaitMoveMessage().getTreasureToFindNext();
        Board board = new Board(boardData);
        
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

    private boolean treasureReachable(PositionData playerPos, PositionData treasurePos, Board board) {
        List<Position> reachable = board.getAllReachablePositions(playerPos);
        return reachable.contains(treasurePos);
    }
    
    private MoveMessageData getMove(Board originalBoard)
    {
        Map<Position, Board> boards = createFakeBoards(originalBoard);
        
        return null;
    }
    
    private Map<Position, Board> createFakeBoards(Board board)
    {
        // Get all allowed shifts
        ArrayList<Position> allowedShifts = new ArrayList<>(getAllAllowedShifts(board));
        
        // Apply all allowed shifts
        return allowedShifts.stream()
                .collect(Collectors.toMap(shiftPos -> shiftPos, shiftPos -> createFakeBoard(board, shiftPos)));
    }
    
    private Board createFakeBoard(Board board, Position shiftPos)
    {
        MoveMessageData move = new MoveMessageData();
        move.setShiftPosition(shiftPos);
        board.fakeShift(move);
        return board;
    }
    
    private List<Position> getAllAllowedShifts(Board board)
    {
        List<Position> allShifts = getAllShifts(board);
        return allShifts.stream()
                .filter(position -> position.equals(board.getForbidden()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    private List<Position> getAllShifts(Board board){
        ArrayList<Position> shiftPositions = new ArrayList<>();
        for(int col = 0; col < 6; col++){
            for(int row = 0; row < 6; row++){
                Position position = new Position(row, col);
                if (!(position == board.getForbidden())){
                    if (position.getCol() % 6 == 0) {
                        if (position.getRow() % 2 == 0){
                            shiftPositions.add(position);
                        }
                    } else if (position.getRow() % 6 == 0) {
                        if (position.getCol() % 2 == 0) {
                            shiftPositions.add(position);
                        }
                    }
                }
            }
        }
        return shiftPositions;
    }
    
    private Position getInverseMove(BoardData boardData) {
    	Position pos=(Position)boardData.getForbidden();
    	return pos.getOpposite();
    }

}
