package cupofdb.objs.pitcher;

public class SpoonFactory {
    public static Spoon createSpoon(String type) {
        if (type.equals("normal")) {
            return normalSpoon();
        }

        return normalSpoon();
    }

    private static Spoon normalSpoon() {
        // return new NormalSpoon();
        return null;
    }
}
