package pokebattler.ui;

import pokebattler.battle.*;
import pokebattler.exception.StorageException;
import pokebattler.storage.BattleStorage;

import java.util.Random;
import java.util.Scanner;

public class BattleController {

    public void startBattle(Scanner scanner) {
        String playerName = InputHelper.promptOrBack(scanner, "Ange ditt namn eller tryck Enter för att backa: ");
        if (playerName == null) {
            return;
        }
        Random random = new Random();
        while (true) {
            BattleManager battleManager = new BattleManager(
                    random,
                    new HumanPokemonSelector(scanner),
                    new HumanAttackSelector(scanner),
                    new CPUPokemonSelector(random),
                    new CPUAttackSelector(random)
            );
            BattleResult result = battleManager.fight(playerName);

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

            System.out.println("Spela igen? (Y/N)?");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("Y")) {
                return;
            }
        }
    }

    public void showStats() {

    }
}
