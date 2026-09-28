package pokebattler.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pokebattler.exception.InvalidAttackException;

public class Attack {
    private String name;
    private Type type;
    private int damage;
    private double accuracy;

    @JsonCreator
    public Attack(@JsonProperty("name") String name,
                  @JsonProperty("type") Type type,
                  @JsonProperty(value = "damage", required = true) int damage, // Om fältet saknas i JSON-filen sätter Jackson 0 som default. Noll skada betraktas som giltigt i validateDamage(), därmed required = true.
                  @JsonProperty("accuracy") double accuracy) {
        validateName(name);
        validateType(type);
        validateDamage(damage);
        validateAccuracy(accuracy);
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.accuracy = accuracy;
    }

    public static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidAttackException("Ogiltig inmatning! Namnet får inte vara tomt.");
        }

        // För att inte få konflikter med Storage.loadCsv():
        if (name.contains(",") || name.contains("|") || name.contains(":")) {
            throw new InvalidAttackException("Ogiltig inmatning! Namnet får inte innehålla tecknen ',', '|' eller ':'.");
        }
    }

    public static void validateType(Type type) {
        if (type == null) {
            throw new InvalidAttackException("Ogiltig inmatning! Typen får inte vara null.");
        }
    }

    public static void validateDamage(int damage) {
        // Man kan tänka sig att damage = 0 är giltigt, t.ex. om en attack bara blindar.
        if (damage < 0) {
            throw new InvalidAttackException("Ogiltig inmatning! Skadan kan inte vara negativ.");
        }
    }

    public static void validateAccuracy(double accuracy) {
        if (accuracy <= 0 || accuracy > 1) {
            throw new InvalidAttackException("Ogiltig inmatning! Träffsäkerheten måste vara inom [0, 1].");
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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
}