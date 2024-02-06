package bullscows;

class GameParams {
    private final int secretSize;
    private final int alphabetSize;

    public GameParams(int secretSize, int alphabetSize) {
        this.secretSize = secretSize;
        this.alphabetSize = alphabetSize;
    }

    public int getSecretSize() {
        return secretSize;
    }

    public int getAlphabetSize() {
        return alphabetSize;
    }
}
