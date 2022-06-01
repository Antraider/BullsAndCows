package bullscows;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {
    private int bulls = 0;
    private int cows = 0;
    int secretCode;
    private int turn = 0;
    private boolean isRunning = true;
    private int length;

    public Game() {
        startGame();
    }

    private void startGame() {
        Scanner scanner = new Scanner(System.in);
        //checkGuess(scanner.nextInt(), secretCode);
        //showResult();
        System.out.println("Please, enter the secret code's length:");
        length = scanner.nextInt();
        if (length > 9) {
            System.out.println("Error");
        } else {
            secretCode =  Integer.parseInt(generateSecretCode(length));
            System.out.println("Okay, let's start a game!");
            while (isRunning) {
                System.out.printf("Turn %d:",++turn);
                checkGuess(scanner.nextInt(), secretCode);
                showResult();
            }
        }
            System.exit(0);
    }

    private String generateSecretCode(int length) {
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
            System.out.printf("Grade: %d bull(s) and %d cow(s).", bulls, cows);
        } else if (bulls > 0) {
            System.out.printf("Grade: %d bull(s)", bulls);
            if (bulls == length) {
                System.out.println("\nCongratulations! You guessed the secret code.");
                isRunning = false;
            }
        } else if (cows > 0) {
            System.out.printf("Grade: %d cow(s). The secret code is 9305.", cows);
        } else {
            System.out.println("Grade: None");
        }
        bulls = 0;
        cows = 0;
    }

    private void checkGuess(int number, int code) {
        ArrayList<Integer> userGuess = getIntegerArrayList(number);
        ArrayList<Integer> secretCode = getIntegerArrayList(code);

        for (int i = 0; i < secretCode.size(); i++) {
            for (int j = 0; j < userGuess.size(); j++) {
                if (i == j && secretCode.get(i).equals(userGuess.get(j))) {
                    bulls++;
                } else if (i != j && secretCode.get(i).equals(userGuess.get(j))) {
                    cows++;
                }
            }
        }
    }

    private ArrayList<Integer> getIntegerArrayList(int number) {
        ArrayList<Integer> array = new ArrayList<>();
        while (number != 0) {
            array.add(0,number % 10);
            number /= 10;
        }
        return array;
    }
}
