package cupofdb.objs.pitcher;

import cupofdb.objs.cup.Cup;

import java.util.Iterator;
import java.util.List;

/**
 * A spoon for managing the pool of connections. </br>
 * Manages idle connections only in the pool.
 */
public class NormalSpoon implements Spoon {
    private Pitcher pitcher;
    private List<Cup> cups;
    private int max;
    private int min;
    private int initialCnt;
    private int idleCnt;
    private int upSize;
    private int downSize;

    private boolean isRunning;

    public NormalSpoon(Pitcher pitcher, int max, int min, int initialCnt, int upSize, int downSize) {
        this.pitcher = pitcher;
        this.cups = this.pitcher.getCups();
        this.max = max;
        this.min = min;
        this.initialCnt = initialCnt;
        this.upSize = upSize;
        this.downSize = downSize;
        this.isRunning = false;
        this.idleCnt = 0;

        // init
        init();
    }

    private void init() {
        for (int i = 0; i < initialCnt && idleCount() <= max; i++) {
            createIdle();
        }
    }

    public int count() {
        return this.cups.size();
    }

    public boolean isEnough() {
        return idleCnt > upSize;
    }

    public void createIdle() {
        Cup cup = new Cup(this.pitcher.getUrl(), this.pitcher.getUsername(), this.pitcher.getPassword());
        this.cups.add(cup);
        this.idleCnt++;
    }

    public void removeIdle() {
        this.cups.removeIf(cup -> !cup.isInUse());
        this.idleCnt--;
    }

    public void up() {
        for (int i = 0; i < upSize && idleCount() <= max; i++) {
            createIdle();
        }
    }

    public void down() {
        for (int i = 0; i < downSize && min <= idleCount(); i++) {
            removeIdle();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!isEnough()) {
                up();
            } else {
                down();
            }
        }
    }

    @Override
    public int idleCount() {
        return this.idleCnt;
    }

    @Override
    public Cup get() {
        Iterator<Cup> iterator = this.cups.iterator();
        while (iterator.hasNext()) {
            Cup cup = iterator.next();
            if (!cup.isInUse()) {
                cup.use();
                return cup;
            }
        }
        return null;
    }
}

