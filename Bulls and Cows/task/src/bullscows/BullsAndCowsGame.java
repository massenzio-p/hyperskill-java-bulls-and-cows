package bullscows;

import java.util.Scanner;

public class BullsAndCowsGame {

    private final Scanner scanner;
    private final SecretGenerator secretGenerator;
    private GameParams params;

    public BullsAndCowsGame(Scanner scanner, SecretGenerator secretGenerator) {
        this.scanner = scanner;
        this.secretGenerator = secretGenerator;
    }


    public void setParams(GameParams gameParams) {
        this.params = gameParams;
    }

    public void play() throws CodeGenerationException {
        String secretCode = this.secretGenerator.generateSecret();
        String input;
        int turn = 0;
        Grader grader = new GuessGrader(secretCode.toCharArray());

        while (true) {
            turn++;
            System.out.printf("Turn %d:%n", turn);
            input = scanner.nextLine();
            Grade grade = grader.getGrade(input.toCharArray());
            String gradeMessage = getGradeMessage(grade);
            System.out.printf(
                    "Grade: %s%n",
                    gradeMessage,
                    String.join("", secretCode));
            if (input.equals(secretCode)) {
                System.out.println("Congratulations! You guessed the secret code.");
                return;
            }
        }
    }

    private String getGradeMessage(Grade grade) {
        if (grade.getBulls() == 0 && grade.getCows() == 0) {
            return "None";
        }
        StringBuilder builder = new StringBuilder();
        if (grade.getBulls() > 0) {
            builder.append(grade.getBulls()).append(" bull");
            if (grade.getBulls() > 1) {
                builder.append("s");
            }
        }
        if (grade.getCows() > 0) {
            if (!builder.isEmpty()) {
                builder.append(" and ");
            }
            builder.append(grade.getCows()).append(" cows");
            if (grade.getCows() > 1) {
                builder.append("s");
            }
        }
        return builder.toString();
    }
}
