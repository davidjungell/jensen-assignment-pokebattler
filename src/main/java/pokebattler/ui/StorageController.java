package pokebattler.ui;

import pokebattler.exception.StorageException;
import pokebattler.service.Manager;
import pokebattler.storage.FileFormat;
import pokebattler.storage.SeedData;
import pokebattler.storage.Storage;

import java.util.Scanner;

public class StorageController {

    static void saveToFile(Scanner scanner) {
        FileFormat format = chooseFileFormat(scanner, "Filformat att spara till:");
        if (format == null) {
            return;
        }
        try {
            Storage.save(Manager.getPokedexList(), format);
            System.out.println("Sparade till pokedex." + format.toString().toLowerCase() + ".");
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        }
    }

    static void loadFromFile(Scanner scanner) {
        FileFormat format = chooseFileFormat(scanner, "Filformat att ladda från:");
        if (format == null) {
            return;
        }
        if (!Storage.fileExists(format)) {
            System.out.println("Kunde inte hitta pokedex." + format.toString().toLowerCase() + ".");
            return;
        }
        try {
            Manager.setPokedexList(Storage.load(format));
            System.out.println("Läste in från pokedex." + format.toString().toLowerCase() + ".");
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        }
    }

    static void resetToSeed() {
        Manager.setPokedexList(SeedData.loadSeedData());
        System.out.println("Pokédexlistan har seedats till sina ursprungliga värden.");
    }

    private static FileFormat chooseFileFormat(Scanner scanner, String prompt) {
        while (true) {
            System.out.println();
            System.out.println(prompt);
            System.out.println("1. JSON");
            System.out.println("2. CSV");
            System.out.println();
            String input = InputHelper.promptOrBack(scanner, "Välj alternativ ([Enter] för att backa): ");
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

    static void saveOnExit() {
        try {
            Storage.save(Manager.getPokedexList(), FileFormat.JSON);
            System.out.println("Avslutar programmet (sparade automatiskt till pokedex.json).");
        } catch (StorageException e) {
            System.out.println(e.getMessage());
            System.out.println("Avslutar programmet.");
        }
    }
}
