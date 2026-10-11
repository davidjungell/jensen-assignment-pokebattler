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
    private final DamageCalculator calculator;
    private final BattleReporter reporter;

    public BattleManager(Random random, PokemonSelector humanPokemonSelector, AttackSelector humanAttackSelector, PokemonSelector cpuPokemonSelector, AttackSelector cpuAttackSelector, DamageCalculator calculator, BattleReporter reporter) {
        this.random = random;
        this.humanPokemonSelector = humanPokemonSelector;
        this.humanAttackSelector = humanAttackSelector;
        this.cpuPokemonSelector = cpuPokemonSelector;
        this.cpuAttackSelector = cpuAttackSelector;
        this.calculator = calculator;
        this.reporter = reporter;
    }

    public BattleResult fight() {
        Pokemon human = humanPokemonSelector.choosePokemon();
        if (human == null) return BattleResult.aborted();
        reporter.pokemonChosen(false, human);
        Pokemon cpu = cpuPokemonSelector.choosePokemon();
        reporter.pokemonChosen(true, cpu);
        List<String> playerAttacksUsed = new ArrayList<>();
        boolean playerTurn = random.nextBoolean();

        int turn = 1;
        while (!human.isFainted() && !cpu.isFainted()) {
            Pokemon attacker = playerTurn ? human : cpu;
            Pokemon defender = playerTurn ? cpu : human;
            AttackSelector selector = playerTurn ? humanAttackSelector : cpuAttackSelector;

            reporter.roundStarted(turn);
            reporter.hpStatus(human, cpu);
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
        reporter.battleEnded(playerWon, human, cpu);
        return BattleResult.completed(human.getName(), playerAttacksUsed, playerWon);
    }

    private void performAttack(Attack attack, Pokemon attacker, Pokemon defender) {
        DamageOutcome outcome = calculator.calculate(attack, defender);

        if (outcome.missed()) {
            reporter.attackMissed(attacker, attack, defender);
            return;
        }

        defender.takeDamage(outcome.damage());
        reporter.attackHit(attacker, attack, defender, outcome);
    }
}
