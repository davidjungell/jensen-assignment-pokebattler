import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import pokebattler.exception.InvalidPokemonException;
import pokebattler.model.Attack;
import pokebattler.model.Pokemon;
import pokebattler.model.Type;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {

    @Test
    void createPokemon_WithValidData_setsFieldsCorrectly() {
        List<Attack> attacks = new ArrayList<>();
        attacks.add(new Attack("Scratch", Type.NORMAL, 10, 1.0));
        Pokemon pokemon = new Pokemon("Charmander", Type.FIRE, 40, 30, attacks);

        assertAll(
                () -> assertEquals("Charmander", pokemon.getName()),
                () -> assertEquals(Type.FIRE, pokemon.getType()),
                () -> assertEquals(40, pokemon.getMaxHp()),
                () -> assertEquals(30, pokemon.getCurrentHp()),
                () -> assertEquals(1, pokemon.getAttacks().size())
        );
    }

    @Test
    void createPokemon_withNullAttacks_resultsInEmptyList() {
        Pokemon pokemon = new Pokemon("Charmander", Type.FIRE, 40, 30, null);

        assertNotNull(pokemon.getAttacks());
        assertTrue(pokemon.getAttacks().isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "Char,mander", "Char|mander", "Char:mander"})
    void createPokemon_withInvalidName_throwsInvalidPokemonException(String invalidName) {
        assertThrows(
                InvalidPokemonException.class,
                () -> new Pokemon(invalidName, Type.FIRE, 40, 30, null));
    }
}
