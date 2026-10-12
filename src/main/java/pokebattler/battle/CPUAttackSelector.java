package pokebattler.battle;

import pokebattler.model.Attack;
import pokebattler.model.Pokemon;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CPUAttackSelector implements AttackSelector {
    private final Random random;

    public CPUAttackSelector(Random random) {
        this.random = random;
    }

    @Override
    public Optional<Attack> chooseAttack(Pokemon attacker) {
        List<Attack> attacks = attacker.getAttacks();

        if (attacks == null || attacks.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(attacks.get(random.nextInt(attacks.size())));
    }
}