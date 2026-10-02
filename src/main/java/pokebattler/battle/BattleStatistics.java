package pokebattler.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleStatistics {
    private final List<BattleResult> results;

    public BattleStatistics(List<BattleResult> results) {
        this.results = new ArrayList<>(results);
    }


    public int totalWins() {
        return (int) results.stream()
                .filter(result -> result.playerWon())
                .count();
    }

    public int totalLosses() {
        return results.size() - totalWins();
    }

    public Map<String, Integer> battlesPerPokemon() {
        Map<String, Integer> battleMap = new HashMap<>();
        for (BattleResult result : results) {
            String name = result.pokemonName();
            Integer battles = battleMap.getOrDefault(name, 0) + 1;
            battleMap.put(name, battles);
        }
        return battleMap;
    }

    public Map<String, Integer> winsPerPokemon() {
        Map<String, Integer> winMap = new HashMap<>();
        for (BattleResult result : results) {
            if (result.playerWon()) {
                String name = result.pokemonName();
                Integer wins = winMap.getOrDefault(name, 0) + 1;
                winMap.put(name, wins);
            }
        }
        return winMap;
    }

    public String winRatioPerPokemon(String pokemonName) {
        int battles = battlesPerPokemon().getOrDefault(pokemonName, 0);
        if (battles == 0) {
            return "Inga strider";
        }
        int wins = winsPerPokemon().getOrDefault(pokemonName, 0);
        int losses = battles - wins;
        int percent = (int) Math.round(100.0 * wins / battles);
        return wins + " - " + losses + " (" + percent + "% vinster)";
    }



}