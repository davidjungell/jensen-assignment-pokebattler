import org.junit.jupiter.api.Test;
import pokebattler.battle.AttackSelector;
import pokebattler.battle.BattleManager;
import pokebattler.battle.DamageCalculator;
import pokebattler.battle.PokemonSelector;
import pokebattler.model.Attack;
import pokebattler.model.Pokemon;
import pokebattler.model.Type;
import pokebattler.ui.ConsoleBattleReporter;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BattleManagerTest {

    private Pokemon makePokemon(String name, int hp) {
        List<Attack> attacks = List.of(new Attack("Scratch", Type.NORMAL, 10, 1.0));
        return new Pokemon(name, Type.FIRE, hp, hp, attacks);
    }

    private Attack makeAttack(String name, int damage) {
        return new Attack(name, Type.NORMAL, damage, 1);
    }

    private BattleManager managerWith(Random random,
                                      PokemonSelector humanPokemon,
                                      AttackSelector humanAttack,
                                      PokemonSelector cpuPokemon,
                                      AttackSelector cpuAttack
    ) {

        return new BattleManager(
                random,
                humanPokemon,
                humanAttack,
                cpuPokemon,
                cpuAttack,
                new DamageCalculator(random),
                new ConsoleBattleReporter()
        );
    }

    @Test
    void fakeSelectorsMakeTheBattleControlled() {
        Pokemon human = makePokemon("Charmander", 100);
        Pokemon cpu = makePokemon("Pikachu", 50);
        Attack humanAttack = makeAttack("Hard Hit", 40);
        Attack cpuAttack = makeAttack("Weak Hit", 1);

        BattleManager manager = managerWith(
                new Random(1),
                () -> Optional.of(human),
                p -> Optional.of(humanAttack),
                () -> Optional.of(cpu),
                p -> Optional.of(cpuAttack)
        );

        manager.fight();
        assertFalse(human.isFainted());
        assertTrue(cpu.isFainted());
    }
}