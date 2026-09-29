package pokebattler.battle;

import pokebattler.model.Attack;
import pokebattler.model.Pokemon;

import java.util.List;
import java.util.Random;

public class CPUAttackSelector implements AttackSelector {
    private final Random random;

    public CPUAttackSelector(Random random) {
        this.random = random;
    }

    @Override
    public Attack chooseAttack(Pokemon attacker) {
        List<Attack> attacks = attacker.getAttacks();
        return attacks.get(random.nextInt(0, attacks.size()));
    }
}