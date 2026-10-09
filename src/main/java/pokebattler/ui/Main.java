package pokebattler.ui;

import pokebattler.service.Manager;
import pokebattler.storage.Storage;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager.setPokedexList(Storage.loadStartupData());
        startMenu(scanner);
        scanner.close();
    }

    public static void startMenu(Scanner scanner) {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println();
            System.out.println("=== Välkommen till Pokébattler ===");
            System.out.println("1. Visa en lista över alla Pokémon med statistik");
            System.out.println("2. Lägg till en ny Pokémon i listan");
            System.out.println("3. Redigera en Pokémon");
            System.out.println("4. Ta bort en Pokémon");
            System.out.println("5. Spara Pokédexlistan till fil");
            System.out.println("6. Läs in Pokédexlistan från fil");
            System.out.println("7. Återställ Pokédexlistan till sin ursprungliga form");
            System.out.println("8. Starta strid");
            System.out.println("9. Visa stridsstatistik");
            System.out.println("10. Avsluta (sparar Pokédexlistan automatiskt)");
            System.out.println();
            System.out.print("Ange ditt val: ");

            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ogiltig inmatning! Försök igen.");
                continue;
            }

            switch (choice) {
                case 1 -> PokemonController.showList(scanner);
                case 2 -> PokemonController.addPokemon(scanner);
                case 3 -> PokemonController.editPokemon(scanner);
                case 4 -> PokemonController.removePokemon(scanner);
                case 5 -> StorageController.saveToFile(scanner);
                case 6 -> StorageController.loadFromFile(scanner);
                case 7 -> StorageController.resetToSeed();
                case 8 -> BattleController.startBattle(scanner);
                case 9 -> BattleController.showStats(scanner);
                case 10 -> {
                    StorageController.saveOnExit();
                    isRunning = false;
                }
                default -> System.out.println("Ogiltig inmatning! Försök igen.");
            }
        }
    }
}
