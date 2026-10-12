package pokebattler.battle;

import pokebattler.model.Attack;
import pokebattler.model.Pokemon;

import java.util.Optional;

public interface AttackSelector {
    /**
     * @return vald attack, eller Optional.empty() om striden avbryts eller Pokémon saknar attacker.
     */
    Optional<Attack> chooseAttack(Pokemon attacker);
}
