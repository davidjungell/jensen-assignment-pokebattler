package pokebattler.battle;

import java.util.*;

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

    public Optional<String> mostCommon(List<String> items) {
        Map<String, Integer> countMap = new HashMap<>();
        for (String item : items) {
            Integer count = countMap.getOrDefault(item, 0) + 1;
            countMap.put(item, count);
        }

        return countMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    public Optional<String> mostUsedPokemon() {
        List<String> names = new ArrayList<>();
        for (BattleResult result : results) {
            names.add(result.pokemonName());
        }
        return mostCommon(names);
    }

    public Optional<String> mostUsedAttack() {
        List<String> attacks = new ArrayList<>();
        for (BattleResult result : results) {
            attacks.addAll(result.playerAttacksUsed());
        }
        return mostCommon(attacks);
    }

}