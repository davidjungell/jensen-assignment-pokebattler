package pokebattler.battle;

// Fälten är överflödiga, eftersom losses och winPercent kan beräknas från battles och wins.
// Designen har ändå behållits så de skrivs ut i JSON-filen.
public record PokemonStats(int battles, int wins, int losses, int winPercent) {
    public PokemonStats {
        if (battles != wins + losses) {
            throw new IllegalArgumentException("Battles måste vara wins + losses.");
        }
    }
}
