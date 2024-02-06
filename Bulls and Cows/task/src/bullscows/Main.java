package bullscows;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the secret code's length:");
        int size = scanner.nextInt();
        String secretCode;
        try {
            secretCode = generateSecret(size);
            System.out.println("Okay, let's start a game!");
        } catch (RuntimeException e) {
            scanner.close();
            return;
        }

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

    private static String generateSecret(int size) {
        if (size > 10) {
            System.out.println("Error: can't generate a secret number with a length of 11 " +
                    "because there aren't enough unique digits.\n");
            throw new RuntimeException();
        }
        StringBuilder randomNumberBuilder = new StringBuilder();
        List<Integer> alphabet = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(alphabet);
        for (int i = 0; i < size; i++) {
            randomNumberBuilder.append(alphabet.get(i));
        }
        return randomNumberBuilder.toString();
    }

    private static String getGradeMessage(Grade grade) {
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
