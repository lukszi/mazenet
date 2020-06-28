package de.fhac.mazenet.client.logic;

import de.fhac.mazenet.server.game.Board;
import de.fhac.mazenet.server.game.Position;
import de.fhac.mazenet.server.generated.*;
import de.fhac.mazenet.server.networking.XmlOutputStream;
import javafx.geometry.Pos;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Lukas Szimtenings on 6/25/2020.
 */
public class AwaitMoveHandler extends MessageHandler {
    private XmlOutputStream out;

    public AwaitMoveHandler(XmlOutputStream out) {
        this.out = out;
    }

    @Override
    public void handle(MazeCom message) {
        System.out.println("Awaiting movement");

        BoardData boardData = message.getAwaitMoveMessage().getBoard();
        Board board = new Board(boardData);
        Treasure treasureToFind = message.getAwaitMoveMessage().getTreasureToFindNext();

        MoveMessageData move = getMove(board, treasureToFind);

        MazeCom mazeCom = new MazeCom();
        mazeCom.setMessagetype(MazeComMessagetype.MOVE);
        mazeCom.setMoveMessage(move);
        try {
            out.write(mazeCom);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean treasureReachable(PositionData playerPos, PositionData treasurePos, Board board) {
        List<Position> reachable = board.getAllReachablePositions(playerPos);
        return reachable.contains(treasurePos);
    }

    private MoveMessageData getMove(Board board, Treasure treasure) {
        MoveMessageData move = null;
        List<Board> fakeBoards = createFakeBoards(board);

        for (Board fakeBoard : fakeBoards) {
            Position playerPos = fakeBoard.findPlayer(STATE.getId());
            PositionData treasurePosData = fakeBoard.findTreasure(treasure);
            Position treasurePos = new Position(treasurePosData);

            if (fakeBoard.getAllReachablePositions(playerPos).contains(treasurePos)) {
                Position forbidden = new Position(fakeBoard.getForbidden());
                Position shiftPos = forbidden.getOpposite();

                move = new MoveMessageData();
                move.setNewPinPos(treasurePos);
                move.setShiftPosition(shiftPos);
                move.setShiftCard(board.getShiftCard());
            }
        }
        if (move == null) {
            move = getRandomMove(board, treasure);
        }
        return move;
    }

    private MoveMessageData getRandomMove(Board board, Treasure treasure) {
        MoveMessageData move = new MoveMessageData();
        Random rnd = new Random();
        List<Board> fakeBoards = createFakeBoards(board);
        Position playerPos = board.findPlayer(STATE.getId());
        Board randomBoard = fakeBoards.get(rnd.nextInt(fakeBoards.size()));
        List<Position> reachable = randomBoard.getAllReachablePositions(playerPos);
        Position newPlayerPos = reachable.get(rnd.nextInt(reachable.size()));

        move.setShiftPosition(randomBoard.getForbidden());
        move.setShiftCard(board.getShiftCard());
        move.setNewPinPos(newPlayerPos);

        return move;

    }


    private List<Board> createFakeBoards(Board board){
        ArrayList<Board> fakeBoards = new ArrayList<>();
        List<Position> shifts = getAllShifts(board);
        Position playerPos = board.findPlayer(STATE.getId());

        for (Position shift : shifts){
            MoveMessageData fakeMove = new MoveMessageData();
            fakeMove.setShiftPosition(shift);
            fakeMove.setShiftCard(board.getShiftCard());
            fakeMove.setNewPinPos(playerPos);

            Board fakeBoard = board.fakeShift(fakeMove);
            fakeBoards.add(fakeBoard);
        }

        return fakeBoards;
    }

    private Board createFakeBoard(Board board, Position shiftPos) {
        MoveMessageData move = new MoveMessageData();
        move.setShiftPosition(shiftPos);
        board.fakeShift(move);
        return board;
    }

    private List<Position> getAllShifts(Board board) {
        ArrayList<Position> shiftPositions = new ArrayList<>();
        for (int col = 0; col < 6; col++) {
            for (int row = 0; row < 6; row++) {
                Position position = new Position(row, col);
                if (!(position == board.getForbidden())) {
                    if (position.getCol() % 6 == 0) {
                        if (position.getRow() % 2 == 0) {
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
        Position pos = (Position) boardData.getForbidden();
        return pos.getOpposite();
    }

}
