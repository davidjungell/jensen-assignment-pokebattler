# Pokémon Battler

This is Assignment 2 for the introductory Java course at Jensen YH. Assignment 1 was a console application, a collection of Pokémon (Pokédex), that handles (CRUD) and stores Pokémon. Assignment 2 expands Pokédex to include fighting capabilities between the Pokémon, storing battlestatistics to a JSON-file.
Battles are human versus CPU and are turn-based. Who starts is determined by coin-toss. CPU chooses a Pokémon at random from seeded data, while the player choose from their Pokédex (which is the same as the seeded data if no custom changes have been added).

Course: Programmering med Java, grund (Fall 2026) - Assignment 2 (Jensen) Author: David Jungell





---

## Table of contents

1. [Pokédex (from assignment 1)](#pokédex-from-assignment-1)

---

## Pokédex (from assignment 1)

### Functions
- Paginated listview over Pokémon
- CRUD (name, type, max HP, current HP, up to 4 attacks)
- Search by name
- Filter by type
- Sort names alphabetically
- Show stats for Pokémon
- JSON and CSV persistence
- At startup, the app tries to load automatically, prioritizing in this order: JSON -> CSV -> seeded data
- Seeded data contains 25 Pokémon
- Saves automatically to JSON when the user exits via the menu
- Field validation when creating and editing Pokémon (e.g. empty name, negative HP)


### CSV-format

The CSV-file should be of the following format:

```
name,type,maxHp,currentHp,attacks
Charmander,FIRE,39,39,Scratch:NORMAL:10:1.0|Growl:NORMAL:1:1.0|Ember:FIRE:15:0.95|Smokescreen:NORMAL:1:1.0
Squirtle,WATER,44,44,Tackle:NORMAL:10:1.0
```

A row corresponds to a Pokémon. In the example is Pokémon `Charmander` of `FIRE`, has 39 max HP, 39 current HP and 4 attacks. Each attack is separated by `|`, and the elements in the attack by `:`. For `Charmander`: the first element in `attacks` is Attack 1 (`Scratch:NORMAL:10:1.0`), and the first element in Attack 1 is its name (`Scratch`), followed by the type of the attack (`NORMAL`), damage (`10`) and accuracy (`1.0`). In the example `Squirtle` only has one attack (`Tackle:NORMAL:10:1.0`).

In order to read a Pokémon with an empty attack, the last comma needs to be present:

```
Charmander,FIRE,39,39,
```

### JSON-format

The collection of Pokémon should be a JSON-array, where each Pokémon is saved as a JSON-object, with the fields `name`, `type`, `maxHp`, `currentHp` and `attacks`. Here `attacks` is a JSON-array, where each attack is a JSON-object with the fields  `name`, `type`, `damage` and `accuracy`.

Here is an example with only one Pokémon, that has only one attack:

```json
[ {
  "name" : "Charmander",
  "type" : "FIRE",
  "maxHp" : 39,
  "currentHp" : 39,
  "attacks" : [ {
    "name" : "Ember",
    "type" : "FIRE",
    "damage" : 15,
    "accuracy" : 0.95
  } ]
} ]
```

### Known limitations
- Duplicates of Pokémon are permitted.

## Getting started

### Requirements

- Java 21 or newer
- Maven 3.9 or newer (tested with 3.9.11)

### Dependencies

Downloaded automatically (see `pom.xml`):

- Jackson `jackson-databind` 2.22.3 (JSON-handling)
- JUnit Jupiter 6.1.3 (testing)

### How to run the app

- Clone the repository and open it as a Maven-project in IntelliJ.
- Run `Main.java`.

## Data files

| File                            | Content                                          | Created by              |
|---------------------------------|--------------------------------------------------|-------------------------|
| `pokedex.json` or `pokedex.csv` | The player's Pokémon collection                  | `Storage`               |
| `battle_results.json`           | All completed battles with statistics summarized | `BattleStorage`         |
| `storage.SeedData.java`         | The Pokémon pool used by the CPU                 | `storage.SeedData.java` |














