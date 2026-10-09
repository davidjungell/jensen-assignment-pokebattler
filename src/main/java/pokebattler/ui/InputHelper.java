package pokebattler.ui;

import java.util.Scanner;

class InputHelper {
    static String promptOrBack(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : input;
    }
}