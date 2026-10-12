package pokebattler.ui;

import pokebattler.battle.BattleReporter;
import pokebattler.battle.DamageOutcome;
import pokebattler.model.Attack;
import pokebattler.model.Pokemon;

public class ConsoleBattleReporter implements BattleReporter {

    @Override
    public void roundStarted(int turn) {
        if (turn % 2 != 0) {
            System.out.printf("%n==================== Runda %d ====================%n", (int) Math.ceil(turn / 2.0));
        }
    }

    @Override
    public void hpStatus(Pokemon human, Pokemon cpu) {
        System.out.printf("Du: %s %d/%d HP  |  CPU: %s %d/%d HP%n",
                human.getName(), human.getCurrentHp(), human.getMaxHp(),
                cpu.getName(), cpu.getCurrentHp(), cpu.getMaxHp());
    }

    @Override
    public void pokemonChosen(boolean isCpu, Pokemon chosen) {
        String header = isCpu ? "CPUs val" : "Ditt val";
        System.out.printf("%s av Pokémon:", header);
        System.out.printf("%nNamn: %-15s Typ: %-10s HP: %d/%d%n",
                chosen.getName(), chosen.getType(), chosen.getCurrentHp(), chosen.getMaxHp());
    }

    @Override
    public void attackMissed(Pokemon attacker, Attack attack, Pokemon defender) {
        System.out.printf("%n%s använde attacken %s på %s, men missade!%n", attacker.getName(), attack.getName(), defender.getName());
    }

    @Override
    public void attackHit(Pokemon attacker, Attack attack, Pokemon defender, DamageOutcome outcome) {
        System.out.printf(
                "%n%s använde attacken %s på %s för %d skada.%s%n",
                attacker.getName(),
                attack.getName(),
                defender.getName(),
                outcome.damage(),
                outcome.critical() ? " (KRITISK TRÄFF!)" : ""
        );
    }

    @Override
    public void battleEnded(boolean playerWon, Pokemon human, Pokemon cpu) {
        String winnerName = playerWon ? "Du" : "CPU";
        String winnerPokemon = playerWon ? human.getName() : cpu.getName();
        String loserPokemon = playerWon ? cpu.getName() : human.getName();
        System.out.printf("%s svimmade! %s (med Pokémon %s) vann striden!%n", loserPokemon, winnerName, winnerPokemon);
    }
}
