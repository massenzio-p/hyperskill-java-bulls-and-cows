package bullscows;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
        Set<Integer> alphabet = new HashSet<>();
        while (randomNumberBuilder.length() < size) {
            char[] nanos = Long.toString(System.nanoTime()).toCharArray();
            for (int i = nanos.length - 1; i >= 0 && randomNumberBuilder.length() < size; i--) {
                int number = Character.getNumericValue(nanos[i]);
                if (alphabet.contains(number) || number == 0 && randomNumberBuilder.isEmpty()) {
                    continue;
                }
                alphabet.add(number);
                randomNumberBuilder.append(number);
            }
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
