# Pokémon Battler

This is Assignment 2 for the introductory Java course at Jensen YH. Assignment 1 is a console application, a collection of Pokémon (Pokédex), that handles (CRUD) and stores Pokémon. Assignment 2 expands Pokédex to include fighting capabilities between the Pokémon.

Battles are human versus CPU and are turn and text based. CPU chooses a Pokémon at random, while the player choose from their Pokédex.

Course: Programmering med Java, grund (Fall 2026) - Assignment 2 (Jensen)

Author: David Jungell

---

## Table of contents

1. [Pokédex (from assignment 1)](#pokédex-from-assignment-1)
    - [Functions](#functions)
    - [CSV-format (`pokedex.csv`)](#csv-format-pokedexcsv)
    - [JSON-format (`pokedex.json`)](#json-format-pokedexjson)
2. [Battle system](#battle-system)
    - [Flow of a battle](#flow-of-a-battle)
    - [Damage output](#damage-output)
    - [Type effectiveness](#type-effectiveness)
    - [Statistics](#statistics)
3. [Getting started](#getting-started)
    - [Requirements](#requirements)
    - [Dependencies](#dependencies)
    - [How to run the app](#how-to-run-the-app)
    - [Usage](#usage)
4. [Data files](#data-files)
5. [OOP design and motivation](#oop-design-and-motivation)
6. [Known limitations](#known-limitations)
7. [Attempted VG requirements](#attempted-vg-requirements)
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
- At startup, the app tries to load automatically, prioritizing in this order: JSON → CSV → seeded data
- Seeded data contains 25 Pokémon
- Saves automatically to JSON when the user exits via the menu
- Field validation when creating and editing Pokémon (e.g. empty name, negative HP)

### CSV-format (`pokedex.csv`)

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

### JSON-format (`pokedex.json`)

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

## Battle system

This section explains how the battles work and which statistics are calculated.

### Flow of a battle

Enter the name of the Pokémon to fight with. The available Pokémon are shown in the first alternative in the start menu at startup. CPU chooses its Pokémon randomly from the seeded pool (`storage.SeedData`). The fight is turn based, with one attack per turn. As the rule for who starts, I chose coin toss, since it was easy to implement and is fair in the long run. The player chooses which attack to use by entering the corresponding number shown in the console, where all available attacks are shown to the user. CPU chooses its attack randomly. The fight continues until a Pokémon faints (reaches 0 HP), or if the player cancels the fight by pressing [Enter].

Every battle starts with full HP Pokémon.

### Damage output

An attack can miss based on chance (dealing 0 damage), determined by the accuracy value associated with each attack. The player is informed if an attack misses in the log, and if it hits, the damage output is shown. Damage is calculated according to the following formula:

```
damage = round( baseDamage x typeEffectiveness x randomFactor x criticalMultiplier )
```

| Factor               | Value                                  |
|----------------------|----------------------------------------|
| `baseDamage`         | The base damage of the attack          |
| `typeEffectiveness`  | See type effectiveness table below     |
| `randomFactor`       | A random value in the range [0.9, 1.1) |
| `criticalMultiplier` | 2.0 (15% to proc), otherwise 1.0       |

### Type effectiveness

The factor `typeEffectiveness` is determined by the type of the attack as well as the type of the defending Pokémon, according to:

| Type of attack  | Type of defending Pokémon | Effect |
|-----------------|---------------------------|--------|
| Fire            | Grass                     | 2x     |
| Water           | Fire                      | 2x     |
| Grass           | Water                     | 2x     |
| Electric        | Water                     | 2x     |
| Fire            | Water                     | 0.5x   |
| Water           | Grass                     | 0.5x   |
| Grass           | Fire                      | 0.5x   |
| Normal          | (all)                     | 1x     |
| Everything else |                           | 1x     |

The implementing code for this table can be found in `model.Type.effectivenessAgainst()`.

### Statistics

When a fight is completed, and only then, the battle results with summary statistics are persisted. The statistics can be viewed in the start menu, and are as follows:

- Total wins and losses
- Most used Pokémon
- Most used attack
- Wins, losses and win percentage per Pokémon

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

### Usage

Start the app and pick an option from the start menu. Press [Enter] to return in menus, or to cancel a fight.

## Data files

| File                            | Content                                          | Created by              |
|---------------------------------|--------------------------------------------------|-------------------------|
| `pokedex.json` or `pokedex.csv` | The player's Pokémon collection                  | `Storage`               |
| `battle_results.json`           | All completed battles with statistics summarized | `BattleStorage`         |

## OOP design and motivation

The battle needs two types of decisions from each side: which Pokémon to choose, and which attack to choose. The code deals with this by using the following interfaces and implementations:

| Interface         | Player (console input) | CPU (random)         |
|-------------------|------------------------|----------------------|
| `PokemonSelector` | `HumanPokemonSelector` | `CPUPokemonSelector` |
| `AttackSelector`  | `HumanAttackSelector`  | `CPUAttackSelector`  |

`BattleManager` does not know how these choices are made (random or via input), since that is hidden behind the interfaces (Strategy). An example is `selector.chooseAttack(...)`. This line of code runs differently depending on the object (polymorphism) at runtime.

### Motivation

- **Loose coupling:** `BattleManager` does not know how the choices are made. This is achieved using the Strategy pattern.
- **Separation of concerns**. The selectors own how a choice is made, including input validation, while BattleManager owns the battle rules.
- **Testability:** Fake selectors can be passed to a JUnit test, without entering console inputs for each round, or relying on randomness.

### Why not inheritance?

Inheritance suits cases when subclasses share (*inherit*) code. The code for choosing a Pokémon/attack for a human versus CPU is very different (input vs random, respectively). Only the way they are called is the same, which is why I chose interfaces.

## Known limitations

- Duplicates of Pokémon are permitted.
- For the battle statistics, if most used Pokémon (or attack) leads to a tie, the result is arbitrary (HashMap order).
- Statistics for CPUs attack are not stored.

## Attempted VG requirements

I've attempted to fulfill all VG requirements:

- Clear and well motivated OOP-architecture
- Implementation of type effectiveness table
- JSON as persistence format
- Critical hits, shown in the log
- Persistent statistics: total win/losses, win/loss-ratio per Pokémon, most used attack, most used Pokémon
- The statistics can be viewed in the menu.
- The app properly deals with a missing or corrupt JSON-file.
