package cupofdb.objs.tray;

import cupofdb.objs.pitcher.Pitcher;

import java.util.HashMap;
import java.util.Map;

/**
 * A container for pitchers.
 */
public class Tray implements AutoCloseable  {
    private Map<String, Pitcher> pitchers;

    public Tray() {
        this.pitchers = new HashMap<>();
    }

    public void addPitcher(Pitcher pitcher) {
        pitchers.put(pitcher.getName(), pitcher);
    }

    public Pitcher getPitcher(String name) {
        return pitchers.get(name);
    }

    @Override
    public void close() throws Exception {
        for (Pitcher pitcher : pitchers.values()) {
            pitcher.close();
        }
    }
}
