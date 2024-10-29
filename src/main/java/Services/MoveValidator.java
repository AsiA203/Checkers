package Services;

import model.Board;
import model.Coordinate;
import model.Square;

import java.util.ArrayList;

public class MoveValidator {
    private Square[][] board;

    public MoveValidator(Board board){
        this.board = board.getBoard();
    }

    public boolean isMoveDiagonal(ArrayList<Coordinate> coordinates){
        boolean status = true;
        for (int i = 1; i < coordinates.size(); i++){
            if (!board[])
        }
    }
    public boolean isToSquareEmpty(ArrayList<Coordinate> coordinates){
        boolean isEmpty = true;
        for (int i = 1; i < coordinates.size();i++){
            if (!board[coordinates.get(i).getRow()][coordinates.get(i).getRow()].isSquareEmpty()){
                isEmpty = false;
            }
        }
        return isEmpty;
    }
}
