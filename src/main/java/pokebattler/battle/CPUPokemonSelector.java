package pokebattler.battle;

import pokebattler.model.Pokemon;
import pokebattler.service.Manager;

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
        return list.get(index);
    }
}
