package pokebattler.battle;

import java.util.List;

public record BattleResult(
        String playerName,
        String pokemonName,
        List<String> playerAttacksUsed,
        boolean playerWon,
        boolean completed
) {

    public static BattleResult aborted() {
        return new BattleResult(null, null, List.of(), false, false);
    }

    public static BattleResult completed(String playerName, String pokemonName, List<String> playerAttacksUsed, boolean playerWon) {
        return new BattleResult(playerName, pokemonName, playerAttacksUsed, playerWon, true);
    }
}
