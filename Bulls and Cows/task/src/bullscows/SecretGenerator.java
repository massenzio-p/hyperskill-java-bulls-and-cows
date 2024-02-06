package bullscows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SecretGenerator {
    private final GameParams params;

    public SecretGenerator(GameParams gameParams) {
        this.params = gameParams;
    }

    public String generateSecret() throws CodeGenerationException {
        String secretCode = innerGenerateSecret();
        System.out.println(createSecretInfoMsg(secretCode));
        System.out.println("Okay, let's start a game!");
        return secretCode;
    }

    private String createSecretInfoMsg(String secretCode) {
        return String.format(
                "The secret is prepared: %s (0-9%s).%n",
                secretCode.replaceAll(".", "*"),
                this.params.getAlphabetSize() > 10 ?
                        ", " + getLetterAlphabetRangeMsg(this.params.getAlphabetSize() - 10) : ""
        );
    }

    private String getLetterAlphabetRangeMsg(int lettersSize) {
        if (lettersSize == 1) {
            return "a";
        }
        return "a-" + (char) (96 + lettersSize);
    }

    private String innerGenerateSecret() throws CodeGenerationException {
        if (this.params.getSecretSize() > 36 || this.params.getAlphabetSize() < this.params.getSecretSize()) {
            System.out.println("Wtf?");
            throw new CodeGenerationException();
        }
        StringBuilder randomNumberBuilder = new StringBuilder();
        List<Character> alphabet = new ArrayList<>(this.params.getSecretSize());
        // from '0' to size
        for (int i = 0; i < 10 && i < this.params.getAlphabetSize(); i++) {
            alphabet.add((char) (i + 48));
        }
        // from 'a' to size
        for (int i = 0, commonSize = alphabet.size(); commonSize < this.params.getAlphabetSize(); commonSize++, i++) {
            alphabet.add((char) (i + 97));
        }
        Collections.shuffle(alphabet);
        for (int i = 0; i < this.params.getSecretSize(); i++) {
            randomNumberBuilder.append(alphabet.get(i));
        }
        return randomNumberBuilder.toString();
    }
}
