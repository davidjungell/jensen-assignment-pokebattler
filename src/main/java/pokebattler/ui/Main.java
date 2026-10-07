package pokebattler.ui;

import pokebattler.exception.PokemonNotFoundException;
import pokebattler.exception.StorageException;
import pokebattler.model.Pokemon;
import pokebattler.model.Type;
import pokebattler.service.Manager;
import pokebattler.storage.FileFormat;
import pokebattler.storage.SeedData;
import pokebattler.storage.Storage;

import java.util.List;
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
            System.out.println("1. Visa en lista över alla Pokémon med statistik.");
            System.out.println("2. Lägg till en ny Pokémon i listan.");
            System.out.println("3. Redigera en Pokémon.");
            System.out.println("4. Ta bort en Pokémon.");
            System.out.println("5. Spara Pokédexlistan till fil.");
            System.out.println("6. Läs in Pokédexlist från fil.");
            System.out.println("7. Återställ Pokédexlistan till sin ursprungliga form.");
            System.out.println("8. Starta strid.");
            System.out.println("9. Visa stridsstatistik.");
            System.out.println("10. Avsluta (sparar Pokédexlistan automatiskt).");
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
                case 1 -> showList(scanner);
                case 2 -> PokemonController.addPokemon(scanner);
                case 3 -> PokemonController.editPokemon(scanner);
                case 4 -> PokemonController.removePokemon(scanner);
                case 5 -> {
                    FileFormat format = chooseFileFormat(scanner, "Välj vilket filformat du vill spara till.");
                    if (format == null) {
                        break;
                    }
                    try {
                        Storage.save(Manager.getPokedexList(), format);
                        System.out.println("Sparat till pokedex." + format.toString().toLowerCase());
                    } catch (StorageException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 6 -> {
                    FileFormat format = chooseFileFormat(scanner, "Välj vilket filformat du vill ladda från ([Enter] för att backa).");
                    if (format == null) {
                        break;
                    }
                    if (!Storage.fileExists(format)) {
                        System.out.println("Kunde inte hitta pokedex." + format.toString().toLowerCase() + ".");
                        break;
                    }
                    try {
                        Manager.setPokedexList(Storage.load(format));
                        System.out.println("Läste in från pokedex." + format.toString().toLowerCase() + ".");
                    } catch (StorageException e) {
                        System.out.println(e.getMessage());
                    }

                }
                case 7 -> {
                    Manager.setPokedexList(SeedData.loadSeedData());
                    System.out.println("Pokédexlistan har seedats till sina ursprungliga värden.");
                }
                case 8 -> BattleController.startBattle(scanner);

                case 9 -> BattleController.showStats(scanner);

                case 10 -> {
                    try {
                        Storage.save(Manager.getPokedexList(), FileFormat.JSON);
                        System.out.println("Avslutar programmet (sparade automatiskt till pokedex.json).");
                    } catch (StorageException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Avslutar programmet.");
                    }
                    isRunning = false;
                }
                default -> System.out.println("Ogiltig inmatning! Försök igen.");
            }

        }

    }

    public static FileFormat chooseFileFormat(Scanner scanner, String prompt) {
        while (true) {
            System.out.println();
            System.out.println(prompt);
            System.out.println("1. JSON");
            System.out.println("2. CSV");
            System.out.println();
            String input = InputHelper.promptOrBack(scanner, "Välj alternativ ([Enter]) för att backa): ");
            if (input == null) {
                return null;
            }
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ogiltig inmatning! Försök igen.");
                continue;
            }
            if (choice < 1 || choice > 2) {
                System.out.println("Ogiltig inmatning! Försök igen.");
                continue;
            }
            if (choice == 1) {
                return FileFormat.JSON;
            } else {
                return FileFormat.CSV;
            }
        }
    }

    public static void showList(Scanner scanner) {
        List<Pokemon> displayList = Manager.getPokedexList();
        int pageSize = 10;
        int currentPage = 0;

        while (true) {
            int totalPages = (int) Math.ceil((double) displayList.size() / pageSize);
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, displayList.size());
            List<Pokemon> pageView = displayList.subList(start, end);

            System.out.println();
            System.out.println("=== Pokémon-lista (sida " + (currentPage + 1) + " av " + totalPages + ") ===");
            int index = start;
            for (Pokemon p : pageView) {
                index++;
                System.out.printf("%2d. Namn: %-15s Typ: %-10s HP: %d/%d%n",
                        index, p.getName(), p.getType(), p.getCurrentHp(), p.getMaxHp());
            }
            System.out.println("[N]ästa  [F]öregående  [S]ortera  [Fi]ltrera  [Sö]k  [V]isa statistik  [Å]terställ vy");
            System.out.println();

            String choice = InputHelper.promptOrBack(scanner, "Ange kommando, eller tryck [Enter] för att backa: ");
            if (choice == null) {
                break;
            }

            switch (choice.toUpperCase()) {
                case "N" -> {
                    if (displayList.size() > end) {
                        currentPage++;
                    } else {
                        System.out.println("Du är redan på sista sidan.");
                    }
                }
                case "F" -> {
                    if (currentPage > 0) {
                        currentPage--;
                    } else {
                        System.out.println("Du är redan på första sidan.");
                    }
                }
                case "S" -> {
                    displayList = Manager.sortByNameBubble(displayList);
                    currentPage = 0;
                }
                case "FI" -> {
                    while (true) {
                        String input = InputHelper.promptOrBack(scanner, "Ange typen att filtrera efter (FIRE, WATER, GRASS, ELECTRIC, NORMAL) eller tryck [Enter] för att backa: ");
                        if (input == null) {
                            break;
                        }

                        try {
                            List<Pokemon> result = Manager.filterByType(Type.valueOf(input.toUpperCase()), displayList);
                            if (result.isEmpty()) {
                                System.out.println("Inga Pokémon att visa efter filtreringen.");
                                break;
                            }
                            displayList = result;
                            currentPage = 0;
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Okänd typ. Försök igen.");
                        }
                    }

                }
                case "SÖ" -> {
                    String input = InputHelper.promptOrBack(scanner, "Sök efter namn (t.ex. 'pika') eller tryck [Enter] för att backa: ");
                    if (input == null) {
                        break;
                    }
                    List<Pokemon> result = Manager.searchByName(input);
                    if (result.isEmpty()) {
                        System.out.println("Sökningen matchar ingen Pokémon.");
                        break;
                    }
                    displayList = result;
                    currentPage = 0;
                }
                case "V" -> {
                    while (true) {
                        String input = InputHelper.promptOrBack(scanner, "Ange namnet på den Pokémon som du vill se statistik för ([Enter] för att backa): ");
                        if (input == null) {
                            break;
                        }

                        Pokemon pokemon;
                        try {
                            pokemon = Manager.findByName(input);
                        } catch (PokemonNotFoundException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        PokemonController.showStats(pokemon);
                    }
                }
                case "Å" -> {
                    displayList = Manager.getPokedexList();
                    currentPage = 0;
                }
                default -> System.out.println("Ogiltigt val! Försök igen.");
            }

        }

    }
}
