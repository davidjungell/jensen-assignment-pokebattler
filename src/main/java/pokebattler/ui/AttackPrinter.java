package pokebattler.ui;

import pokebattler.model.Attack;
import pokebattler.model.Pokemon;

import java.util.List;

class AttackPrinter {
    static void printNumbered(Pokemon pokemon) {
        System.out.println();
        System.out.println("Attacker hos " + pokemon.getName() + ":");
        List<Attack> attacks = pokemon.getAttacks();
        for (int i = 0; i < attacks.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, describe(attacks.get(i)));
        }
    }

    static String describe(Attack a) {
        return String.format("%-15s Typ: %-10s Skada: %-4d Träffsäkerhet: %.0f%%",
                a.getName(), a.getType(), a.getDamage(), a.getAccuracy() * 100);
    }
}
