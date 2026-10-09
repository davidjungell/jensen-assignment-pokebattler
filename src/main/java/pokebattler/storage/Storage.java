package pokebattler.storage;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import pokebattler.exception.InvalidAttackException;
import pokebattler.exception.InvalidPokemonException;
import pokebattler.exception.StorageException;
import pokebattler.model.Attack;
import pokebattler.model.Pokemon;
import pokebattler.model.Type;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Storage {
    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private static final String CSV_FILE = "pokedex.csv";
    private static final String JSON_FILE = "pokedex.json";

    public static List<Pokemon> loadStartupData() {
        if (jsonFileExists()) {
            try {
                List<Pokemon> list = loadJson();
                System.out.println("Lyckades läsa in " + JSON_FILE + ".");
                return list;
            } catch (StorageException e) {
                System.out.println(e.getMessage());
                System.out.println("Försöker läsa in CSV istället.");
            }
        }
        if (csvFileExists()) {
            try {
                List<Pokemon> list = loadCsv();
                System.out.println("Lyckades läsa in " + CSV_FILE + ".");
                return list;
            } catch (StorageException e) {
                System.out.println(e.getMessage());
                System.out.println("Använder seedad data istället.");
            }
        }
        return SeedData.loadSeedData();
    }

    public static boolean fileExists(FileFormat format) {
        return switch (format) {
            case JSON -> jsonFileExists();
            case CSV -> csvFileExists();
        };
    }

    private static boolean jsonFileExists() {
        File jsonFile = new File(JSON_FILE);
        return jsonFile.exists() && jsonFile.length() > 0;
    }

    private static boolean csvFileExists() {
        File csvFile = new File(CSV_FILE);
        return csvFile.exists() && csvFile.length() > 0;
    }

    public static void save(List<Pokemon> list, FileFormat format) {
        switch (format) {
            case JSON -> saveJson(list);
            case CSV -> saveCsv(list);
        }
    }

    public static List<Pokemon> load(FileFormat format) {
        return switch (format) {
            case JSON -> loadJson();
            case CSV -> loadCsv();
        };
    }

    private static void saveJson(List<Pokemon> list) {
        try {
            mapper.writerFor(new TypeReference<List<Pokemon>>() {})
                    .writeValue(new File(JSON_FILE), list);
        } catch (IOException e) {
            throw new StorageException("Kunde inte spara till JSON-fil.", e);
        }
    }

    private static List<Pokemon> loadJson() {
        try {
            return mapper.readValue(new File(JSON_FILE), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            JsonLocation loc = e.getLocation();
            String location = (loc != null && loc.getLineNr() > 0)
                    ? String.valueOf(loc.getLineNr()) : "";
            Throwable cause = e.getCause();
            if (cause instanceof InvalidPokemonException || cause instanceof InvalidAttackException) {
                // location pekar här på JSON objektet, inte den felaktiga raden
                throw new StorageException("Korrupt eller felformaterad JSON-fil, ungefär vid rad " + location + ". " + cause.getMessage(), e);
            }
            throw new StorageException("Kunde inte tolka JSON filen. Se rad: " + location, e);

        } catch (IOException e) {
            throw new StorageException("Kunde inte ladda JSON filen.", e);
        }
    }

    private static void saveCsv(List<Pokemon> list) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(CSV_FILE))) {
            writer.write("name,type,maxHp,currentHp,attacks");
            writer.newLine();
            for (Pokemon p : list) {
                List<Attack> attacks = p.getAttacks() == null ? new ArrayList<>() : p.getAttacks(); // gard mot NPE
                String attacksField = attacks.stream()
                        .map(a -> a.getName() + ":" + a.getType() + ":" + a.getDamage() + ":" + a.getAccuracy())
                        .collect(Collectors.joining("|"));

                writer.write(String.join(",",
                        p.getName(),
                        String.valueOf(p.getType()),
                        String.valueOf(p.getMaxHp()),
                        String.valueOf(p.getCurrentHp()),
                        attacksField));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new StorageException("Kunde inte spara till CSV-fil.", e);
        }
    }

    private static List<Pokemon> loadCsv() {
        List<Pokemon> result = new ArrayList<>();
        int lineNumber = 1;

        try (BufferedReader reader = Files.newBufferedReader(Path.of(CSV_FILE))) {
            String line;
            reader.readLine();  // skippa header

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) continue;
                List<Attack> attacks = new ArrayList<>();
                String[] fields = line.split(",", -1);
                if (fields.length != 5) {
                    throw new StorageException("Korrupt eller felformaterad CSV-fil. Se rad: " + lineNumber);
                }

                if (!fields[4].isBlank()) {
                    String[] attacksArray = fields[4].split("\\|");
                    for (String attackStr : attacksArray) {
                        String[] attackElements = attackStr.split(":");
                        if (attackElements.length != 4) {
                            throw new StorageException("Korrupt attack fält i CSV-filen, se rad: " + lineNumber + " (" + attackStr + ").");
                        }

                        Attack attack = new Attack(
                                attackElements[0],
                                Type.valueOf(attackElements[1]),
                                Integer.parseInt(attackElements[2]),
                                Double.parseDouble(attackElements[3])
                        );
                        attacks.add(attack);
                    }
                }
                // Fungerar lägga till Pokemon med tom attack.
                result.add(new Pokemon(
                        fields[0],
                        Type.valueOf(fields[1]),
                        Integer.parseInt(fields[2]),
                        Integer.parseInt(fields[3]),
                        attacks
                ));
            }
        } catch (IOException e) {
            throw new StorageException("Kunde inte läsa in från CSV.", e);
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException | InvalidPokemonException |
                 InvalidAttackException e) {
            throw new StorageException("Korrupt eller felformaterad CSV-fil. Se rad: " + lineNumber + ". " + e.getMessage(), e);
        }
        return result;
    }
}
