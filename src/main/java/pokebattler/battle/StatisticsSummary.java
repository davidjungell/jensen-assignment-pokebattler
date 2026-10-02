package pokebattler.battle;

import java.util.Map;

public record StatisticsSummary(
        int totalWins,
        int totalLosses,
        String mostUsedPokemon,
        String mostUsedAttack,
        Map<String, PokemonStats> perPokemon
) {
}
