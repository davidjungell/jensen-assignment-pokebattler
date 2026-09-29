package pokebattler.battle;

import pokebattler.model.Attack;
import pokebattler.model.Pokemon;

import java.util.Random;

public class BattleManager {
    private final Random random;
    private final AttackSelector selectorA;
    private final AttackSelector selectorB;

    public BattleManager(Random random, AttackSelector SelectorA, AttackSelector SelectorB) {
        this.random = random;
        this.selectorA = SelectorA;
        this.selectorB = SelectorB;
    }

    public BattleResult fight(StringBuilder log, String playerA, String playerB, Pokemon PokemonA, Pokemon PokemonB) {
        boolean aStarts = random.nextBoolean();
        Pokemon firstPokemon = aStarts ? PokemonA : PokemonB;
        Pokemon secondPokemon = aStarts ? PokemonB : PokemonA;
        AttackSelector firstAttackSelector = aStarts ? selectorA : selectorB;
        AttackSelector secondAttackSelector = aStarts ? selectorB : selectorA;

        String winnerName, loserName;
        int round = 1;
        while(true) {
            boolean fightCompleted = takeTurn(firstAttackSelector, firstPokemon, secondPokemon);
            if (!fightCompleted) {
                return BattleResult.aborted(log.toString());
            }
            if (secondPokemon.isFainted()) {
                winnerName = playerA;
                loserName = playerB;
                break;
            }

            takeTurn(secondAttackSelector, secondPokemon, firstPokemon);
            if (firstPokemon.isFainted()) {
                winnerName = playerB;
                loserName = playerA;
                break;
            }
            round++;
        }
        return BattleResult.completed(log.toString(), winnerName, loserName);
    }

    private boolean takeTurn(AttackSelector attackSelector, Pokemon attacker, Pokemon defender) {
        Attack attack = attackSelector.chooseAttack(attacker);
        if (attack == null) {
            return false;
        }

        boolean missAttack = random.nextDouble() > attack.getAccuracy();
        if (missAttack) {

            return true;
        }

        double multiplier = attack.getType().effectivenessAgainst(defender.getType());
        double CRITICAL_CHANCE = 0.15;
        boolean critical = random.nextDouble() < CRITICAL_CHANCE;
        if (critical) {
            double CRITICAL_MULTIPLIER = 2.0;
            multiplier *= CRITICAL_MULTIPLIER;
        }
        int damage = (int) Math.round(attack.getDamage() * multiplier);
        defender.takeDamage(damage);
        return true;
    }

}
