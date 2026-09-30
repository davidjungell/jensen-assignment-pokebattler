package pokebattler.ui;

import pokebattler.battle.AttackSelector;
import pokebattler.model.Attack;
import pokebattler.model.Pokemon;

import java.util.List;
import java.util.Scanner;

public class HumanAttackSelector implements AttackSelector {
    private final Scanner scanner;

    public HumanAttackSelector(Scanner scanner) {
        this.scanner = scanner;
    }
    @Override
    public Attack chooseAttack(Pokemon pokemon) {
        List<Attack> attacks = pokemon.getAttacks();
        while (true) {
            PokemonController.printAttacks(pokemon);
            String input = InputHelper.promptOrBack(scanner, "Välj attack (1-" + attacks.size() + ") eller tryck Enter för att avbryta striden: ");
            if (input == null) {
                return null;
            }

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ogiltig inmatning. Försök igen.");
                continue;
            }

            if (choice >= 1 && choice <= attacks.size()) {
                return attacks.get(choice - 1);
            } else {
                System.out.println("Ogiltig inmatning. Försök igen.");
            }
        }
    }
}
