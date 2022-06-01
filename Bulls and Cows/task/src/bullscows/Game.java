package bullscows;
import java.util.Collection;
import java.util.Scanner;

public class Game {
    private int bulls = 0;
    private int cows = 0;
    int[] secretCode;

    public Game() {
        startGame();
    }

    private void startGame() {
        Scanner scanner = new Scanner(System.in);
        //checkGuess(scanner.nextInt(), secretCode);
        //showResult();
        System.out.println(generateSecretCode(scanner.nextInt()));
    }

    private String generateSecretCode(int length) {
        if (length > 9) {
            return "Error";
        }
        long randomSeed = Math.abs(System.nanoTime());
        StringBuilder builder = new StringBuilder();
        while (builder.length() < length) {
            if (randomSeed == 0) {
                randomSeed = Math.abs(System.nanoTime());
            }
            if (builder.indexOf(Long.toString(randomSeed % 10)) != -1 || randomSeed % 10 == 0) {
                randomSeed /= 10;
                continue;
            }
            builder.append(randomSeed % 10);
            randomSeed /= 10;
        }
        return builder.toString();
    }

    private void showResult() {
        if (bulls > 0 && cows > 0) {
            System.out.printf("Grade: %d bull(s) and %d cow(s). The secret code is 9305.", bulls, cows);
        } else if (bulls > 0) {
            System.out.printf("Grade: %d bull(s). The secret code is 9305.", bulls);
        } else if (cows > 0) {
            System.out.printf("Grade: %d cow(s). The secret code is 9305.", cows);
        } else {
            System.out.println("Grade: None. The secret code is 9305.");
        }
    }

    private void checkGuess(int number, int[] code) {

        //int length = number != 0 ? (int) (Math.log10(number) + 1) : 1; // check length
        int[] secretCode = code;
        int[] userGuess = new int[4];
        for (int i = userGuess.length - 1; i >= 0 ; i--) {
            userGuess[i] = number % 10;
            number /= 10;
        }
        for (int i = 0; i < secretCode.length; i++) {
            for (int j = 0; j < userGuess.length; j++) {
                if (i == j && secretCode[i] == userGuess[j]) {
                    bulls++;
                } else if (i != j &&  secretCode[i] == userGuess[j]) {
                    cows++;
                }

            }
        }
    }
}
