package pokebattler.battle;

import pokebattler.model.Pokemon;

import java.util.Optional;

public interface PokemonSelector {
    /**
     * @return vald Pokémon, eller Optional.empty() om valet avbryts, eller om ingen Pokémon kan väljas.
     */
    Optional<Pokemon> choosePokemon();
}
