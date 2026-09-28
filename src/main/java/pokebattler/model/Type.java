package pokebattler.model;

public enum Type {
    FIRE, WATER, GRASS, ELECTRIC, NORMAL;

    public double effectivenessAgainst(Type defender) {
        return switch (this) {
            case FIRE -> switch (defender) {
                case GRASS -> 2.0;
                case WATER -> 0.5;
                default -> 1.0;
            };
            case WATER -> switch (defender) {
                case FIRE -> 2.0;
                case GRASS -> 0.5;
                default -> 1.0;
            };
            case GRASS -> switch (defender) {
                case WATER -> 2.0;
                case FIRE -> 0.5;
                default -> 1.0;
            };
            case ELECTRIC -> defender == WATER ? 2.0 : 1.0;
            case NORMAL -> 1.0;
        };
    }
}