package pokebattler.battle;

import pokebattler.model.Pokemon;

import pokebattler.ui.BattleController;

import java.util.List;
import java.util.Random;

public class CPUPokemonSelector implements PokemonSelector {
    private final Random random;
    private final List<Pokemon> list;

    public CPUPokemonSelector(Random random, List<Pokemon> list) {
        this.random = random;
        this.list = list;
    }

    @Override
    public Pokemon choosePokemon() {
        List<Pokemon> candidates = list
                .stream()
                .filter(p -> p.getAttacks() != null && !p.getAttacks().isEmpty())
                .toList();

        int index = random.nextInt(candidates.size());
        Pokemon chosen = candidates.get(index).copy();
        BattleController.printChosenPokemon(true, chosen);
        return chosen;
    }
}
