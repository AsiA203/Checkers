package Enums;

public enum MoveResult {
    MOVE_VALID("Move is valid."),
    CAPTURE("Capture is possible"),
    MOVE_INVALID("Move is invalid. Please move only diagonally and one move at a time if no capture"),
    INPUT_FORMAT_ERROR("Format of input move is not correct. Please follow this example with your own move 'A1 -> B2'."),
    MOVE_OUT_OF_BOUNDS("Move is invalid due to being out of bounds. Please enter new input."),
    EXTRA_MOVE_REQUIRED("You have one more possible capture. Please capture one more stone"),
    SQUARE_OCCUPIED("You can't put stone on top of another stone. Please enter new input."),
    WHITE_NO_MORE_MOVES("White Team have no more possible moves. Black Team won"),
    BLACK_NO_MORE_MOVES("Black Team have no more possible moves. White Team won");


    private final String message;

    MoveResult(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
