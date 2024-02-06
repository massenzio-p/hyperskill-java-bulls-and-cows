package bullscows;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try (var scanner  = new Scanner(System.in)) {
            GameParams gameParams = collectParams(scanner);

            BullsAndCowsGame game = new BullsAndCowsGame(scanner, new SecretGenerator(gameParams));
            game.play();
        } catch (CodeGenerationException | InvalidParamsException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static GameParams collectParams(Scanner scanner) throws InvalidParamsException {
        String input = null;
        int wholeSize;
        int alphabetSize;
        try {
            System.out.println("Input the length of the secret code:");
            input = scanner.nextLine();
            wholeSize = Integer.parseInt(input);
            if (wholeSize < 1) {
                throw new IllegalArgumentException();
            }

            System.out.println("Input the number of possible symbols in the code:");
            input = scanner.nextLine();
            alphabetSize = Integer.parseInt(input);
            if (alphabetSize < 1) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidParamsException(String.format("Error: \"%s\" isn't a valid number.", input));
        }
        if (alphabetSize > 36) {
            throw new InvalidParamsException(
                    "Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        }
        if (alphabetSize < wholeSize) {
            throw new InvalidParamsException(String.format(
                    "Error: it's not possible to generate a code " +
                            "with a length of %d with %d unique symbols.",
                    wholeSize, alphabetSize));
        }

        return new GameParams(wholeSize, alphabetSize);
    }
}
