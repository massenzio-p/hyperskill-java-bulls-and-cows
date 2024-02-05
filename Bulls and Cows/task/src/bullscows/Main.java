package bullscows;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int size = scanner.nextInt();
        if (size > 10) {
            System.out.println("Error: can't generate a secret number with a length of 11 " +
                    "because there aren't enough unique digits.\n");
            return;
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
        System.out.printf("The random secret number is %s.%n", randomNumberBuilder);

        /*String secret = Integer.toString(
                new Random().nextInt(10000 - 1000) + 1000);
        Grader grader = new GuessGrader(secret.toCharArray());

        Grade grade = grader.getGrade(scanner.nextLine().toCharArray());
        String gradeMessage = getGradeMessage(grade);

        System.out.printf(
                "Grade: %s. The secret code is %s.%n",
                gradeMessage,
                String.join("", secret));*/
    }

    private static String getGradeMessage(Grade grade) {
        if (grade.getBulls() == 0 && grade.getCows() == 0) {
            return "None";
        }
        StringBuilder builder = new StringBuilder();
        if (grade.getBulls() > 0) {
            builder.append(grade.getBulls()).append(" bull(s)");
        }
        if (grade.getCows() > 0) {
            if (!builder.isEmpty()) {
                builder.append(" and ");
            }
            builder.append(grade.getCows()).append(" cows(s)");
        }
        return builder.toString();
    }
}
