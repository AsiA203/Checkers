package Controller;

import Enums.Color;
import Enums.MoveResult;
import Services.MoveValidator;
import model.Board;
import model.Coordinate;

import java.util.HashMap;

public class GameController {
    private Board board;
    private MoveValidator moveValidator;
    private int whiteCaptureCount;
    private int blackCaptureCount;

    public GameController(Board board, MoveValidator moveValidator) {
        this.board = board;
        this.moveValidator = moveValidator;
        this.whiteCaptureCount = 0;
        this.blackCaptureCount = 0;
    }

    public int getWhiteCaptureCount() {
        return whiteCaptureCount;
    }

    public int getBlackCaptureCount() {
        return blackCaptureCount;
    }

    //main method that coordinates all moves.
    // IMPORTANT: Always coordinates "FROM" goes first, if both coordinates are needed
    public MoveResult mainController(String move, Color color) throws Exception {
        move = move.trim().replace(" ", "");
        if (move.length() == 6 && move.contains("->")) {
            Coordinate[] coordinates = convertedMovesToBoardCoordinates(move);

            // Check if coordinates is null before accessing its elements
            if (coordinates == null || coordinates.length < 2) {
                return MoveResult.INPUT_FORMAT_ERROR; // Handle the error case
            }

            Coordinate coordinateFrom = coordinates[0];
            Coordinate coordinateTo = coordinates[1];
            Coordinate coordinatePossibleCapture = possibleCaptureCoordinates(coordinateFrom, coordinateTo);

            MoveResult result = moveValidator.isMovePossible(coordinateFrom, coordinateTo, color);
            switch (result) {
                case MOVE_OUT_OF_BOUNDS:
                    return MoveResult.MOVE_OUT_OF_BOUNDS;

                case SQUARE_OCCUPIED:
                    return MoveResult.SQUARE_OCCUPIED;

                case MOVE_VALID: {
                    moveStone(coordinateFrom, coordinateTo, color);
                    isItKingNow(coordinateTo, color);
                    return MoveResult.MOVE_VALID;
                }

                case CAPTURE: {
                    captureStone(coordinatePossibleCapture.getRow(), coordinatePossibleCapture.getColumn(), color);
                    placeStoneAfterCapture(coordinateFrom, coordinateTo, color);
                    isItKingNow(coordinateTo, color);
                    return MoveResult.CAPTURE;
                }

                case EXTRA_MOVE_REQUIRED: {
                    captureStone(coordinatePossibleCapture.getRow(), coordinatePossibleCapture.getColumn(), color);
                    placeStoneAfterCapture(coordinateFrom, coordinateTo, color);
                    isItKingNow(coordinateTo, color);
                    return MoveResult.EXTRA_MOVE_REQUIRED;
                }

                case MOVE_INVALID:
                    return MoveResult.MOVE_INVALID;
            }
        }
        return MoveResult.INPUT_FORMAT_ERROR;
    }

    //place stone after capture function is successful
    private void placeStoneAfterCapture(Coordinate from, Coordinate to, Color color) throws Exception {
        board.removeStone(from.getRow(), from.getColumn());
        board.placeStone(to.getRow(), to.getColumn(), color);
    }

    //moveStone
    private MoveResult moveStone(Coordinate from, Coordinate to, Color color) throws Exception {
        boolean isMoveValid = moveValidator.isMoveValid(from, to, color);
        boolean isSquareEmpty = moveValidator.isToSquareEmpty(to);
        if (isMoveValid && isSquareEmpty) {
            board.removeStone(from.getRow(), from.getColumn());
            board.placeStone(to.getRow(), to.getColumn(), color);
            isItKingNow(to, color);
        } else if (!isMoveValid) {
            return MoveResult.MOVE_INVALID;
        }
        return MoveResult.SQUARE_OCCUPIED;
    }

    private boolean isItKingNow(Coordinate to, Color color) throws Exception {
        if (color == Color.WHITE) {
            if ((to.getRow() == 1 || to.getRow() == 3 || to.getRow() == 5 || to.getRow() == 7) && to.getColumn() == 0) {
                board.makeStoneKing(to.getRow(), to.getColumn());
            }

            return true;
        } else if (color == Color.BLACK) {
            if ((to.getRow() == 1 || to.getRow() == 3 || to.getRow() == 5 || to.getRow() == 7) && to.getColumn() == 7) {
                board.makeStoneKing(to.getRow(), to.getColumn());
            }

            return true;
        }
        return false;
    }

    //Capture Stone and increase counters
    private void captureStone(int row, int column, Color color) throws Exception {
        if (color == Color.WHITE) {
            if (board.geStone(row, column) != null && board.geStone(row, column).getColor() != color) {
                board.removeStone(row, column);
                whiteCaptureCount++;
            }
        } else {
            if (board.geStone(row, column) != null && board.geStone(row, column).getColor() != color) {
                board.removeStone(row, column);
                blackCaptureCount++;
            }
        }
    }

    private Coordinate possibleCaptureCoordinates(Coordinate from, Coordinate to) {
        int middleRow = (from.getRow() + to.getRow()) / 2;
        int middleCol = (from.getColumn() + to.getColumn()) / 2;

        return new Coordinate(middleRow, middleCol);
    }

    private Coordinate[] convertedMovesToBoardCoordinates(String move) {
        HashMap<String, Integer> columnCoordinates = new HashMap<>();
        columnCoordinates.put("A", 0);
        columnCoordinates.put("B", 1);
        columnCoordinates.put("C", 2);
        columnCoordinates.put("D", 3);
        columnCoordinates.put("E", 4);
        columnCoordinates.put("F", 5);
        columnCoordinates.put("G", 6);
        columnCoordinates.put("H", 7);

        String[] moveStringSplit = move.trim().replace(" ", "").split("->");
        Coordinate[] convertedMoves = new Coordinate[2];
        try {
            for (int i = 0; i < moveStringSplit.length; i++) {
                // adding row and column converted coordinates
                convertedMoves[i] = new Coordinate(8 - Integer.parseInt(moveStringSplit[i].substring(1)), columnCoordinates.get(moveStringSplit[i].substring(0, 1)));
            }
        } catch (NumberFormatException | NullPointerException exception) {
            return null;
        }

        return convertedMoves;
    }
}
