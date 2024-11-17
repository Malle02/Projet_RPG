package MonPackage;

public class Monster extends Destruction {
    private static final double BASE_DAMAGE = 10; // Dégâts de base du monstre
    private static final double LIFE = 50;

    public Monster() {
        super(LIFE);
    }

    public double attack() {
        // Génère des dégâts aléatoires autour de la valeur de BASE_DAMAGE
        double damage = BASE_DAMAGE + (Math.random() * 5); // Dégâts aléatoires entre 10 et 15
        System.out.println("Le monstre attaque et inflige " + damage + " points de dégâts !");
        return damage;
    }

    public String asciiArt() {
        return
                "     __/\\__\n" +
                "    /      \\\n" +
                "   |  O  O  |\n" +
                "   |    ∆   |\n" +
                "    \\  ~~~ /\n" +
                "     \\_____/\n";
    }
}
