package pokebattler.battle;

import java.util.List;

public record BattleResult(
        String pokemonName,
        List<String> playerAttacksUsed,
        boolean playerWon,
        boolean completed
) {

    public BattleResult {
        if (playerAttacksUsed == null) {
            playerAttacksUsed = List.of();
        }
    }

    public static BattleResult aborted() {
        return new BattleResult(null, List.of(), false, false);
    }

    public static BattleResult completed(String pokemonName, List<String> playerAttacksUsed, boolean playerWon) {
        return new BattleResult(pokemonName, playerAttacksUsed, playerWon, true);
    }
}
