package Controller;

import Enums.Color;
import Services.MoveValidator;
import model.Board;
import model.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;

public class GameController {
    private Board board;
    private MoveValidator moveValidator;
    int whiteCaptureCount;
    int blackCaptureCount;

    public GameController(Board board, MoveValidator moveValidator){
        this.board = board;
        this.moveValidator = moveValidator;
        this.whiteCaptureCount = 0;
        this.blackCaptureCount = 0;
    }

    public void mainController(String move, Color color){
        ArrayList<Coordinate> coordinates = convertedMovesToBoardCoordinates(move);
        int fromRow = coordinates.get(0).getRow();
        int fromColumn = coordinates.get(0).getColumn();

        for (int i = 0; i < coordinates.size(); i++){
            fromRow = coordinates.get(0).getRow();
            fromColumn = coordinates.get(0).getColumn();
        }
    }

    //Is stone moving correctly depending on the stone type
    public boolean isMoveValid(int fromRow, int fromColumn, ArrayList<Coordinate> coordinates, Color color) throws Exception {
        boolean status = true;
        if (!board.geStone(fromRow, fromColumn).isKing()){
            for (int i = 1; i < coordinates.size(); i++){
                int rowResult = Math.abs(coordinates.get(i).getRow() - fromRow);
                int columnResult = Math.abs(coordinates.get(i).getColumn() - fromColumn);
                if (rowResult != columnResult){
                    status = false;
                }
            }
        } else{
            //checking kings moves
        }
        return status;
    }


    //moveStone
    public void moveStone(ArrayList<Coordinate> coordinates, Color color) throws Exception {
        if (moveValidator.isToSquareEmpty(coordinates)){
            board.removeStone(coordinates.get(0).getRow(), coordinates.get(0).getColumn());
            board.placeStone(coordinates.get(coordinates.size()-1).getRow(),coordinates.get(coordinates.size()-1).getColumn(), color);
            isItKingNow(coordinates, color);
        }
    }

    public boolean isItKingNow(ArrayList<Coordinate> coordinates, Color color) throws Exception {
        int lastRow = coordinates.get(coordinates.size()-1).getRow();
        int lastColumn = coordinates.get(coordinates.size()-1).getColumn();

        if (color == Color.White) {
            if ((lastRow == 1 || lastRow == 3 || lastRow == 5 || lastRow == 7) && lastColumn == 0){
                board.makeStoneKing(lastRow, lastColumn);
            }
            return true;
        }

        if (color == Color.Black) {
            if ((lastRow == 1 || lastRow == 3 || lastRow == 5 || lastRow == 7) && lastColumn == 7){
                board.makeStoneKing(lastRow, lastColumn);
            }
            return true;
        }
        return false;
    }

    //Capture Stone
    public void captureStone(int row, int column, Color color) throws Exception {
        if (color == Color.White){
            board.removeStone(row, column);
            whiteCaptureCount++;
        } else {
            board.removeStone(row, column);
            blackCaptureCount++;
        }
    }

    public ArrayList<Coordinate> convertedMovesToBoardCoordinates(String move){
        HashMap<String, Integer> columnCoordinates = new HashMap<>();
        columnCoordinates.put("A", 0);
        columnCoordinates.put("B", 1);
        columnCoordinates.put("C", 2);
        columnCoordinates.put("D", 3);
        columnCoordinates.put("E", 4);
        columnCoordinates.put("F", 5);
        columnCoordinates.put("G", 6);
        columnCoordinates.put("H", 7);

        String [] moveStringSplit = move.trim().replace(" ", "").split("->");
        ArrayList<Coordinate> convertedMoves = new ArrayList<>(moveStringSplit.length);

        for (String s : moveStringSplit){
            // adding row and column converted coordinates
            convertedMoves.add(new Coordinate(8 - Integer.parseInt(s.substring(1)),columnCoordinates.get(s.substring(0,1))));
        }
        return convertedMoves;
    }
}
