package pokebattler.battle;

import pokebattler.model.Attack;
import pokebattler.model.Pokemon;

public interface AttackSelector {
    Attack chooseAttack(Pokemon attacker);
}
