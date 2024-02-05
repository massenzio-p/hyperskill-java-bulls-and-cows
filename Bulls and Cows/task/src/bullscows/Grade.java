package bullscows;

public class Grade {

    private final int bulls;
    private final int cows;

    public Grade(int bulls, int cows) {
        this.bulls = bulls;
        this.cows = cows;
    }

    public int getBulls() {
        return bulls;
    }

    public int getCows() {
        return cows;
    }
}
