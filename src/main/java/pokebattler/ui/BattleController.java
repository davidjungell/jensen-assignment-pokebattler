package pokebattler.ui;

import pokebattler.battle.*;
import pokebattler.exception.StorageException;
import pokebattler.model.Pokemon;
import pokebattler.storage.BattleStorage;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class BattleController {

    public static void startBattle(Scanner scanner) {

        Random random = new Random();
        while (true) {
            BattleManager battleManager = new BattleManager(
                    random,
                    new HumanPokemonSelector(scanner),
                    new HumanAttackSelector(scanner),
                    new CPUPokemonSelector(random),
                    new CPUAttackSelector(random)
            );
            BattleResult result = battleManager.fight();

            if (!result.completed()) {
                System.out.println("Striden avbröts, statistiken sparas inte.");
                return;
            }

            try {
                BattleStorage.append(result);
                System.out.println("Resultatet sparades i battle_results.json");
            } catch (StorageException e) {
                System.out.println(e.getMessage());
            }

            System.out.println();
            System.out.print("Spela igen? (Y/N)?");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("Y")) {
                return;
            }
        }
    }

    public static void showStats(Scanner scanner) {
        List<BattleResult> results;
        try {
            results = BattleStorage.load();
        } catch (StorageException e) {
            System.out.println(e.getMessage());
            return;
        }
        if (results.isEmpty()) {
            System.out.println("Inga strider har spelats än.");
            System.out.println();
            InputHelper.promptOrBack(scanner, "Tryck [Enter] för att återvända till menyn: ");
            return;
        }

        StatisticsSummary summary = new BattleStatistics(results).summary();

        System.out.println();
        System.out.println("=== Stridsstatistik ===");
        System.out.println("Vinster: " + summary.totalWins());
        System.out.println("Förluster: " + summary.totalLosses());
        System.out.println("Mest använda Pokémon: " + orNone(summary.mostUsedPokemon()));
        System.out.println("Mest använda attack: " + orNone(summary.mostUsedAttack()));
        System.out.println();
        System.out.println("Vinst/förlust per Pokémon:");

        for (Map.Entry<String, PokemonStats> entry : summary.perPokemon().entrySet()) {
            PokemonStats s = entry.getValue();
            System.out.printf("%-15s %d - %d (%d%% vinster)%n", entry.getKey() + ":", s.wins(), s.losses(), s.winPercent());
        }
        System.out.println();
        InputHelper.promptOrBack(scanner, "Tryck [Enter] för att återvända till menyn: ");
    }

    private static String orNone(String value) {
        return value == null ? "ingen" : value;
    }

    public static void printChosenPokemon(boolean isCpu, Pokemon chosen) {
        String header = isCpu ? "CPUs val" : "Ditt val";
        System.out.println();
        System.out.printf("%s av Pokémon:%n", header);
        System.out.printf("Namn: %-15s Typ: %-10s HP: %d/%d%n",
                chosen.getName(), chosen.getType(), chosen.getCurrentHp(), chosen.getMaxHp());
    }
}
