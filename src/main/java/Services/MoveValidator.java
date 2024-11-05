package Services;

import Enums.Color;
import Enums.MoveResult;
import model.Board;
import model.Coordinate;
import model.Square;


public class MoveValidator {
    private Square[][] board;

    public MoveValidator(Board board) {
        this.board = board.getBoard();
    }

    //is move(any) possible
    public MoveResult isMovePossible(Coordinate from, Coordinate to, Color color) {
        boolean checkFromBoundaries = (from.getRow() >= 0 && from.getRow() < 8) && (from.getColumn() >= 0 && from.getColumn() < 8);
        boolean checkToBoundaries = (to.getRow() >= 0 && to.getRow() < 8) && (to.getColumn() >= 0 && to.getColumn() < 8);

        //checking boundaries
        if (!checkFromBoundaries || !checkToBoundaries) {
            return MoveResult.MOVE_OUT_OF_BOUNDS;
        }

        //checking if move is valid
        if (!isMoveValid(from, to, color)) {
            return MoveResult.MOVE_INVALID;
        }

        //checking if "to" square is not occupied
        if (isSquareOccupied(to)) {
            return MoveResult.SQUARE_OCCUPIED;
        }


        if (Math.abs(to.getRow() - from.getRow()) == 1 && Math.abs(to.getColumn() - from.getColumn()) == 1) { //if just move
            return MoveResult.MOVE_VALID;
        } else if (Math.abs(to.getRow() - from.getRow()) == 2 && Math.abs(to.getColumn() - from.getColumn()) == 2) { //if capture
            //checking if there's a square and only one square in between FROM and TO
            int middleRow = (from.getRow() + to.getRow()) / 2;
            int middleCol = (from.getColumn() + to.getColumn()) / 2;
            if (board[middleRow][middleCol].isSquareEmpty() || board[middleRow][middleCol].getSquare().getColor() == color) {
                return MoveResult.MOVE_INVALID;
            }


            if (!isOneMoreMove(to, color)) {
                return MoveResult.CAPTURE;
            }

            //if all checks are passed
            return MoveResult.EXTRA_MOVE_REQUIRED;
        }
        return MoveResult.MOVE_INVALID; //if it passes first three checks, but it's not a move or capture -> assuming that move is invalid (for now)
    }


    //check if there are any more moves for whites and blacks
    public MoveResult isThereAnyMorePossibleMovesOnBoard(Color color) throws Exception {
        if (color == Color.WHITE) {
            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    Square square = board[row][column];
                    if (!square.isSquareEmpty() && square.getSquare().getColor() == color) {
                        Coordinate from = new Coordinate(row, column);
                        //TODO
                    }
                }
            }
        }
        return null; //TODO
    }


    //is square occupied
    public boolean isSquareOccupied(Coordinate to) {
        //not empty = false
        //empty = true
        return !board[to.getRow()][to.getColumn()].isSquareEmpty();
    }

    //check if one more move is possible - Regular stones
    public boolean isOneMoreMove(Coordinate nowFrom, Color color) { // Coordinate "nowFrom" = Coordinate "to" from isCapturePossible();
        boolean status = false;

        Square oneMoreRight;
        Square oneMoreLeft;
        if (color == Color.WHITE) { //whites
            if (nowFrom.getColumn() + 1 > 8 || nowFrom.getColumn() - 1 < 0) {
                return false;
            } else {
                oneMoreRight = board[nowFrom.getRow() - 1][nowFrom.getColumn() + 1];
                oneMoreLeft = board[nowFrom.getRow() - 1][nowFrom.getColumn() - 1];
            }

        } else { //blacks
            if (nowFrom.getColumn() - 1 < 0 || nowFrom.getColumn() + 1 > 8) {
                return false;
            } else {
                oneMoreRight = board[nowFrom.getRow() + 1][nowFrom.getColumn() + 1];
                oneMoreLeft = board[nowFrom.getRow() + 1][nowFrom.getColumn() - 1];
            }
        }

        if ((!oneMoreRight.isSquareEmpty() && oneMoreRight.getSquare().getColor() != color)) {
            status = true;
        } else status = false;

//        else if (!oneMoreLeft.isSquareEmpty() && oneMoreLeft.getSquare().getColor() != color) {
//            status = true;
//        }
        return status;
    }


    //Is stone moving correctly depending on the stone type
    public boolean isMoveValid(Coordinate from, Coordinate to, Color color) {
        int rowResult = Math.abs(to.getRow() - from.getRow());
        int columnResult = Math.abs(to.getColumn() - from.getColumn());

        if (!board[from.getRow()][from.getColumn()].getSquare().isKing()) { // For regular stones
            if (rowResult == 1 && columnResult == 1) {
                if (color == Color.WHITE && (from.getRow() > to.getRow())) { // White can't move down
                    return true;
                } else if (color == Color.BLACK && (from.getRow() < to.getRow())) {
                    return true; // Black can't move up
                }

            } else if (rowResult == 2 && columnResult == 2) { // For capture moves
                int middleRow = (from.getRow() + to.getRow()) / 2;
                int middleColumn = (from.getColumn() + to.getColumn()) / 2;
                Square middleSquare = board[middleRow][middleColumn];

                return !middleSquare.isSquareEmpty() && middleSquare.getSquare().getColor() != color;

            }
        }

        //if stone is king
        if (board[from.getRow()][from.getColumn()].getSquare().isKing()) {
            return rowResult == columnResult;

        }

        return false;
    }

    public boolean isToSquareEmpty(Coordinate to) {
        return board[to.getRow()][to.getColumn()].isSquareEmpty();
    }
}