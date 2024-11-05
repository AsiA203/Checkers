import Controller.GameController;
import Enums.Color;
import Enums.MoveResult;
import Services.MoveValidator;
import model.Board;
import model.Player;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        MoveValidator isMoveValid = new MoveValidator(board);
        GameController gameController = new GameController(board, isMoveValid);
        board.printBoard();

        Scanner scanner = new Scanner(System.in);
        Player playerWhite = new Player(Color.WHITE);
        Player playerBlack = new Player(Color.BLACK);

        System.out.println("Please enter player 1 (Team White) enter your name: ");
        playerWhite.setName(scanner.nextLine());

        System.out.println("Please enter player 2 (Team Black) enter your name: ");
        playerBlack.setName(scanner.nextLine());

        Player currentPlayer;

        while ((gameController.getBlackCaptureCount() < 12 && gameController.getWhiteCaptureCount() < 12)) {
            for (int i = 1; i < 1000; i++) {
                if (i % 2 != 0) {
                    System.out.println("Team White, enter your move: ");
                    currentPlayer = playerWhite;

                } else {
                    System.out.println("Team Black, enter your move: ");
                    currentPlayer = playerBlack;
                }


                MoveResult result;
                do {
                    result = gameController.mainController(scanner.nextLine(), currentPlayer.getColor());
                    if (result != MoveResult.MOVE_VALID && result != MoveResult.CAPTURE) {
                        helper(result);
                    }

                } while ((result != MoveResult.MOVE_VALID && result != MoveResult.CAPTURE));

                board.printBoard();
            }
        }

        System.out.println("Game Over!");
        if (gameController.getWhiteCaptureCount() == 12) {
            System.out.println(MoveResult.BLACK_NO_MORE_MOVES);
        } else System.out.println(MoveResult.WHITE_NO_MORE_MOVES);
    }

    public static void helper(MoveResult result) {
        switch (result) {
            case MOVE_OUT_OF_BOUNDS: {
                System.out.println(MoveResult.MOVE_OUT_OF_BOUNDS);
                break;
            }

            case SQUARE_OCCUPIED: {
                System.out.println(MoveResult.SQUARE_OCCUPIED);
                break;
            }

            case EXTRA_MOVE_REQUIRED: {
                System.out.println(MoveResult.EXTRA_MOVE_REQUIRED);
                break;
            }

            case MOVE_INVALID: {
                System.out.println(MoveResult.MOVE_INVALID);
                break;
            }

            case INPUT_FORMAT_ERROR: {
                System.out.println(MoveResult.INPUT_FORMAT_ERROR);
                break;
            }
        }
    }
}