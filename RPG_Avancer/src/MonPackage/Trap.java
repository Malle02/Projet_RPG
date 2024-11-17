package MonPackage;

public class Trap extends Destruction {
    private static final double DAMAGE = 15;

    public Trap() {
        super(DAMAGE);
    }

    public String asciiArt() {
        return "💀 Piège\n" +
               "   _____\n" +
               "  |     |\n" +
               "  |  ☠️  |\n" +
               "  |_____|\n";
    }

    public double getDamage() {
        return DAMAGE;
    }
}
