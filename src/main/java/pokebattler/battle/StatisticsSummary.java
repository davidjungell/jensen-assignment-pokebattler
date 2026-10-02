package pokebattler.battle;

import java.util.Map;

public record StatisticsSummary(
        int totalWins,
        int totalLosses,
        String mostUsedPokemon,
        String moseUsedAttack,
        Map<String, PokemonStats> perPokemon
) {
}
