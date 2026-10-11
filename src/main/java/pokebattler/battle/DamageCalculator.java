package pokebattler.battle;

import pokebattler.model.Attack;
import pokebattler.model.Pokemon;

import java.util.Random;

public class DamageCalculator {
    private final Random random;
    private static final double CRITICAL_CHANCE = 0.15;
    private static final double CRITICAL_MULTIPLIER = 2.0;

    public DamageCalculator(Random random) {
        this.random = random;
    }

    DamageOutcome calculate(Attack attack, Pokemon defender) {
        boolean missAttack = random.nextDouble() >= attack.getAccuracy();
        if (missAttack) {
            return new DamageOutcome(true, 0, false);
        }

        double randomDamageFactor = random.nextDouble(0.9, 1.1);
        double multiplier = attack.getType().effectivenessAgainst(defender.getType()) * randomDamageFactor;
        boolean critical = random.nextDouble() < CRITICAL_CHANCE;
        if (critical) {
            multiplier *= CRITICAL_MULTIPLIER;
        }
        int damage = (int) Math.round(attack.getDamage() * multiplier);

        return new DamageOutcome(false, damage, critical);
    }
}
