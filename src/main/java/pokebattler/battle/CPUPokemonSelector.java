package pokebattler.battle;

import pokebattler.model.Pokemon;
import pokebattler.service.Manager;
import pokebattler.ui.BattleController;

import java.util.List;
import java.util.Random;

public class CPUPokemonSelector implements PokemonSelector {
    private final Random random;

    public CPUPokemonSelector(Random random) {
        this.random = random;
    }

    @Override
    public Pokemon choosePokemon() {
        List<Pokemon> list = Manager.getPokedexList();
        int index = random.nextInt(list.size());
        Pokemon chosen = list.get(index);
        BattleController.printChosenPokemon(true, chosen);
        return chosen;
    }
}
