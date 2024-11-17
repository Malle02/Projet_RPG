package MonPackage;

public abstract class Weapon {
    protected double damage;
    protected double price;
    protected String name;
    protected double monsterDamageRatio;
    protected double obstacleDamageRatio;

    public Weapon(double damage, double price, String name, double monsterDamageRatio, double obstacleDamageRatio) {
        this.damage = damage;
        this.price = price;
        this.name = name;
        this.monsterDamageRatio = monsterDamageRatio;
        this.obstacleDamageRatio = obstacleDamageRatio;
    }

    public double getDamage() {
        return damage;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public abstract String asciiArt();

    public double getMonsterDamageRatio() {
        return this.monsterDamageRatio;
    }

    public double getObstacleDamageRatio() {
        return this.obstacleDamageRatio;
    }

    public void attack(Monster monster) {
        double damageDealt = this.damage * this.getMonsterDamageRatio();
        monster.hit(damageDealt);
        System.out.println("Vous avez infligé " + damageDealt + " dégâts au monstre avec " + this.name + " !");
    }

    public void attack(Obstacle obstacle) {
        double damageDealt = this.damage * this.getObstacleDamageRatio();
        obstacle.hit(damageDealt);
        System.out.println("Vous avez détruit l'obstacle en infligeant " + damageDealt + " dégâts avec " + this.name + " !");
    }

    @Override
    public String toString() {
        return this.name + " - Dégâts = " + this.damage + " - Prix = " + this.price + "$";
    }
}
