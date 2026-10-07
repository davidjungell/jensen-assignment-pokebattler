package pokebattler.battle;

import pokebattler.model.Attack;
import pokebattler.model.Pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleManager {
    private final Random random;
    private final PokemonSelector humanPokemonSelector;
    private final AttackSelector humanAttackSelector;
    private final PokemonSelector cpuPokemonSelector;
    private final AttackSelector cpuAttackSelector;
    private final static double CRITICAL_CHANCE = 0.15;
    private final static double CRITICAL_MULTIPLIER = 2.0;

    public BattleManager(Random random, PokemonSelector humanPokemonSelector, AttackSelector humanAttackSelector, PokemonSelector cpuPokemonSelector, AttackSelector cpuAttackSelector) {
        this.random = random;
        this.humanPokemonSelector = humanPokemonSelector;
        this.humanAttackSelector = humanAttackSelector;
        this.cpuPokemonSelector = cpuPokemonSelector;
        this.cpuAttackSelector = cpuAttackSelector;
    }

    public BattleResult fight() {
        Pokemon human = humanPokemonSelector.choosePokemon();
        if (human == null) return BattleResult.aborted();
        printChosenPokemon(false, human);
        Pokemon cpu = cpuPokemonSelector.choosePokemon();
        printChosenPokemon(true, cpu);
        List<String> playerAttacksUsed = new ArrayList<>();
        boolean playerTurn = random.nextBoolean();

        int turn = 1;
        while (!human.isFainted() && !cpu.isFainted()) {
            Pokemon attacker = playerTurn ? human : cpu;
            Pokemon defender = playerTurn ? cpu : human;
            AttackSelector selector = playerTurn ? humanAttackSelector : cpuAttackSelector;

            if (turn % 2 != 0) {
                System.out.println();
                report("====== Runda %d ======", (int) Math.ceil(turn / 2.0));
            }

            Attack attack = selector.chooseAttack(attacker);
            if (attack == null) {
                return BattleResult.aborted();
            }
            performAttack(attack, attacker, defender);
            if (playerTurn) playerAttacksUsed.add(attack.getName());
            playerTurn = !playerTurn;
            turn++;
        }

        boolean playerWon = cpu.isFainted();
        String winnerName = playerWon ? "Du" : "CPU";
        String winnerPokemon = playerWon ? human.getName() : cpu.getName();
        report("%s (med Pokémon %s) vinner striden!", winnerName, winnerPokemon);

        return BattleResult.completed(human.getName(), playerAttacksUsed, playerWon);
    }

    private void performAttack(Attack attack, Pokemon attacker, Pokemon defender) {
        boolean missAttack = random.nextDouble() > attack.getAccuracy();
        System.out.println();
        if (missAttack) {
            report("%s använde attacken %s på %s, men missade!", attacker.getName(), attack.getName(), defender.getName());
            return;
        }

        double randomDamageFactor = random.nextDouble(0.9, 1.1);
        double multiplier = attack.getType().effectivenessAgainst(defender.getType()) * randomDamageFactor;
        boolean critical = random.nextDouble() < CRITICAL_CHANCE;
        if (critical) {
            multiplier *= CRITICAL_MULTIPLIER;
        }
        int damage = (int) Math.round(attack.getDamage() * multiplier);
        defender.takeDamage(damage);
        report(
                "%s använde attacken %s på %s för %d skada.%s",
                attacker.getName(),
                attack.getName(),
                defender.getName(),
                damage,
                critical ? " (KRITISK TRÄFF!)" : ""
        );
    }

    private void report(String format, Object... formatArgs) {
        String formatted = String.format(format, formatArgs);
        System.out.println(formatted);
    }

    private void printChosenPokemon(boolean isCpu, Pokemon chosen) {
        String header = isCpu ? "CPUs val" : "Ditt val";
        report("%s av Pokémon:", header);
        report("Namn: %-15s Typ: %-10s HP: %d/%d",
                chosen.getName(), chosen.getType(), chosen.getCurrentHp(), chosen.getMaxHp());
    }
}
