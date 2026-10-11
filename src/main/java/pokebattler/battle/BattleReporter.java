package pokebattler.battle;

import pokebattler.model.Attack;
import pokebattler.model.Pokemon;

public interface BattleReporter {
    void roundStarted(int turn);
    void hpStatus(Pokemon human, Pokemon cpu);
    void pokemonChosen(boolean isCpu, Pokemon pokemon);
    void attackMissed(Pokemon attacker, Attack attack, Pokemon defender);
    void attackHit(Pokemon attacker, Attack attack, Pokemon defender, DamageOutcome outcome);
    void battleEnded(boolean playerWon, Pokemon human, Pokemon cpu);
}
