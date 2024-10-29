import Controller.GameController;
import Enums.Color;
import Services.MoveValidator;
import model.Board;

public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        MoveValidator isMoveValid = new MoveValidator(board);
        GameController gameController = new GameController(board, isMoveValid);
        board.printBoard();

        System.out.println("---------------------------------------");
        gameController.moveStone("F6 -> D4 -> ", Color.Black);
        board.printBoard();

        System.out.println("---------------------------------------");
        gameController.moveStone("G7 -> F6 -> ", Color.Black);
        board.printBoard();

        System.out.println("---------------------------------------");
        gameController.moveStone("C3 -> E5 -> G7", Color.White);
        board.printBoard();
    }
}
