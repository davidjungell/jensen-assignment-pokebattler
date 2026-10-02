package pokebattler.battle;

public record PokemonStats(
        int battles,
        int wins,
        int losses,
        int winPercent
) {
}
