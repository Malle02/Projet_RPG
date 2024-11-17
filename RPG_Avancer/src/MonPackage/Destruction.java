package MonPackage;

public class Destruction {
    protected double health;

    public Destruction(double health) {
        this.health = health;
    }

    public void hit(double damage) {
        this.health -= damage;
        System.out.println("Destruction subit " + damage + " dégâts.");
    }

    public boolean isAlive() {
        return health > 0;
    }

    public double getHealth() {
        return health;
    }
}
