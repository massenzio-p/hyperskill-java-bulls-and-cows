package bullscows;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GuessGrader implements Grader {

    private final char[] secret;
    private final Set<Character> alphabet;

    public GuessGrader(char[] secret) {
        this.secret = secret;
        this.alphabet = new HashSet<>();
        for (var digit : secret) {
            alphabet.add(digit);
        }
    }

    @Override
    public Grade getGrade(char[] guess) {
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < guess.length; i++) {
            if (secret[i] == guess[i]) {
                bulls++;
            } else if (alphabet.contains(guess[i])) {
                cows++;
            }
        }
        return new Grade(bulls, cows);
    }
}
