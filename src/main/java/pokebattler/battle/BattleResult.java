package pokebattler.battle;

public class BattleResult {
    String log;
    String winnerName;
    String loserName;
    boolean completed;

    public BattleResult(String log, String winnerName, String loserName, boolean completed) {
        this.log = log;
        this.winnerName = winnerName;
        this.loserName = loserName;
        this.completed = completed;
    }

    public static BattleResult aborted(String log) {
        return new BattleResult(log, null, null, false);
    }

    public static BattleResult completed(String log, String winnerName, String loserName) {
        return new BattleResult(log, winnerName, loserName, true);
    }

    public boolean isCompleted() {
        return completed;
    }
}
