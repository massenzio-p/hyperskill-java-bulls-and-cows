package bullscows;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the secret code's length:");
        int commonSize = scanner.nextInt();
        System.out.println("Input the number of possible symbols in the code:");
        int lettersSize = scanner.nextInt();
        scanner.nextLine();
        String secretCode;
        try {
            secretCode = generateSecret(commonSize, lettersSize);
            System.out.printf(
                    "The secret is prepared: %s (0-9%s).%n",
                    secretCode.replaceAll(".", "*"),
                    lettersSize > 10 ? ", " + getAlphabetMsg(lettersSize - 10) : ""
            );
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

    private static String getAlphabetMsg(int lettersSize) {
        if (lettersSize == 1) {
            return "a";
        }
        return "a-" + (char) (96 + lettersSize);
    }

    private static String generateSecret(int size, int alphabetSize) {
        if (size > 36 || alphabetSize < size) {
            System.out.println("Wtf?");
            throw new RuntimeException();
        }
        StringBuilder randomNumberBuilder = new StringBuilder();
        List<Character> alphabet = new ArrayList<>(size);
        // from '0' to size
        for (int i = 0; i < 10 && i < alphabetSize; i++) {
            alphabet.add((char) (i + 48));
        }
        // from 'a' to size
        for (int i = 0, commonSize = alphabet.size(); commonSize < alphabetSize; commonSize++, i++) {
            alphabet.add((char) (i + 97));
        }
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
