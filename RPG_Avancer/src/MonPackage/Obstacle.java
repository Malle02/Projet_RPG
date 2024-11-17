package MonPackage;

public class Obstacle extends Destruction {
    private static final double LIFE = 50;

    public Obstacle() {
        super(LIFE);
    }

    public String asciiArt() {
        return
                "   _______\n" +
                "  |       |\n" +
                "  |       |\n" +
                "  |_______|\n";
    }

    public void destroyedMessage() {
        System.out.println("L'obstacle est détruit et ne bloque plus votre chemin ! 🪨");
    }
}
