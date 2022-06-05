package bullscows;
import java.util.*;

public class Game {
    private int bulls = 0;
    private int cows = 0;
    private String secretCode;
    private int turn = 0;
    private boolean isRunning = true;
    private int length;
    private int range;

    public Game() {
        startGame();
    }

    private void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the secret code's length:");
        String len = scanner.nextLine();
        try {
            length = Integer.parseInt(len);
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + len + "\" isn't a valid number.");
            System.exit(0);
        }
        if (length == 0) {
            System.out.println("Error: \"" + length + "\" isn't a valid number.");
            System.exit(0);
        }
        System.out.println("Input the number of possible symbols in the code:");
        range = scanner.nextInt();

        if (range < length) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.",length , range);
            System.exit(0);
        }
        if (range > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }

        if (length > 9) {
            System.out.println("Error");
        } else {
            secretCode = generateSecretCode(length, range);
            System.out.println("Okay, let's start a game!");
            while (isRunning) {
                System.out.printf("Turn %d:",++turn);
                checkGuess(scanner.next(), secretCode);
                showResult();
            }
        }
            System.exit(0);
    }

    private String generateSecretCode(int length, int range) {
        String s = "0123456789abcdefghijklmnopqrstuvwxyz";
        String codeSeed = s.substring(0, range);
        String temp;
        char[] seed = codeSeed.toCharArray();

        ArrayList<Character> cod = new ArrayList<>();
        for (char ch: seed) {
            cod.add(ch);
        }
        Collections.shuffle(cod);
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            builder.append(cod.get(i));
        }
        StringBuilder codePrepared = new StringBuilder();
        codePrepared.append("The secret is prepared: ");
        codePrepared.append("*".repeat(Math.max(0, length)));
        if (range < 11) {
            temp = codeSeed.replaceAll("^(\\d).*(\\d)", " ($1-$2)");
        } else {
            temp = codeSeed.replaceAll("^(\\d).*(\\d)(\\w).*(\\w)", " ($1-$2, $3-$4)");
        }
        codePrepared.append(temp);
        System.out.println(codePrepared.toString());

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

    private void checkGuess(String number, String code) {
        char[] guess = number.toCharArray();
        char[] secret = code.toCharArray();

        for (int i = 0; i < secret.length; i++) {
            for (int j = 0; j < guess.length; j++) {
                if (i == j && secret[i] == guess[j]) {
                    bulls++;
                } else if (i != j && secret[i] == guess[j]) {
                    cows++;
                }
            }
        }
    }

}
