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

    public BattleResult fight(String playerName) {
        Pokemon human = humanPokemonSelector.choosePokemon();
        if (human == null) return BattleResult.aborted();
        Pokemon cpu = cpuPokemonSelector.choosePokemon();
        List<String> playerAttacksUsed = new ArrayList<>();
        boolean playerTurn = random.nextBoolean();

        int turn = 1;
        while (!human.isFainted() && !cpu.isFainted()) {
            Pokemon attacker = playerTurn ? human : cpu;
            Pokemon defender = playerTurn ? cpu : human;
            AttackSelector selector = playerTurn ? humanAttackSelector : cpuAttackSelector;

            if (turn % 2 != 0) {
                report("Runda %d", (int) Math.ceil(turn / 2.0));
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
        String winnerName = playerWon ? playerName : "CPU";
        String winnerPokemon = playerWon ? human.getName() : cpu.getName();
        report("%s (med Pokémon %s) vinner striden!", winnerName, winnerPokemon);

        return BattleResult.completed(playerName, human.getName(), playerAttacksUsed, playerWon);
    }

    private void performAttack(Attack attack, Pokemon attacker, Pokemon defender) {
        boolean missAttack = random.nextDouble() > attack.getAccuracy();
        if (missAttack) {
            report("%s använde attacken %s på %s, men missade!", attacker, attack, defender);
            return;
        }

        double multiplier = attack.getType().effectivenessAgainst(defender.getType());
        boolean critical = random.nextDouble() < CRITICAL_CHANCE;
        if (critical) {
            multiplier *= CRITICAL_MULTIPLIER;
        }
        int damage = (int) Math.round(attack.getDamage() * multiplier);
        defender.takeDamage(damage);
        report("%s använde attacken %s på %s för %d skada.%s", attacker, attack, defender, damage, critical ? " (KRITISK TRÄFF!)" : "");
    }

    //TODO: vi har print nu i Battlemanager, strukturproblem? Ta bort detta ersätt med sout?
    private void report(String format, Object... formatArgs) {
        String formatted = String.format(format, formatArgs);
        System.out.println(formatted);
    }
}
