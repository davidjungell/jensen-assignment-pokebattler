package pokebattler.ui;

import pokebattler.exception.InvalidAttackException;
import pokebattler.exception.InvalidPokemonException;
import pokebattler.exception.PokemonNotFoundException;
import pokebattler.model.Attack;
import pokebattler.model.Pokemon;
import pokebattler.model.Type;
import pokebattler.service.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PokemonController {
    public static void addPokemon(Scanner scanner) {
        String input;
        String name = null;
        boolean valid = false;
        while (!valid) {
            System.out.println();
            input = InputHelper.promptOrBack(scanner, "Ange namnet på den Pokémon du vill lägga till, eller tryck [Enter] för att backa till menyn: ");
            if (input == null) {
                return;
            }
            try {
                Pokemon.validateName(input);
                name = input;
                valid = true;
            } catch (InvalidPokemonException e) {
                System.out.println(e.getMessage());
            }

        }

        Type type = null;
        while (type == null) {
            input = InputHelper.promptOrBack(scanner, "Ange typen (FIRE, WATER, GRASS, ELECTRIC, NORMAL) för denna Pokémon, eller tryck [Enter] för att backa till menyn: ");
            if (input == null) {
                return;
            }

            try {
                type = Type.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Ogiltig typ! Försök igen.");
            }
        }

        int maxHp = 0;
        valid = false;
        while (!valid) {
            input = InputHelper.promptOrBack(scanner, "Ange max HP för denna Pokémon eller tryck [Enter] för att backa till menyn: ");
            if (input == null) {
                return;
            }

            try {
                maxHp = Integer.parseInt(input);
                Pokemon.validateMaxHp(maxHp);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Ogiltig inmatning! Försök igen.");
            } catch (InvalidPokemonException e) {
                System.out.println(e.getMessage());
            }
        }

        int currentHp = 0;
        valid = false;
        while (!valid) {
            input = InputHelper.promptOrBack(scanner, "Ange nuvarande HP för denna Pokémon eller tryck [Enter] för att backa till menyn: ");
            if (input == null) {
                return;
            }

            try {
                currentHp = Integer.parseInt(input);
                Pokemon.validateCurrentHp(currentHp, maxHp);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Ogiltig inmatning! Försök igen.");
            } catch (InvalidPokemonException e) {
                System.out.println(e.getMessage());
            }
        }

        List<Attack> attacks = new ArrayList<>();
        Manager.addPokemon(new Pokemon(name, type, maxHp, currentHp, attacks));
        System.out.println(name + " har lagts till. Använd 'Redigera en Pokémon' -> 'Redigera attacker' för att lägga till attacker.");
    }

    public static void removePokemon(Scanner scanner) {
        while (true) {
            System.out.println();
            String input = InputHelper.promptOrBack(scanner, "Ange namnet på den Pokémon som ska tas bort eller tryck [Enter] för att backa: ");
            if (input == null) {
                return;
            }

            Pokemon pokemon;
            try {
                pokemon = Manager.findByName(input);
            } catch (PokemonNotFoundException e) {
                System.out.println(e.getMessage());
                continue;
            }

            Manager.removePokemon(pokemon);
            System.out.println("Pokémon " + pokemon.getName() + " har tagits bort.");
        }
    }

    public static void editPokemon(Scanner scanner) {
        while (true) {
            System.out.println();
            String input = InputHelper.promptOrBack(scanner, "Ange namnet på den Pokémon som ska redigeras eller tryck [Enter] för att backa: ");
            if (input == null) {
                return;
            }

            Pokemon pokemon;
            try {
                pokemon = Manager.findByName(input);
            } catch (PokemonNotFoundException e) {
                System.out.println(e.getMessage());
                continue;
            }

            while (true) {
                System.out.println();
                System.out.println("Redigera egenskaper hos " + pokemon.getName() + ".");
                System.out.println("1. Namn.");
                System.out.println("2. Typ (" + pokemon.getType() + ").");
                System.out.println("3. Max HP (" + pokemon.getMaxHp() + ").");
                System.out.println("4. Nuvarande HP (" + pokemon.getCurrentHp() + ").");
                System.out.println("5. Redigera attacker.");
                System.out.println("6. Visa statistik för " + pokemon.getName() + ".");
                System.out.println();
                input = InputHelper.promptOrBack(scanner, "Ange ditt val ([Enter] för att backa): ");

                if (input == null) {
                    break;
                }

                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Ogiltig inmatning! Försök igen.");
                    continue;
                }

                boolean valid = false;
                switch (choice) {
                    case 1 -> {
                        while (!valid) {
                            input = InputHelper.promptOrBack(scanner, "Ange nytt namn eller tryck [Enter] för att backa: ");
                            if (input == null) {
                                break;
                            }
                            try {
                                Pokemon.validateName(input);
                                valid = true;
                                System.out.println("Namnet har ändrats från " + pokemon.getName() + " till " + input + ".");
                                pokemon.setName(input);
                                break;
                            } catch (InvalidPokemonException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    case 2 -> {
                        Type type = null;
                        while (type == null) {
                            input = InputHelper.promptOrBack(scanner, "Ange typen (FIRE, WATER, GRASS, ELECTRIC, NORMAL) eller tryck [Enter] för att backa: ");
                            if (input == null) {
                                break;
                            }
                            try {
                                type = Type.valueOf(input.toUpperCase());
                                System.out.println("Typ ändrad från " + pokemon.getType() + " till " + type + ".");
                                pokemon.setType(type);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Ogiltig inmatning! Försök igen");
                            }
                        }
                    }
                    case 3 -> {
                        while (!valid) {
                            input = InputHelper.promptOrBack(scanner, "Ange nytt max HP eller tryck [Enter] för att backa: ");
                            if (input == null) {
                                break;
                            }
                            try {
                                int maxHp = Integer.parseInt(input);
                                Pokemon.validateMaxHp(maxHp);
                                System.out.println("Max HP ändrat från " + pokemon.getMaxHp() + " till " + maxHp + ".");
                                pokemon.setMaxHp(maxHp);
                                valid = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Ogiltig inmatning! Försök igen");
                            } catch (InvalidPokemonException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    case 4 -> {
                        while (!valid) {
                            input = InputHelper.promptOrBack(scanner, "Ange nuvarande HP eller tryck [Enter] för att backa: ");
                            if (input == null) {
                                break;
                            }
                            try {
                                int currentHp = Integer.parseInt(input);
                                Pokemon.validateCurrentHp(currentHp, pokemon.getMaxHp());
                                System.out.println("Max HP ändrat från " + pokemon.getCurrentHp() + " till " + currentHp + ".");
                                pokemon.setCurrentHp(currentHp);
                                valid = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Ogiltig inmatning! Försök igen");
                            } catch (InvalidPokemonException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    case 5 -> editAttacks(scanner, pokemon);
                    case 6 -> showStats(pokemon);
                    default -> System.out.println("Ogiltig inmatning! Försök igen.");
                }
            }
        }

    }

    private static String readAttackName(Scanner scanner, int attackNumber) {
        while (true) {
            System.out.println();
            String input = InputHelper.promptOrBack(scanner, "Ange namn för Attack " + attackNumber + " eller tryck [Enter] för att backa: ");
            if (input == null) {
                return null;
            }
            try {
                Attack.validateName(input);
                return input;
            } catch (InvalidAttackException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Type readAttackType(Scanner scanner, int attackNumber) {
        while (true) {
            String input = InputHelper.promptOrBack(scanner, "Ange typen (FIRE, WATER, GRASS, ELECTRIC, NORMAL) för Attack " + attackNumber + ", eller tryck [Enter] för att backa: ");
            if (input == null) {
                return null;
            }
            try {
                return Type.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Ogiltig typ! Försök igen.");
            }
        }
    }

    private static Integer readAttackDamage(Scanner scanner, int attackNumber) {
        while (true) {
            String input = InputHelper.promptOrBack(scanner, "Ange skadan (>=0) för Attack " + attackNumber + ", eller tryck [Enter] för att backa: ");
            if (input == null) {
                return null;
            }
            try {
                int damage = Integer.parseInt(input);
                Attack.validateDamage(damage);
                return damage;
            } catch (NumberFormatException e) {
                System.out.println("Ogiltig inmatning! Försök igen.");
            } catch (InvalidAttackException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Double readAttackAccuracy(Scanner scanner, int attackNumber) {
        while (true) {
            String input = InputHelper.promptOrBack(scanner, "Ange träffsäkerheten (]0-1]) för Attack " + attackNumber + ", eller tryck [Enter] för att backa: ");
            if (input == null) {
                return null;
            }
            try {
                double accuracy = Double.parseDouble(input);
                Attack.validateAccuracy(accuracy);
                return accuracy;
            } catch (NumberFormatException e) {
                System.out.println("Ogiltig inmatning! Försök igen.");
            } catch (InvalidAttackException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Attack readSingleAttack(Scanner scanner, int attackNumber) {
        String name = null;
        Type type = null;
        Integer damage = null;
        Double accuracy = null;

        int step = 1;
        while (step <= 4) {
            switch (step) {
                case 1 -> {
                    name = readAttackName(scanner, attackNumber);
                    if (name == null) return null; // hoppar ut ur hela attacken
                    step++;
                }
                case 2 -> {
                    type = readAttackType(scanner, attackNumber);
                    if (type == null) {
                        step--;
                        break; // återgå till name
                    }
                    step++;
                }
                case 3 -> {
                    damage = readAttackDamage(scanner, attackNumber);
                    if (damage == null) {
                        step--;
                        break; // återgå till type
                    }
                    step++;
                }
                case 4 -> {
                    accuracy = readAttackAccuracy(scanner, attackNumber);
                    if (accuracy == null) {
                        step--;
                        break;
                    }
                    step++;
                }
            }
        }
        return new Attack(name, type, damage, accuracy); // Editor varnar kan bli NPE här, men flödet ovan garanterar att vi inte kan få NPE här
    }

    private static void addSingleAttack(Scanner scanner, Pokemon pokemon) {
        List<Attack> attacks = pokemon.getAttacks();
        if (attacks.size() >= 4) {
            System.out.println(pokemon.getName() + " har redan max antal attacker (4).");
            return;
        }
        Attack newAttack = readSingleAttack(scanner, attacks.size() + 1);
        if (newAttack == null) {
            return;
        }
        attacks.add(newAttack);
        System.out.println(newAttack.getName() + " har lagts till för " + pokemon.getName() + ".");
    }

    static void printAttacks(Pokemon pokemon) {
        System.out.println();
        System.out.println("Attacker hos " + pokemon.getName() + ":");
        List<Attack> attacks = pokemon.getAttacks();
        for (int i = 0; i < attacks.size(); i++) {
            Attack a = pokemon.getAttacks().get(i);
            System.out.printf("%d. %-15s Typ: %-10s Skada: %-4d Träffsäkerhet: %.0f%%%n",
                    i + 1, a.getName(), a.getType(), a.getDamage(), a.getAccuracy() * 100);
        }
    }

    private static void editSingleAttack(Scanner scanner, Pokemon pokemon, int attackNumber) {
        List<Attack> attacks = pokemon.getAttacks();
        Attack attack = attacks.get(attackNumber - 1);

        while (true) {
            System.out.println();
            System.out.println("Redigera '" + attack.getName() + "'.");
            System.out.println("1. Namn (" + attack.getName() + ")");
            System.out.println("2. Typ (" + attack.getType() + ")");
            System.out.println("3. Skada (" + attack.getDamage() + ")");
            System.out.println("4. Träffsäkerhet (" + attack.getAccuracy() + ")");
            System.out.println("5. Ta bort attacken.");
            System.out.println();

            String input = InputHelper.promptOrBack(scanner, "Ange ditt val (1-5), eller tryck [Enter] för att backa): ");
            if (input == null) {
                return;
            }

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ogiltig inmatning! Försök igen.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    String attackName = readAttackName(scanner, attackNumber);
                    if (attackName == null) {
                        break;
                    }
                    System.out.println("Namnet har ändrats från '" + attack.getName() + "' till '" + attackName + "'.");
                    attack.setName(attackName);
                }
                case 2 -> {
                    Type attackType = readAttackType(scanner, attackNumber);
                    if (attackType == null) {
                        break;
                    }
                    System.out.println("Typ ändrad från " + attack.getType() + " till " + attackType + ".");
                    attack.setType(attackType);
                }
                case 3 -> {
                    Integer damage = readAttackDamage(scanner, attackNumber);
                    if (damage == null) {
                        break;
                    }
                    System.out.println("Skadan har ändrats från " + attack.getDamage() + " till " + damage + ".");
                    attack.setDamage(damage);
                }
                case 4 -> {
                    Double accuracy = readAttackAccuracy(scanner, attackNumber);
                    if (accuracy == null) {
                        break;
                    }

                    System.out.println("Träffsäkerheten har ändrats från " + attack.getAccuracy() + " till " + accuracy + ".");
                    attack.setAccuracy(accuracy);
                }
                case 5 -> {
                    attacks.remove(attackNumber - 1);
                    System.out.println("Attacken " + attack.getName() + " har tagits bort.");
                    return;
                }
                default -> System.out.println("Ogiltig inmatning! Försök igen.");
            }
        }
    }

    private static void editAttacks(Scanner scanner, Pokemon pokemon) {
        while (true) {
            if (pokemon.getAttacks().isEmpty()) {
                System.out.println();
                System.out.println(pokemon.getName() + " har inga attacker.");
                System.out.println("[L]ägg till attack.");
                System.out.println();

                String input = InputHelper.promptOrBack(scanner, "Ange ditt kommando, eller tryck [Enter] för att backa: ");
                if (input == null) {
                    return;
                }
                if (input.equalsIgnoreCase("L")) {
                    addSingleAttack(scanner, pokemon);
                } else {
                    System.out.println("Ogiltig inmatning! Försök igen.");
                }
            } else {
                List<Attack> attacks = pokemon.getAttacks();
                printAttacks(pokemon);
                System.out.println("[L]ägg till en ny attack.");
                System.out.println();
                String input = InputHelper.promptOrBack(scanner, "Ange vilken attack du vill redigera (1-" + attacks.size() + "), tryck [L] för att lägga till en attack, eller [Enter] för att backa: ");
                if (input == null) {
                    return;
                }

                if (input.equalsIgnoreCase("L")) {
                    addSingleAttack(scanner, pokemon);
                    continue;
                }

                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Ogiltig inmatning! Försök igen.");
                    continue;
                }

                if (choice < 1 || choice > attacks.size()) {
                    System.out.println("Ogiltig inmatning! Försök igen.");
                    continue;
                }

                editSingleAttack(scanner, pokemon, choice);
            }
        }
    }

    public static void showStats(Pokemon p) {
        System.out.println();
        System.out.println("=== " + p.getName() + " (" + p.getType() + ") ===");
        System.out.println("HP: " + p.getCurrentHp() + "/" + p.getMaxHp());
        System.out.println("Attacker:");
        for (Attack a : p.getAttacks()) {
            System.out.printf("  - %-15s Typ: %-10s Skada: %-4d Träffsäkerhet: %.0f%%%n",
                    a.getName(), a.getType(), a.getDamage(), a.getAccuracy() * 100);
        }
        System.out.println();
    }
}
