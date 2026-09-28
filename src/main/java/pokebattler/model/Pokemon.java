package pokebattler.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pokebattler.exception.InvalidPokemonException;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private String name;
    private Type type;
    private int maxHp;
    private int currentHp;
    private final List<Attack> attacks;

    @JsonCreator
    public Pokemon(@JsonProperty("name") String name,
                   @JsonProperty("type") Type type,
                   @JsonProperty("maxHp") int maxHp,
                   @JsonProperty("currentHp") int currentHp,
                   @JsonProperty("attacks") List<Attack> attacks) {
        validateName(name);
        validateType(type);
        validateMaxHp(maxHp);
        validateCurrentHp(currentHp, maxHp);
        this.name = name;
        this.type = type;
        this.maxHp = maxHp;
        this.currentHp = currentHp;
        this.attacks = attacks == null ? new ArrayList<>() : new ArrayList<>(attacks);
    }

    public static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidPokemonException("Ogiltig inmatning! Namnet får inte vara tomt.");
        }

        // För att inte få konflikter med Storage.loadCsv():
        if (name.contains(",") || name.contains("|") || name.contains(":")) {
            throw new InvalidPokemonException("Ogiltig inmatning! Namnet får inte innehålla tecknen ',', '|' eller ':'.");
        }
    }

    public static void validateType(Type type) {
        if (type == null) {
            throw new InvalidPokemonException("Ogiltig inmatning! Typen får inte vara null.");
        }
    }

    public static void validateMaxHp(int maxHp) {
        if (maxHp <= 0) {
            throw new InvalidPokemonException("Ogiltig inmatning! Max HP bör bara positivt.");
        }
    }

    public static void validateCurrentHp(int currentHp, int maxHp) {
        if (currentHp <= 0) {
            throw new InvalidPokemonException("Ogiltig inmatning! Nuvarande HP bör bara positivt.");
        }
        if (currentHp > maxHp) {
            throw new InvalidPokemonException("Ogiltig inmatning! Nuvarande HP får inte vara mer än max HP.");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }
}