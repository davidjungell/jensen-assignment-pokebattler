package pokebattler.ui;

import pokebattler.battle.AttackSelector;
import pokebattler.model.Attack;
import pokebattler.model.Pokemon;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class HumanAttackSelector implements AttackSelector {
    private final Scanner scanner;

    public HumanAttackSelector(Scanner scanner) {
        this.scanner = scanner;
    }
    @Override
    public Optional<Attack> chooseAttack(Pokemon pokemon) {
        List<Attack> attacks = pokemon.getAttacks();
        while (true) {
            AttackPrinter.printNumbered(pokemon);
            String input = InputHelper.promptOrBack(scanner, "\nVälj attack (1-" + attacks.size() + ") eller tryck [Enter] för att avbryta: ");
            if (input == null) {
                return Optional.empty();
            }

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ogiltig inmatning. Försök igen.");
                continue;
            }

            if (choice >= 1 && choice <= attacks.size()) {
                return Optional.of(attacks.get(choice - 1));
            } else {
                System.out.println("Ogiltig inmatning. Försök igen.");
            }
        }
    }
}
