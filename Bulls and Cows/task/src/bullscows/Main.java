package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String secret = Integer.toString(
                new Random().nextInt(10000 - 1000) + 1000);
        Grader grader = new GuessGrader(secret.toCharArray());

        Grade grade = grader.getGrade(scanner.nextLine().toCharArray());
        String gradeMessage = getGradeMessage(grade);

        System.out.printf(
                "Grade: %s. The secret code is %s.%n",
                gradeMessage,
                String.join("", secret));
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
