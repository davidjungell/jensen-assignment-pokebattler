package pokebattler.ui;

import pokebattler.battle.PokemonSelector;
import pokebattler.exception.PokemonNotFoundException;
import pokebattler.model.Pokemon;
import pokebattler.service.Manager;

import java.util.Optional;
import java.util.Scanner;

public class HumanPokemonSelector implements PokemonSelector {
    private final Scanner scanner;

    public HumanPokemonSelector(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Optional<Pokemon> choosePokemon() {
        while (true) {
            String input = InputHelper.promptOrBack(scanner, "\nVilken Pokémon vill du använda? (Enter för att avbryta): ");
            if (input == null) {
                return Optional.empty();
            }

            Pokemon chosen;
            try {
                chosen = Manager.findByName(input).copy();
            } catch (PokemonNotFoundException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (chosen.getAttacks() == null || chosen.getAttacks().isEmpty()) {
                System.out.println(chosen.getName() + " har inga attacker! Välj en annan Pokémon.");
            } else {
                return Optional.of(chosen);
            }
        }
    }
}
