package pokebattler.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import pokebattler.battle.BattleResult;
import pokebattler.battle.BattleStatistics;
import pokebattler.battle.StatisticsSummary;
import pokebattler.exception.StorageException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BattleStorage {
    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private static final String RESULTS_JSON = "battle_results.json";

    public record BattleFile(List<BattleResult> results, StatisticsSummary summary) {};


    public static List<BattleResult> load() {
        File file = new File(RESULTS_JSON);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            BattleFile data = mapper.readValue(file, BattleFile.class);
            if (data == null || data.results() == null) {
                return new ArrayList<>();
            }
            List<BattleResult> results = new ArrayList<>(data.results());
            results.removeIf(r -> r == null || !r.completed() || r.pokemonName() == null);
            return results;
        } catch (IOException e) {
            throw new StorageException("Kunde inte läsa " + RESULTS_JSON + ".", e);
        }
    }

    public static void append(BattleResult result) {
        List<BattleResult> results = load();
        results.add(result);
        BattleFile data = new BattleFile(results, new BattleStatistics(results).summary());
        try {
            mapper.writeValue(new File(RESULTS_JSON), data);
        } catch (IOException e) {
            throw new StorageException("Kunde inte skriva till " + RESULTS_JSON + ".", e);
        }
    }
}
