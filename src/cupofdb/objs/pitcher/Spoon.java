package cupofdb.objs.pitcher;

import cupofdb.objs.cup.Cup;

/**
 * A spoon for managing the pool of connections.
 */
public interface Spoon extends Runnable {

    public int idleCount();

    public Cup get();
}
